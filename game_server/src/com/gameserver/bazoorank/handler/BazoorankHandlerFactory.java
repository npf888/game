package com.gameserver.bazoorank.handler;


public class BazoorankHandlerFactory {
private static final BazooRankMessageHandler handler = new BazooRankMessageHandler();
	
	public static BazooRankMessageHandler getHandler() 
	{
		return handler;
	}
}
