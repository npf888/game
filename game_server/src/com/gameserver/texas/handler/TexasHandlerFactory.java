package com.gameserver.texas.handler;


/**
 * 德州处理器
 * @author wayne
 *
 */
public class TexasHandlerFactory {

	private static final TexasMessageHandler handler = new TexasMessageHandler();

	public static TexasMessageHandler getHandler() 
	{
		return handler;
	}

}
