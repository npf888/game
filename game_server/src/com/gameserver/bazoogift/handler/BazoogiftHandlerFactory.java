package com.gameserver.bazoogift.handler;


public class BazoogiftHandlerFactory {
private static final BazoogiftMessageHandler handler = new BazoogiftMessageHandler();
	
	public static BazoogiftMessageHandler getHandler() 
	{
		return handler;
	}
}
