package com.gameserver.worldboss.handler;

import com.gameserver.common.Globals;
import com.gameserver.player.Player;
import com.gameserver.worldboss.msg.CGBeforeStart;
import com.gameserver.worldboss.msg.CGBossStartEndInfo;
import com.gameserver.worldboss.msg.CGGetBossInfo;
import com.gameserver.worldboss.msg.CGGetRankInfo;
import com.gameserver.worldboss.msg.CGOpenPanel;
import com.gameserver.worldboss.msg.CGRefreshBossInfo;
import com.gameserver.worldboss.msg.CGReturnBlood;
import com.gameserver.worldboss.msg.GCBossStartEndInfo;
import com.gameserver.worldboss.msg.GCGetBossInfo;
import com.gameserver.worldboss.msg.GCGetRankInfo;
import com.gameserver.worldboss.msg.GCRefreshBossInfo;


/**
 * 任务处理器
 * @author wayne
 *
 */
public class WorldbossMessageHandler {
	


	public void handleGetBossInfo(Player player, CGGetBossInfo cgGetBossInfo) {
		GCGetBossInfo gCGetBossInfo = Globals.getWorldBossNewService().getLastWinPeopleAndTwoBoss();
		player.sendMessage(gCGetBossInfo);
	}


	public void handleGetRankInfo(Player player, CGGetRankInfo cgGetRankInfo) {
		GCGetRankInfo gCetRankInfo = Globals.getWorldBossNewService().getAttackRank(player);
		player.sendMessage(gCetRankInfo);
	}


	public void handleOpenPanel(Player player, CGOpenPanel cgOpenPanel) {
		Globals.getWorldBossNewService().openOrCloseWorldBoss(player.getHuman(), cgOpenPanel.getPanelType());
		GCGetRankInfo gCetRankInfo = Globals.getWorldBossNewService().getAttackRank(player);
		player.sendMessage(gCetRankInfo);
	}


	public void handleBossStartEndInfo(Player player,
			CGBossStartEndInfo cgBossStartEndInfo) {
		GCBossStartEndInfo GCBossStartEndInfo= Globals.getWorldBossNewService().cgRefreshTime();
		player.sendMessage(GCBossStartEndInfo);
		
	}

	
	public void handleRefreshBossInfo(Player player,
			CGRefreshBossInfo cgRefreshBossInfo) {
		GCRefreshBossInfo gCRefreshBossInfo =  Globals.getWorldBossNewService().refreshBoss(player);
		if(gCRefreshBossInfo == null){
			return;
		}
		player.sendMessage(gCRefreshBossInfo);
		
	}


	public void handleBeforeStart(Player player, CGBeforeStart cgBeforeStart) {
		Globals.getWorldBossNewService().bossBeforeStart(player);
		
	}


	public void handleReturnBlood(Player player, CGReturnBlood cgReturnBlood) {
		player.sendMessage(Globals.getWorldBossNewService().returnBlood());
		
	}

}
