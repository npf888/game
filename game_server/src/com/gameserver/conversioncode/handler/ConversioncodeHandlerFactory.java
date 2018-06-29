package com.gameserver.conversioncode.handler;

public class ConversioncodeHandlerFactory {

	private static final  ConversioncodeHandler handler = new ConversioncodeHandler();

	public static ConversioncodeHandler getHandler(){
		return handler;
	}

}
