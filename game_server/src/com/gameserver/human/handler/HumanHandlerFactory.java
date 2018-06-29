package com.gameserver.human.handler;

/**
 * 工厂
 * @author wayne
 *
 */
public class HumanHandlerFactory {
	/** 消息处理器 */
	private static HumanMessageHandler handler = new HumanMessageHandler();

	public static HumanMessageHandler getHandler() 
	{
		return handler;
	}
}
