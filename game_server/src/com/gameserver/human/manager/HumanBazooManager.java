package com.gameserver.human.manager;

import com.common.InitializeRequired;
import com.gameserver.bazoo.insideData.BazooTemporaryData;
import com.gameserver.bazoo.msg.GCBazooHallStatus;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.player.Player;

public class HumanBazooManager implements  InitializeRequired {

	private Human owner;

	private BazooTemporaryData BazooTemporaryData = null;
	public HumanBazooManager(Human owner) {
		this.owner = owner;
	}
	
	@Override
	public void init() {
		BazooTemporaryData=new BazooTemporaryData(owner);
	}

	public Human getOwner() {
		return owner;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
	}

	public BazooTemporaryData getBazooTemporaryData() {
		return BazooTemporaryData;
	}

	public void setBazooTemporaryData(BazooTemporaryData bazooTemporaryData) {
		BazooTemporaryData = bazooTemporaryData;
	}

	/**
	 * 返回用户的状态
	 * @return
	 */
	public GCBazooHallStatus getStatus() {
		GCBazooHallStatus GCBazooHallStatus = new GCBazooHallStatus();
		int signInStatus = owner.getHumanBazooSignInManager().getStatus();
		int taskNumber =owner.getHumanBazooTaskManager().getTaskNumber();
		GCBazooHallStatus.setSignInStatus(signInStatus);
		GCBazooHallStatus.setTaskNumber(taskNumber);
		return GCBazooHallStatus;
	}

}
