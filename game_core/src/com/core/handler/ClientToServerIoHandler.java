package com.core.handler;

import org.apache.mina.common.IoSession;

import com.core.server.AbstractIoHandler;
import com.core.session.ExternalDummyServerSession;

/**
 * 用于客户端连接到Server的网络消息接收器
 * @author Thinker
 * 
 */
@Deprecated
public class ClientToServerIoHandler extends AbstractIoHandler<ExternalDummyServerSession>
{

	@Override
	protected ExternalDummyServerSession createSession(IoSession session)
	{
		return new ExternalDummyServerSession(session);
	}

	@Override
	public void sessionClosed(IoSession session)
	{
		super.sessionClosed(session);
	}
}
