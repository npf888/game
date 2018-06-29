package com.gameserver.activity.handler;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.activity.Activity;
import com.gameserver.activity.data.ActivityEntityData;
import com.gameserver.activity.redisMsg.ActivityRedisMessage;
import com.gameserver.activity.redisMsg.DeleteActivityRedisMessage;
import com.gameserver.common.Globals;

/**
 * 活动redis
 * @author wayne
 *
 */
public class ActviityRedisMessageHandler {

	private Logger logger = Loggers.activityLogger;
	
	/**
	 * 添加活动
	 * @param activityRedisMessage
	 */
	public void handleActivityRedisMessage(ActivityRedisMessage activityRedisMessage) {
		// TODO Auto-generated method stub
		Activity activity = new Activity();
		activity.fromEntity( ActivityEntityData.convertFromActivityEntityData(activityRedisMessage.getActivityEntity()));
		Globals.getActivityService().updateActivity(activity);
		logger.info("接收活动 活动id["+activity.getDbId()+"]");
	}

	/**
	 * 删除活动
	 * @param deleteActivityRedisMessage
	 */
	public void handleDeleteActivityRedisMessage(
			DeleteActivityRedisMessage deleteActivityRedisMessage) {
		long aId = deleteActivityRedisMessage.getId();
		Globals.getActivityService().deleteActivity(aId);
		logger.info("删除活动 活动id["+aId+"]");
	}

}
