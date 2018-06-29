package com.core.msg.sys;

import com.core.msg.MessageType;
import com.core.msg.SessionMessage;
import com.core.msg.SysInternalMessage;
import com.core.session.ISession;


/**
 * 
 * @param <T>
 * 
 */
public class SessionClosedMessage<T extends ISession> extends
		SysInternalMessage implements SessionMessage<T> {

	protected T session;

	public SessionClosedMessage(T sender) {
		super.setType(MessageType.SYS_SESSION_CLOSE);
		super.setTypeName("SYS_SESSION_CLOSE");
		this.session = sender;
	}

	@Override
	public T getSession() {
		return this.session;
	}

	public void setSession(T session) {
		this.session = session;
	}

	@Override
	public void execute() {
		session.close();
	}

	@Override
	public String toString() {
		return "SessionClosedMessage";
	}
}
