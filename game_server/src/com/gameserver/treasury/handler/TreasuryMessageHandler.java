package com.gameserver.treasury.handler;

import com.gameserver.player.Player;
import com.gameserver.treasury.msg.CGTreasury;


/**
 * 任务处理器
 * @author wayne
 *
 */
public class TreasuryMessageHandler {
	

	public void handleTreasury(Player player, CGTreasury cgTreasury) {
		player.sendMessage(player.getHuman().getHumanTreasuryManager().sendTreasury());
	}
	

}
