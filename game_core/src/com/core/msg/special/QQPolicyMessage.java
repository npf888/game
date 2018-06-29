package com.core.msg.special;

import com.core.msg.BaseMinaMessage;
import com.core.msg.MessageParseException;
import com.core.msg.MessageType;

@SuppressWarnings("rawtypes")
public class QQPolicyMessage extends BaseMinaMessage
{
	
	protected boolean readImpl()
	{
		int times = 100;
		byte b = readByte();
		while (b != 0 && times > 0) 
		{
			b = readByte();
			times--;
		}
		return true;
	}

	public boolean write() throws MessageParseException
	{
		return writeImpl();
	}

	protected boolean writeImpl() 
	{
		try
		{
			writeShort(4);
			writeShort(MessageType.GC_QQPOLICY);
			return true;
		}catch(Exception e)
		{
			
		}
		return true;
	}

	public short getType()
	{
		return MessageType.QQ_POLICY;
	}

	public String getTypeName() 
	{
		return "CS_QQPOLICY";
	}

	@Override
	public void execute()
	{
		
	}
}
