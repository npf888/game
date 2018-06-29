package com.gameserver.weekcard.handler;



/**
 * 周卡
 * @author wayne
 *
 */
public class WeekcardHandlerFactory {

	private static final WeekcardMessageHandler handler = new WeekcardMessageHandler();

	public static WeekcardMessageHandler getHandler() 
	{

		return handler;
	}
	
}
