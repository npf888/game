package com.gameserver.treasury.handler;


public class TreasuryHandlerFactory {
	private static final TreasuryMessageHandler handler = new TreasuryMessageHandler();

	public static TreasuryMessageHandler getHandler() 
	{
		return handler;
	}
}
