package com.gameserver.chat.handler;


/**
 * 聊天消息处理器提供类
 * @author Thinker
 */
public class ChatHandlerFactory
{
	private static final ChatMessageHandler handler = new ChatMessageHandler();
	private static final ChatRedisMessageHandler redisHandler = new ChatRedisMessageHandler();
	
	public static ChatMessageHandler getHandler() 
	{
		return handler;
	}
	
	public static ChatRedisMessageHandler getRedisHandler(){
		return redisHandler;
	}
}
