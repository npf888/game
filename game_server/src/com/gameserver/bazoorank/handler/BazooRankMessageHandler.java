package com.gameserver.bazoorank.handler;

import com.gameserver.bazoorank.data.HumanRankInfo;
import com.gameserver.bazoorank.msg.CGBazooRankRequest;
import com.gameserver.bazoorank.msg.CGBazooRankTotalGoldRequest;
import com.gameserver.bazoorank.msg.GCBazooRankRequest;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

public class BazooRankMessageHandler {

	/**
	 * 查询盈利
	 * @param player
	 * @param cgBazooRankRequest
	 */
	public void handleBazooRankRequest(Player player,
			CGBazooRankRequest cgBazooRankRequest) {
		int dateType = cgBazooRankRequest.getDateType();//1:天   2:周  3:月
		HumanRankInfo[]  HumanRankInfo = Globals.getHumanBankService().getBazooRankBy(dateType);
		GCBazooRankRequest GCBazooRankRequest = new GCBazooRankRequest();
		GCBazooRankRequest.setHumanRankInfo(HumanRankInfo);
		player.sendMessage(GCBazooRankRequest);
	}

	public void handleBazooRankTotalGoldRequest(Player player,
			CGBazooRankTotalGoldRequest cgBazooRankTotalGoldRequest) {
		HumanRankInfo[]  HumanRankInfo = Globals.getHumanBankService().getAllPlayer();
		GCBazooRankRequest GCBazooRankRequest = new GCBazooRankRequest();
		GCBazooRankRequest.setHumanRankInfo(HumanRankInfo);
		player.sendMessage(GCBazooRankRequest);
	}

}
