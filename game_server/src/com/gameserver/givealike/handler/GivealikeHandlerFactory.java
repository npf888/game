package com.gameserver.givealike.handler;


public class GivealikeHandlerFactory {
	private static GivealikeHandler givealikeHandler = new GivealikeHandler();
	public static GivealikeHandler getHandler() {
		
		return givealikeHandler;
	}
}
