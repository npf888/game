package com.robot.bazoo.cow;


public class BazooCowHandlerFactory {
	private static final BazooCowMessageHandler handler = new BazooCowMessageHandler();

	public static BazooCowMessageHandler getHandler() 
	{
		return handler;
	}
}
