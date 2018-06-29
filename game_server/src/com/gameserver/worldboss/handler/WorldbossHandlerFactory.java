package com.gameserver.worldboss.handler;


public class WorldbossHandlerFactory {
	private static final WorldbossMessageHandler handler = new WorldbossMessageHandler();

	public static WorldbossMessageHandler getHandler() 
	{
		return handler;
	}
}
