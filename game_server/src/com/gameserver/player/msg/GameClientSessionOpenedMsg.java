package com.gameserver.player.msg;

import com.common.constants.Loggers;
import com.core.msg.sys.SessionOpenedMessage;
import com.gameserver.startup.GameClientSession;

/**
 * 处理玩家连接和断开连接的消息，此消息在系统主线程中执行
 * 
 * @author Thinker
 * 
 */
public class GameClientSessionOpenedMsg extends SessionOpenedMessage<GameClientSession>
{
	public GameClientSessionOpenedMsg(GameClientSession session) {
		super(session);
	}

	@Override
	public void execute() 
	{	
		if (Loggers.stateLogger.isDebugEnabled())
		{
			Loggers.stateLogger.debug("Session open " + session);
		}
	}
}
