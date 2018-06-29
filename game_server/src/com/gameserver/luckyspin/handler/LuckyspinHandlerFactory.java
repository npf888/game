package com.gameserver.luckyspin.handler;



public class LuckyspinHandlerFactory {
	private static LuckyspinMessageHandler luckyspinMessageHandler = new LuckyspinMessageHandler();
	public static LuckyspinMessageHandler getHandler() {
		// TODO Auto-generated method stub
		return luckyspinMessageHandler;
	}
}
