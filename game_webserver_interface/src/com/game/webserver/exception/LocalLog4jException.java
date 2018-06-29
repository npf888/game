package com.game.webserver.exception;

public class LocalLog4jException extends LocalException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -397233476508734051L;

	public LocalLog4jException(String msg)
	{
	   super(msg);
	}

	public LocalLog4jException() {
	   super("Log4J 文件配置错误");
	}
}
