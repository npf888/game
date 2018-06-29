package com.gameserver.bazoosignin.handler;


public class BazoosigninHandlerFactory {
private static final BazooSignInMessageHandler handler = new BazooSignInMessageHandler();
	
	public static BazooSignInMessageHandler getHandler() 
	{
		return handler;
	}
}
