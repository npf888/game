package com.core.msg;

import com.core.session.MinaSession;

/**
 * 带有消息发送者的消息基类
 * @author Thinker
 */
public abstract class BaseMinaMessage<T extends MinaSession> extends BaseMessage implements SessionMessage<T>
{
	private T session;

	public T getSession()
	{
		return session;
	}

	public void setSession(T s) 
	{
		this.session = s;
	}

	public abstract short getType();
}