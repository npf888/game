package com.gameserver.common.msg;

import com.core.msg.BaseMinaMessage;
import com.gameserver.startup.MinaGameClientSession;

/**
 * 服务器端发送给客户端的消息基类
 * @author Thinker
 */
public abstract class GCMessage extends BaseMinaMessage<MinaGameClientSession>
{
	@Override
	protected boolean readImpl()
	{
		return false;
	}

	@Override
	public void execute()
	{
		throw new UnsupportedOperationException();
	}
	
}
