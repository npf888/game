package com.common.exception;

/**
 *  跨线程操作异常
 *	@author Thinker
 */
public class CrossThreadException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public CrossThreadException()
	{
		super();
	}

	public CrossThreadException(String msg)
	{
		super(msg);
	}
}
