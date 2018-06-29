package com.gameserver.activity.handler;


/**
 * 活动消息工厂
 * @author wayne
 *
 */
public class ActivityHandlerFactory {

	private static ActivityMessageHandler  handler = new ActivityMessageHandler();
	private static ActviityRedisMessageHandler reidsHandler = new ActviityRedisMessageHandler();
	public static ActivityMessageHandler getHandler() {
		// TODO Auto-generated method stub
		return handler;
	}
	public static ActviityRedisMessageHandler getRedisHandler() {
		// TODO Auto-generated method stub
		return reidsHandler;
	}

}
