package com.gameserver.baccart.redisMsg;


import com.gameserver.baccart.handler.BaccartHandlerFactory;
import com.gameserver.redis.IRedisMessage;

public class BaccartRoomCloseRedisMessage implements IRedisMessage{
	private int roomId;
	private int closed;
	
	
	/**
	 * @return the roomId
	 */
	public int getRoomId() {
		return roomId;
	}



	/**
	 * @param roomId the roomId to set
	 */
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}



	public int getClosed() {
		return closed;
	}



	public void setClosed(int closed) {
		this.closed = closed;
	}



	@Override
	public void execute() {
		// TODO Auto-generated method stub
		BaccartHandlerFactory.getRedisHandler().handleBaccartRoomClose(this);
	}

}
