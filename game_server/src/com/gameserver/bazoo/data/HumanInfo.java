package com.gameserver.bazoo.data;

import com.gameserver.human.Human;
import com.gameserver.player.Player;

public class HumanInfo extends Human{

	
	private long curPassportId;
	
	public HumanInfo(Player player) {
		super(player);
	}

	@Override
	public long getPassportId() {
		return this.curPassportId;
	}

	public long getCurPassportId() {
		return curPassportId;
	}

	public void setCurPassportId(long curPassportId) {
		this.curPassportId = curPassportId;
	}

	
}
