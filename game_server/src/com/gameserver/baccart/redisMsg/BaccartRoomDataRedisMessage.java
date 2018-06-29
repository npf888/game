package com.gameserver.baccart.redisMsg;

import com.gameserver.baccart.handler.BaccartHandlerFactory;
import com.gameserver.redis.IRedisMessage;

public class BaccartRoomDataRedisMessage implements IRedisMessage {
	
	private int roomId;
	private long jackpot;
	private long stock;
	
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

	public long getJackpot() {
		return jackpot;
	}

	public void setJackpot(long jackpot) {
		this.jackpot = jackpot;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		BaccartHandlerFactory.getRedisHandler().handleBaccartRoomData(this);
	}

}
