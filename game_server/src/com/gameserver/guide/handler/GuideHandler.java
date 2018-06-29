package com.gameserver.guide.handler;

import com.gameserver.guide.msg.CGItemInvoke;
import com.gameserver.player.Player;

public class GuideHandler {

	public void handleItemInvoke(Player player, CGItemInvoke cgItemInvoke) {
		player.getHuman().getHumanPayguideManager().sendMonthcard();
	}



}
