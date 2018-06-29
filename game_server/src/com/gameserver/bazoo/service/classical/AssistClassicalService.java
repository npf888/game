package com.gameserver.bazoo.service.classical;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.bazoo.data.DiceInfo;
import com.gameserver.bazoo.data.EndCountInfo;
import com.gameserver.bazoo.insideData.PlayerInfo;
import com.gameserver.bazoo.msg.GCDiceSingleSwing;
import com.gameserver.bazoo.msg.GCDiceUnifySwing;
import com.gameserver.bazoo.msg.GCDiceUserShouldSwing;
import com.gameserver.bazoo.msg.GCEndCount;
import com.gameserver.bazoo.msg.GCRobotDiceUnifySwing;
import com.gameserver.bazoo.service.room.BazooPubService;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoo.service.room.RoomService;
import com.gameserver.bazoo.util.DiceUtils;
import com.gameserver.player.Player;

/**
 * 辅助service 主要是让代码看起来更整洁
 * @author JavaServer
 *
 */
public class AssistClassicalService {
	private Logger logger = Loggers.BAZOO;
	private RoomService roomService;
	private BazooPubService bazooPubService;
	public AssistClassicalService(RoomService roomService,BazooPubService bazooPubService){
		this.roomService=roomService;
		this.bazooPubService=bazooPubService;
	}
	
	
	
	

	public void sendSomeOneShouldSwingMessage(Player p,Player nextPlayer) {
		GCDiceUserShouldSwing GCDiceUserShouldSwing = new GCDiceUserShouldSwing();
		GCDiceUserShouldSwing.setShouldCallPassportId(nextPlayer.getPassportId());
		p.sendBazooMessage(GCDiceUserShouldSwing);
	}
	
	
	
	
	
	
	/**
	 * 发送色子的信息 重摇
	 * @param player
	 * @param diceList
	 */
	public void sendSingleDiceMessage(Player player,List<Integer> diceList){
		GCDiceSingleSwing diceInfo = new GCDiceSingleSwing();
		diceInfo.setPassportId(player.getPassportId());
		int[] diceArr = new int[diceList.size()];
		for(int j=0;j<diceList.size();j++){
			diceArr[j]=diceList.get(j);
		}
		diceInfo.setDiceValues(diceArr);
		player.sendBazooMessage(diceInfo);
	}

	/**
	 * 重摇 发送给其他人
	 * @param player
	 * @param diceList
	 */
	public void sendSingleDiceToOtherMessage(Player player){
		Room room = roomService.getRoom(player);
		
		GCDiceSingleSwing diceInfo = new GCDiceSingleSwing();
		diceInfo.setPassportId(player.getPassportId());
		int[] diceArr = new int[0];
		diceInfo.setDiceValues(diceArr);
		List<Player> pList = room.getPlayers();
		for(int i=0;i<pList.size();i++){
			Player p= pList.get(i);
			if(p.getPassportId() != player.getPassportId()){
				p.sendBazooMessage(diceInfo);
			}
		}
	}
	/**
	 * 发送色子的信息
	 * @param player
	 * @param diceList
	 */
	public void sendDiceMessage(Player oPlayer,List<Integer> diceList){
		GCDiceUnifySwing diceInfo = new GCDiceUnifySwing();
		diceInfo.setPassportId(oPlayer.getPassportId());
		diceInfo.setDiceValues(DiceUtils.getArrFromDiceList(diceList));
		oPlayer.sendBazooMessage(diceInfo);
	}
	/**
	 * 给机器人发送色子的值
	 * @param player
	 * @param diceList
	 */
	public void sendDiceToRobotMessage(Player robot,List<Player> players){
		GCRobotDiceUnifySwing diceInfos = new GCRobotDiceUnifySwing();
		DiceInfo[] diceInfoArr = new DiceInfo[players.size()];
		for(int i=0;i<players.size();i++){
			Player p = players.get(i);
			DiceInfo diceInfo = new DiceInfo();
			diceInfo.setPassportId(p.getPassportId());
			diceInfo.setDiceValues(DiceUtils.getArrFromDiceList(p.getHuman().getBazooRoomEveryUserInfo().getDiceValues()));
			diceInfo.setCowNameInt(-1);
			diceInfo.setRedDiceValues(new int[]{});
			diceInfoArr[i]=diceInfo;
		}
		diceInfos.setDiceInfo(diceInfoArr);
		robot.sendBazooMessage(diceInfos);
	}
	
	
	//结束最后一个等待的人
	/*public void endWaitingMan(PlayerInfo playerInfo, RoomNumber roomNumber){
		Player waitingPlayer = playerInfo.getWaitingMan();
		if(waitingPlayer != null){
			waitingPlayer.getHuman().getBazooRoomEveryUserInfo().getClassicalUserInfo().interrupt(waitingPlayer,roomNumber);
		}
	}*/
	
	
	
	public EndCountInfo getEndCountInfo(Player player, RoomEveryUserInfo bazooRoomUserInfo,
			long gold, int len) {
		EndCountInfo endCountInfo = new EndCountInfo();
		int[] values = DiceUtils.getArrFromDiceList(bazooRoomUserInfo.getDiceValues());
		endCountInfo.setDiceValues(values);
		endCountInfo.setGold(gold);
		endCountInfo.setMultiple(len);
		endCountInfo.setPassportId(player.getPassportId());
		endCountInfo.setPersonTotalGold(player.getHuman().getGold());
		return endCountInfo;
		
	}






	
	
	public void endCountSendMessage(RoomNumber roomNumber, List<EndCountInfo> endCountInfoList){
		Room room = roomService.getRoom(roomNumber);
		GCEndCount GCEndCount = new GCEndCount();
		EndCountInfo[] EndCountInfoArr = new EndCountInfo[endCountInfoList.size()];
		for(int i=0;i<endCountInfoList.size();i++){
			EndCountInfoArr[i]=endCountInfoList.get(i);
		}
		GCEndCount.setEndCountInfo(EndCountInfoArr);
		GCEndCount.setFinalDiceNum(room.getPlayerInfo().getFinalDiceNum());
		room.sendMessage(room.getPlayers(), GCEndCount);
		
	}
	
	//计算 某个房间有几个 几 是否正确
	public boolean countNum(Player player,int num,int value){
		Room room = roomService.getRoom(player);
		RoomNumber roomNumber = room.getRoomNumber();
		PlayerInfo playerInfo = room.getPlayerInfo();
		List<Player> roomPlayerList = room.getPlayers();
		
		int theValue = 0;
		//当前用户的房间
		for(int i=0;i<roomPlayerList.size();i++){
			Player p = roomPlayerList.get(i);
			RoomEveryUserInfo roomEveryUserInfo = p.getHuman().getBazooRoomEveryUserInfo();
			/**
			 * 没有参与 就过
			 */
			if(p.getHuman().getBazooRoomEveryUserInfo().getUserStatus() == com.gameserver.bazoo.service.room.RoomEveryUserInfo.USER_STATUS_WATCH){//看看有没有参与
				continue;
			}
			/**
			 * 如果是顺子  就直接过
			 */
			List<Integer> diceValues = roomEveryUserInfo.getDiceValues();
			Collections.sort(diceValues);
			int diceValue=0;
			int dv = 0;
			for(Integer dice:diceValues){
				if(diceValue != dice.intValue()){
					diceValue=dice;
					dv++;
				}
			}
			if(dv==5){//说明是顺子
				continue;
			}
			
			diceValue=0;
			dv = 0;
			int numberOne = 0;
			if(!playerInfo.isOnePoint()){//如果没有叫过 才能算万能符
				for(Integer dice:diceValues){
					if(diceValue != dice.intValue()){
						diceValue=dice;
						dv++;
					}
					if(dice.intValue() == 1){//说明有万能符 1
						numberOne=1;
					}
				}
			}
			/**
			 * 豹子  多给一个 
			 */
			if(dv==1){//说明是豹子
				theValue++;
			}else if(dv==2 && numberOne == 1){//说明是有万能符 的豹子
				theValue++;
			}
			
			/**
			 * 1是万能符
			 */
			for(Integer dice:diceValues){
				if(dice.intValue() == value || (numberOne == 1 && dice.intValue() == 1)){
					theValue++;
				}
			}
		}
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[计算对错]---[当前用户ID::"+player.getPassportId()+"]---[最后一个用户叫号::"+num+"-个-"+value+"]---[实际::"+theValue+"-个-"+value+"]");
		playerInfo.setFinalDiceNum(theValue);
		if(theValue >= num){
			return false;
		}
		return true;
	}
	
	//验证
	public boolean validatePlayer(RoomNumber roomNumber, Player player,PlayerInfo info, int num, int value) {
		/**
		 * 看看上一个用户是哪个用户
		 * 本次叫号 有没有 小于上一个用户
		 */
		Player lastMan = info.getLastMan();
		if(lastMan == null){
			return true;
		}
		int lastDiceNumber = lastMan.getHuman().getBazooRoomEveryUserInfo().getClassicalUserInfo().getDiceNumber();
		int lastDiceValue = lastMan.getHuman().getBazooRoomEveryUserInfo().getClassicalUserInfo().getDiceValue();
		
		if(value > 6){
			return false;
		}
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[用户叫号]---[验证信息]---[当前用户名::"+player.getHuman().getName()+"]"+"diceNum:"+num+"---diceValue:"+value);
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[用户叫号]---[验证信息]---[上一用户名::"+lastMan.getHuman().getName()+"]"+ "diceNum:"+lastDiceNumber+ "---diceValue:"+lastDiceValue);
		int last = Integer.valueOf(lastDiceNumber+""+lastDiceValue);
		int now = Integer.valueOf(num+""+value);
		
		if(now<=last){
			return false;
		}
		//当前叫号的用户 
		info.setLastMan(player);
		info.setEpicycleStatus(true);
		return true;
	}




	/**
	 * 
	 * @param room
	 * @param nextPlayer
	 * @param num
	 * @param value
	 * @return
	 */
	public boolean validateMax(Room room, Player nextPlayer, int num, int value) {
		int maxPlayerNum = room.getPlayersPartIn().size();
		int maxNum = Integer.valueOf(maxPlayerNum*6+""+6);
		int nowNum = Integer.valueOf(num+""+value);
		logger.info("[无双吹牛"+room.getRoomNumber().toString()+"]---[maxNum:"+maxNum+"]---[nowNum:"+nowNum+"]");
		if(nowNum >= maxNum){
			return false;
		}
		return true;
	}
}
