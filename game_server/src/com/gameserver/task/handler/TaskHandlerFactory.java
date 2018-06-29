package com.gameserver.task.handler;


public class TaskHandlerFactory {
	private static final TaskMessageHandler handler = new TaskMessageHandler();

	public static TaskMessageHandler getHandler() 
	{
		return handler;
	}
}
