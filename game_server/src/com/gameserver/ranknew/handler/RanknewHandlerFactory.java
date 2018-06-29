package com.gameserver.ranknew.handler;

public class RanknewHandlerFactory {

	private static final RankNewMessageHandler handler = new RankNewMessageHandler();

	public static RankNewMessageHandler getHandler() 
	{
		return handler;
	}
}
