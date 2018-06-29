package com.robot.bazoo.blackWhite;


public class BazooBlackWhiteHandlerFactory {
	private static final BazooBlackWhiteMessageHandler handler = new BazooBlackWhiteMessageHandler();

	public static BazooBlackWhiteMessageHandler getHandler() 
	{
		return handler;
	}
}
