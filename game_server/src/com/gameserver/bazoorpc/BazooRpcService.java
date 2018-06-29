package com.gameserver.bazoorpc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.util.RandomUtil;
import com.gameserver.bazoo.msg.CGRoomPubJoin;
import com.gameserver.bazoo.msg.GCRobotWhichRoomToGoin;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoorpc.data.RobotHumanNumber;
import com.gameserver.common.Globals;
import com.gameserver.common.unit.GameUnitList;
import com.gameserver.player.Player;
import com.gameserver.player.enums.PlayerRoleEnum;

/**
 * 机器人远程调用的服务
 * @author JavaServer
 *
 */
public class BazooRpcService implements InitializeRequired {
	private Logger logger = Loggers.BAZOO;

	@Override
	public void init() {
		
		
	}

	/**
	 * 清理机器人
	 */
	public RobotHumanNumber cleanRobot(String roomNumber){
		Room room = Globals.getBazooPubService().getRoomService().getRoom(RoomNumber.toRoomNumber(roomNumber));
		int robotNumber = 0;
		int humanNumber = 0;
		List<Player> players = room.getPlayersNotPureWatch();
		for(Player player:players){
			//如果是机器人
			if(player.getPlayerRoleEnum() == PlayerRoleEnum.ROBOT){
				robotNumber++;
			}else{
				humanNumber++;
			}
		}
		//如果只有机器人了，就将机器人状态改为退出房间  并删除房间
		if(humanNumber == 0 && robotNumber>0){
			for(Player player:players){
				player.getHuman().getBazooRoomEveryUserInfo().setIsInRoom(RoomEveryUserInfo.USER_NOT_IN_ROOM, player.getPassportId());
			}
			//删除房间
			Globals.getBazooPubService().getRoomService().removeRoomForce(RoomNumber.toRoomNumber(roomNumber));
		}
		RobotHumanNumber robotHumanNumber = new RobotHumanNumber();
		robotHumanNumber.setRobotNumber(robotNumber);
		robotHumanNumber.setHumanNumber(humanNumber);
		robotHumanNumber.setPlayers(players);
		return robotHumanNumber;
	}
	
	
	/**
	 * 
	 * new-现在内存里会有固定数量的机器人，比如30个，用户进入房间后 就去 内存里 找，找不到拉倒
	 * 
	 * old-远程开启机器人 
	 */
	
	public void startRemoteRobot(String roomNumber){
		//看看现在房间里有几个 普通人 几个机器人
		RobotHumanNumber robotHumanNumber = cleanRobot(roomNumber);
		int robotNumber = robotHumanNumber.getRobotNumber();
		int humanNumber = robotHumanNumber.getHumanNumber();
		List<Player> players = robotHumanNumber.getPlayers();
		
		//房间的总人数  ：普通人 + 机器人 = 总人数
		int totalNum = robotNumber + humanNumber;
		//需要添加的机器人
		int number = 5-totalNum;
		
		//大于零 就 加 机器人
		if(number > 0){
			//返回的是  已经在内存中存在的机器人
			List<Player> robotPlayers = lookForRobots(roomNumber,number);
			goInRoom(robotPlayers,roomNumber);
		}
		//小于零 就 减去一个 机器人
		if(number < 0){
			for(Player player:players){
				//如果是机器人
				if(player.getPlayerRoleEnum() == PlayerRoleEnum.ROBOT){
					player.getHuman().getBazooRoomEveryUserInfo().setIsInRoom(RoomEveryUserInfo.USER_NOT_IN_ROOM, player.getPassportId());
					return;
				}
			}
		}
		
		
	}
	
	
	private void goInRoom(List<Player> robotPlayers,String roomNumber){
		if(robotPlayers != null && robotPlayers.size()>0){
			for(Player p:robotPlayers){
//				CGRoomPubJoin CGRoomPubJoin = new CGRoomPubJoin();
//				CGRoomPubJoin.setRoomNumber(roomNumber);
				GCRobotWhichRoomToGoin GCRobotWhichRoomToGoin = new GCRobotWhichRoomToGoin();
				GCRobotWhichRoomToGoin.setPassportId(p.getPassportId());
				GCRobotWhichRoomToGoin.setRoomNumber(roomNumber);
				p.sendMessage(GCRobotWhichRoomToGoin);
			}
		}
	}
	
	/**
	 * 寻找机器人
	 * @param roomNumber
	 * @param robotSize
	 * @return
	 */
	public List<Player> lookForRobots(String roomNumber,int robotSize){
		//现在 是只从内存里找 
		List<Player> robotList = new ArrayList<Player>();
		GameUnitList<Player> onlinePlayerList = Globals.getOnlinePlayerService().getOnlinePlayers();
		//从内存里找出 所有没在房间的机器人
		List<Player> allRobotsNotInRoom = new ArrayList<Player>();
		for(Player player:onlinePlayerList){
			if(player.getPlayerRoleEnum() == PlayerRoleEnum.ROBOT){
				//当前找到的机器人必须 退出了房间（真正退出了房间，房间里已经没有这个机器人）
				if(player.getHuman().getBazooRoomEveryUserInfo().getIsInRoom() == RoomEveryUserInfo.USER_NOT_IN_ROOM){
					String bazooRoomNumber = player.getHuman().getBazooRoom();
					Room theRoom = Globals.getBazooPubService().getRoomService().getRoom(RoomNumber.toRoomNumber(bazooRoomNumber));
					if(theRoom == null){
						allRobotsNotInRoom.add(player);
					}else{
						if(!theRoom.getPlayers().contains(player)){
							allRobotsNotInRoom.add(player);
						}
					}
				}
			}
		}
		
		//随机选取机器人
		for(int i=0;i<robotSize;i++){
			if(allRobotsNotInRoom.size() <=0 ){
				break;
			}
			int index = RandomUtil.nextInt(0, allRobotsNotInRoom.size());
			Player player = allRobotsNotInRoom.get(index);
			allRobotsNotInRoom.remove(player);
			robotList.add(player);
		}
		//如果不够 ，再去主动开启 机器人
		/*if(robotList.size()<robotSize){
			int number = 0;
			if(robotList.size() == 0){
				number=2;
			}else if(robotList.size() == 1){
				number=1;
			}
			//设置机器人进入房间
			DynamicProxy.robotGoInRoom(roomNumber, number);
		}*/
		return robotList;
	}
}
