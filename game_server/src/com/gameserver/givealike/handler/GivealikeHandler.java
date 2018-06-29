package com.gameserver.givealike.handler;

import java.util.List;

import com.gameserver.common.Globals;
import com.gameserver.givealike.msg.CGGivealikeSave;
import com.gameserver.givealike.msg.CGIsnotGivealike;
import com.gameserver.givealike.msg.GCIsnotGivealike;
import com.gameserver.givealike.pojo.HumanGivealike;
import com.gameserver.player.Player;

public class GivealikeHandler {

	public void handleGivealikeSave(Player player,
			CGGivealikeSave cgGivealikeSave) {
		Globals.getHumanGivealikeService().saveGiveLike(player.getHuman(), cgGivealikeSave.getSlotType(),
				cgGivealikeSave.getPaintAssess(),
				cgGivealikeSave.getPlayAssess(), 
				cgGivealikeSave.getTotalAssess());
		
	}

	public void handleIsnotGivealike(Player player,
			CGIsnotGivealike cgIsnotGivealike) {
		List<HumanGivealike> selectHumanGivealikeList  = Globals.getHumanGivealikeService().getHumanGiveLike(player.getHuman(), cgIsnotGivealike.getSlotType());
		GCIsnotGivealike gCIsnotGivealike =new GCIsnotGivealike();
		if(selectHumanGivealikeList != null && selectHumanGivealikeList.size() >0){
			gCIsnotGivealike.setIsNot(1);
		}else{
			gCIsnotGivealike.setIsNot(0);
		}
		
		player.sendMessage(gCIsnotGivealike);
	}

}
