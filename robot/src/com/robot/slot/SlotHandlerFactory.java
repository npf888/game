package com.robot.slot;


public class SlotHandlerFactory {

	private static final SlotMessageHandler handler = new SlotMessageHandler();

	public static SlotMessageHandler getHandler() 
	{
		return handler;
	}
}
