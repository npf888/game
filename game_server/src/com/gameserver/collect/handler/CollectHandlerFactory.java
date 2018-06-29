package com.gameserver.collect.handler;

public class CollectHandlerFactory {
	
	private static  final CollectMessageHandler handler = new CollectMessageHandler();
	public static CollectMessageHandler getHandler() {
		return handler;
	}

}
