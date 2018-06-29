package com.gameserver.newbie.handler;

public class NewbieHandlerFactory {
	private static final NewbieMessageHandler handler = new NewbieMessageHandler();

	public static NewbieMessageHandler getHandler() 
	{
		return handler;
	}
}
