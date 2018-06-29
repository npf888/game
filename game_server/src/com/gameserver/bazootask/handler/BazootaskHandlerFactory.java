package com.gameserver.bazootask.handler;


public class BazootaskHandlerFactory {
	private static final BazootaskMessageHandler handler = new BazootaskMessageHandler();
	
	public static BazootaskMessageHandler getHandler() 
	{
		return handler;
	}
}
