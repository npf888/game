package com.gameserver.item.handler;

public class ItemHandlerFactory {

	private static ItemMessageHandler itemMessageHandler = new ItemMessageHandler();
	public static ItemMessageHandler getHandler() {
		// TODO Auto-generated method stub
		return itemMessageHandler;
	}

}
