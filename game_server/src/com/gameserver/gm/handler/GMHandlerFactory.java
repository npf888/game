package com.gameserver.gm.handler;


/**
 * gm 处理器
 * @author wayne
 *
 */
public class GMHandlerFactory {
	private static final GMRedisMessageHandler redisHandler = new GMRedisMessageHandler();

	public static GMRedisMessageHandler getRedisHandler() 
	{
		return redisHandler;
	}
}
