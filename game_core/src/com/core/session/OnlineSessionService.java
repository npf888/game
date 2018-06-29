package com.core.session;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.common.constants.Loggers;
import com.core.msg.IMessage;

/**
 * 维护服务器上所有的会话
 * @author Thinker
 * 
 */
public class OnlineSessionService<E extends MinaSession>
{
	private List<E> sessions = new CopyOnWriteArrayList<E>();

	public boolean addSession(E session)
	{
		sessions.add(session);
		return true;
	}

	public void removeSession(E session) 
	{
		sessions.remove(session);
	}
	
	public List<E> getSessionList()
	{
		return sessions;
	}
	/**
	 * @param msg
	 */
	public void broadcast(IMessage msg)
	{
		for (ISession session : sessions)
		{
			if (Loggers.msgLogger.isDebugEnabled())
			{
				Loggers.msgLogger.debug("[[ Broadcast ]]" + msg);
			}
			session.write(msg);
		}
	}
}
