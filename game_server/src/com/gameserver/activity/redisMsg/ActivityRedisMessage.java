package com.gameserver.activity.redisMsg;


import com.gameserver.activity.data.ActivityEntityData;
import com.gameserver.activity.handler.ActivityHandlerFactory;
import com.gameserver.redis.IRedisMessage;

public class ActivityRedisMessage implements IRedisMessage{
	private ActivityEntityData activityEntity;

	public ActivityEntityData getActivityEntity() {
		return activityEntity;
	}

	public void setActivityEntity(ActivityEntityData activityEntity) {
		this.activityEntity = activityEntity;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ActivityHandlerFactory.getRedisHandler().handleActivityRedisMessage(this);
	}
	
}


