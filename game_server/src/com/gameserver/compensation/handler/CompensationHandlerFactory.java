package com.gameserver.compensation.handler;



public class CompensationHandlerFactory {
	
	private static CompensationRedisMessageHandler reidsHandler = new CompensationRedisMessageHandler();

	public static CompensationRedisMessageHandler getRedisHandler() {
		// TODO Auto-generated method stub
		return reidsHandler;
	}
}
