package com.gameserver.misc.handler;



public class MiscHandlerFactory {

	/** 消息处理器 */
	private static MiscMessageHandler handler = new MiscMessageHandler();

	public static MiscMessageHandler getHandler() {
		// TODO Auto-generated method stub
		return handler;
	}

}
