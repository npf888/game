package com.gameserver.bazoo.handler;


/**
 * 聊天消息处理器提供类
 * @author Thinker
 */
public class BazooHandlerFactory
{
	private static final BazooMessageHandler handler = new BazooMessageHandler();
	
	public static BazooMessageHandler getHandler() 
	{
		return handler;
	}
	
}
