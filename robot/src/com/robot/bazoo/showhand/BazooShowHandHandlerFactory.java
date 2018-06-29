package com.robot.bazoo.showhand;


public class BazooShowHandHandlerFactory {
	private static final BazooShowHandMessageHandler handler = new BazooShowHandMessageHandler();

	public static BazooShowHandMessageHandler getHandler() 
	{
		return handler;
	}
}
