package com.logserver.msg;

import com.core.msg.BaseMessage;

/**
 * DbsServer发送给WorldServer的消息基类
 * 
 * @author Thinker
 */
public abstract class OWMessage extends BaseMessage
{
	public boolean readImpl() 
	{
		return false;
	}

	@Override
	public void execute()
	{
		throw new UnsupportedOperationException();
	}
}
