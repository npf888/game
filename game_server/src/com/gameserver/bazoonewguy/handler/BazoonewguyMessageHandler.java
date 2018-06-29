package com.gameserver.bazoonewguy.handler;

import java.util.List;

import com.gameserver.bazoonewguy.HumanBazooNewGuy;
import com.gameserver.bazoonewguy.msg.CGBazooNewGuyProcess;
import com.gameserver.player.Player;

public class BazoonewguyMessageHandler {

	public void handleBazooNewGuyProcess(Player player,
			CGBazooNewGuyProcess cgBazooNewGuyProcess) {
		List<HumanBazooNewGuy> humanBazooNewGuys = player.getHuman().getHumanBazooNewGuyManager().getHumanBazooNewGuys();
		
		for(HumanBazooNewGuy newGuy:humanBazooNewGuys){
			if(newGuy.getType().intValue() ==cgBazooNewGuyProcess.getModeType()){
				newGuy.setProcess(cgBazooNewGuyProcess.getProcess());
				newGuy.setModified();
			}
		}
		
	}

}
