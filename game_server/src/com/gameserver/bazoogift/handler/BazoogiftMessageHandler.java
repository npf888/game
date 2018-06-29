package com.gameserver.bazoogift.handler;

import com.gameserver.bazoogift.msg.CGBazooRedPackage;
import com.gameserver.bazoogift.msg.CGBazooSendGift;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

public class BazoogiftMessageHandler {

	public void handleBazooSendGift(Player player,
			CGBazooSendGift cgBazooSendGift) {
		int itemType = cgBazooSendGift.getItemType();
		long toPlayerId = cgBazooSendGift.getToPlayerId();
		int itemId = cgBazooSendGift.getItemId();
		int number = cgBazooSendGift.getNumber();
		Globals.getBazooGiftService().sendGiftToSomeone(player,toPlayerId,itemId,itemType,number);
	}

	/**
	 * 领取红包 一次性全部领取
	 * @param player
	 * @param cgBazooRedPackage
	 */
	public void handleBazooRedPackage(Player player,
			CGBazooRedPackage cgBazooRedPackage) {
		int itemId = cgBazooRedPackage.getItemId();
		Globals.getBazooGiftService().takeAllRedPackage(player,itemId);
		
	}

}
