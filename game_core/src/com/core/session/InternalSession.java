package com.core.session;

import com.core.msg.IMessage;

/**
 * @author Thinker
 * 
 */
@Deprecated
public class InternalSession implements ISession
{
	@Override
	public void close() 
	{
		
	}

	@Override
	public boolean isConnected()
	{
		return false;
	}

	@Override
	public void write(IMessage msg)
	{
		
	}

	@Override
	public boolean closeOnException() 
	{
		return false;
	}
}