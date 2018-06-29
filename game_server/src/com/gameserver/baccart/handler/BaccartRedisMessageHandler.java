package com.gameserver.baccart.handler;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.baccart.BaccartRoom;
import com.gameserver.baccart.redisMsg.BaccartRoomCloseRedisMessage;
import com.gameserver.baccart.redisMsg.BaccartRoomDataRedisMessage;
import com.gameserver.common.Globals;

public class BaccartRedisMessageHandler {

	private Logger logger = Loggers.baccartRoomLogger;
	
	public void handleBaccartRoomClose(
			BaccartRoomCloseRedisMessage baccartRoomCloseRedisMessage) {
		// TODO Auto-generated method stub
		int closed = baccartRoomCloseRedisMessage.getClosed();
		int roomId = baccartRoomCloseRedisMessage.getRoomId();
		
		logger.info("百家乐房间["+roomId+"]关闭["+closed+"]");
		BaccartRoom room = Globals.getBaccartService().baccartRoomById(roomId);
		if(room ==null){
			logger.warn("后台关闭房间不存在["+roomId+"]");
			return;
		}
			
		if(closed==1){
			room.close();
		
		}else{
			room.open();
		}
		
	}

	public void handleBaccartRoomData(
			BaccartRoomDataRedisMessage baccartRoomDataRedisMessage) {
		// TODO Auto-generated method stub
		
		int roomId = baccartRoomDataRedisMessage.getRoomId();
		long stock = baccartRoomDataRedisMessage.getStock();
		long jackpot =  baccartRoomDataRedisMessage.getJackpot();
		
		logger.info("百家乐房间["+roomId+"]jackpot["+jackpot+"],库存["+stock+"]");
		if(stock<0){
			return;
		}
		
		if(jackpot<0){
			return;
		}

		
		BaccartRoom room = Globals.getBaccartService().baccartRoomById(roomId);
		if(room ==null){
			logger.warn("后台改变房间数据不存在["+roomId+"]");
			return;
		}
		room.getBaccarRoomModel().setJackpot(jackpot);
		room.getBaccarRoomModel().setStock(stock);
		room.getBaccarRoomModel().setModified();
	}


}
