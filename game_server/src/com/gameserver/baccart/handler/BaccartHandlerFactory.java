package com.gameserver.baccart.handler;





/**
 * 百家乐
 * @author wayne
 *
 */
public class BaccartHandlerFactory {
	private static BaccartMessageHandler  handler = new BaccartMessageHandler();

	public static BaccartMessageHandler getHandler() {
		// TODO Auto-generated method stub
		return handler;
	}
	
	private static BaccartRedisMessageHandler  redisHandler = new BaccartRedisMessageHandler();

	public static BaccartRedisMessageHandler getRedisHandler() {
		// TODO Auto-generated method stub
		return redisHandler;
	}


}
