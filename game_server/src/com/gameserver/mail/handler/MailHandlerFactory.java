package com.gameserver.mail.handler;



/**
 * 邮件消息工厂
 * @author Thinker
 *
 */
public class MailHandlerFactory
{
	/** 消息处理器 */
	private static MailMessageHandler handler = new MailMessageHandler();
	private static MailRedisMessageHandler redisHandler = new MailRedisMessageHandler();
	
	public static MailMessageHandler getHandler() 
	{
		return handler;
	}
	
	public static MailRedisMessageHandler getRedisHandler()
	{
		return redisHandler;
	}
}
