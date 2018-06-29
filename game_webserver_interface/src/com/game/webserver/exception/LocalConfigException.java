package com.game.webserver.exception;

public class LocalConfigException extends LocalException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -108886814362073529L;

	public LocalConfigException(String msg)
	{
	    super("IMOP LOCAL 配置异常：" + msg);
	}
}
