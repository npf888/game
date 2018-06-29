package com.gameserver.common.msg;

import com.core.msg.BaseMinaMessage;
import com.gameserver.startup.MinaGameClientSession;

/**
 * 客户端发送给服务器的消息的
 * @author Thinker
 */
public abstract class CGMessage extends BaseMinaMessage<MinaGameClientSession>
{
	@Override
	protected boolean writeImpl() 
	{
		return false;
	}
	
	public boolean isCollect()
	{
		return false;
	}
}
