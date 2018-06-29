package com.core.msg.sys;

import com.core.msg.MessageType;
import com.core.msg.SysInternalMessage;

/**
 * 未知消息，解析消息时遇到无法解析的消息类型时创建此消息 
 * @author Thinker
 *
 */
public class UnknownMessage extends SysInternalMessage
{

	@Override
	public short getType() 
	{
		return MessageType.MSG_UNKNOWN;
	}

	@Override
	public String getTypeName()
	{
		return "MSG_UNKNOWN";
	}

	@Override
	public void execute()
	{
		
	}

}
