package com.gameserver.signin.handler;

import com.gameserver.common.Globals;


public class SigninHandlerFactory {

	private static final SignInMessageHandler handler = new SignInMessageHandler(Globals.getOnlinePlayerService(),Globals.getLangService());

	public static SignInMessageHandler getHandler() 
	{
		return handler;
	}
	
}
