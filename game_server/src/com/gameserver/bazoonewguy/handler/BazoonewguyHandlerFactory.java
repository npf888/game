package com.gameserver.bazoonewguy.handler;


public class BazoonewguyHandlerFactory {
private static final BazoonewguyMessageHandler handler = new BazoonewguyMessageHandler();
	
	public static BazoonewguyMessageHandler getHandler() 
	{
		return handler;
	}
}
