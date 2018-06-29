package com.gameserver.slot.handler;

import com.gameserver.common.Globals;

public class SlotHandlerFactory {
	
	private static final SlotMessageHandler handler = new SlotMessageHandler(Globals.getOnlinePlayerService(),Globals.getLangService());

	public static SlotMessageHandler getHandler() 
	{
		return handler;
	}
}
