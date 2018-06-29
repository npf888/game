package com.gameserver.player.handler;



/**
 *  玩家消息处理器提供类
 *	@author Thinker
 */
public class PlayerHandlerFactory
{
	private static final PlayerMessageHandler handler = new PlayerMessageHandler();

	public static PlayerMessageHandler getHandler() 
	{
		return handler;
	}
}
