package com.gameserver.monthcard.handler;


/**
 * 月卡处理器
 * @author wayne
 *
 */
public class MonthcardHandlerFactory {

	private static final MonthcardMessageHandler handler = new MonthcardMessageHandler();

	public static MonthcardMessageHandler getHandler() 
	{
		return handler;
	}
	
}
