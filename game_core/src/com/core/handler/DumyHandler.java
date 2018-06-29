package com.core.handler;

import com.core.msg.IMessage;
import com.core.server.IMessageHandler;


/**
 * 捕获所有未被识别消息,记录到日志中
 * @author Thinker
 * 
 */
public class DumyHandler implements IMessageHandler<IMessage>
{
	@Override
	public void execute(IMessage message) 
	{
		message.execute();
	}
	
	@Override
	public short[] getTypes() 
	{
		return null;
	}
}
