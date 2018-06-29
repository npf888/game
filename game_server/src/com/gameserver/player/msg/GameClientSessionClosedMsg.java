package com.gameserver.player.msg;

import com.core.msg.IGlobalLogicQueueMessage;
import com.core.msg.sys.SessionClosedMessage;
import com.gameserver.player.Player;
import com.gameserver.player.handler.PlayerHandlerFactory;
import com.gameserver.startup.GameClientSession;

/**
 * 处理玩家连接和断开连接的消息，此消息在系统主线程中执行
 * 
 * @author Thinker
 */
public class GameClientSessionClosedMsg extends SessionClosedMessage<GameClientSession> implements IGlobalLogicQueueMessage
{
	public GameClientSessionClosedMsg(GameClientSession session) 
	{
		super(session);
	}

	@Override
	public void execute()
	{
		Player player = session.getPlayer();
		PlayerHandlerFactory.getHandler().handlePlayerCloseSession(player);
	}
}
