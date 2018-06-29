package com.gameserver.activity.redisMsg;

import com.gameserver.activity.handler.ActivityHandlerFactory;
import com.gameserver.redis.IRedisMessage;

public class DeleteActivityRedisMessage  implements IRedisMessage{
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Override
	public void execute() {
		ActivityHandlerFactory.getRedisHandler().handleDeleteActivityRedisMessage(this);
	}
}

