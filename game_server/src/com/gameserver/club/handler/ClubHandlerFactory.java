package com.gameserver.club.handler;

public class ClubHandlerFactory {
	private static final ClubMessageHandler handler = new ClubMessageHandler();

	public static ClubMessageHandler getHandler() 
	{
		return handler;
	}
}
