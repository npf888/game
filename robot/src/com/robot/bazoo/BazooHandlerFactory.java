package com.robot.bazoo;


public class BazooHandlerFactory {
	private static final BazooMessageHandler handler = new BazooMessageHandler();
	private static final BazooMessage2Handler handler2 = new BazooMessage2Handler();

	public static BazooMessageHandler getHandler() 
	{
		return handler;
	}
	public static BazooMessage2Handler getHandler2() 
	{
		return handler2;
	}
}
