package com.gameserver.startup;

import com.core.session.ISession;
import com.gameserver.player.Player;

/**
 * 与 GameServer 连接的客户端的会话信息
 * @author Thinker
 */
public interface GameClientSession extends ISession 
{
	void setPlayer(Player player);

	Player getPlayer();

	String getIp();
}
