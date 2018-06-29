package com.gameserver.compensation.redisMsg;

import com.gameserver.compensation.handler.CompensationHandlerFactory;
import com.gameserver.redis.IRedisMessage;

public class DeleteCompensationRedisMessage implements IRedisMessage{
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		CompensationHandlerFactory.getRedisHandler().handleDeleteCompensationRedisMessage(this);
	}

}
