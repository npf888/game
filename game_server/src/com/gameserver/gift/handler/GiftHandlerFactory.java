package com.gameserver.gift.handler;

public class GiftHandlerFactory {

	private static GiftMessageHandler giftMessageHandler = new GiftMessageHandler();
	public static GiftMessageHandler getHandler() {
		
		return giftMessageHandler;
	}
}
