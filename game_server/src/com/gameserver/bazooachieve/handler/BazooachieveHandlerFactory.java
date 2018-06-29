package com.gameserver.bazooachieve.handler;


public class BazooachieveHandlerFactory {
	private static final BazooachieveMessageHandler handler = new BazooachieveMessageHandler();
	
	public static BazooachieveMessageHandler getHandler() 
	{
		return handler;
	}
}
