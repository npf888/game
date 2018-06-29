package com.gameserver.bazoo.schedule;

import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoo.service.room.RoomService;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

/**
 * 无双吹牛 
 * 下一轮 开始
 * @author JavaServer
 *
 */
public class ClassicalScheduleBeginTheGame implements LLISchedule{
	private Logger logger = Loggers.BAZOO;

	private String roomNumber;
	public ClassicalScheduleBeginTheGame(String roomNumber)
	{
		this.roomNumber=roomNumber;
	}


	@Override
	public void execute() {
		logger.info("[无双吹牛"+roomNumber+"]---["+roomNumber+"]---[匹配成功 后 定时器]---[等待]");
		RoomNumber objRoomNumber = RoomNumber.toRoomNumber(roomNumber);
		RoomService roomService = Globals.getBazooPubService().getRoomService();;
		Room room = roomService.getRoom(objRoomNumber);
		if(room == null){
			return;
		}
		//每次开始 清理房间
		room.cleanRoom(false,false);
		//获取房间的人数
		List<Player> roomPlayerList = room.getPlayersPartIn();
		//小于两个 就不能 开始
		if(roomPlayerList.size() < 2 ){
			if(roomPlayerList.size()>0){
				Globals.getBazooPubService().sendOutMessage(roomPlayerList.get(0),room);
			}
			return;
		}
		// 游戏开始了 ，并且依然 有用户退出 要 给每个人发送退出房间的消息
		for(Player player:roomPlayerList){
			Globals.getBazooPubService().outRoomMessage(player,room);
		}
		
		
		//经典模式
		if(objRoomNumber.getModeType() == RoomNumber.MODE_TYPE_CLASSICAL){
			Globals.getBazooMService().beginTheGame(objRoomNumber,room);
			
		}else if(objRoomNumber.getModeType() == RoomNumber.MODE_TYPE_COW){
			Globals.getBazooCowService().swingAll(objRoomNumber,room);
			
		}else if(objRoomNumber.getModeType() == RoomNumber.MODE_TYPE_SHOWHAND){
			Globals.getBazooShowHandService().swingAll(objRoomNumber,room);
			
		}else if(objRoomNumber.getModeType() == RoomNumber.MODE_TYPE_BLACK_WHITE){
			Globals.getBlackWhiteService().beginTheGame(objRoomNumber,room);
			
		}else if(objRoomNumber.getModeType() == RoomNumber.MODE_TYPE_SINGLEFIGHT){
			
		}
	}

}
