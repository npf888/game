package com.gameserver.relation.handler;


/**
 * 好友消息工厂
 * @author Thinker
 *
 */
public class RelationHandlerFactory
{
	private static RelationMessageHandler handler = new RelationMessageHandler();

	private static RelationRedisMessageHandler redisHandler = new RelationRedisMessageHandler();
	
	public static RelationMessageHandler getHandler()
	{
		return handler;
	}
	
	public static RelationRedisMessageHandler getRedisHandler()
	{
		return redisHandler;
	}
	
}

