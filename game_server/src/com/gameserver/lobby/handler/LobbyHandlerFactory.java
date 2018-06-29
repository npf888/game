package com.gameserver.lobby.handler;

public class LobbyHandlerFactory {
	
	private static final LobbyMessageHandler handler = new LobbyMessageHandler();

	public static LobbyMessageHandler getHandler() {
		
		return handler;
	}

}
