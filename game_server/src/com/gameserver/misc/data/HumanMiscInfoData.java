package com.gameserver.misc.data;

import com.gameserver.misc.HumanMisc;

/**
 * misc
 * @author wayne
 *
 */
public class HumanMiscInfoData {
	private long onlineTime;
	private int currentOnlineRewardId;
	private long firstRechargeTime;
	private int renameTimes;
	private int newUser;
	private long lastGetTime;
	
	
	public long getLastGetTime() {
		return lastGetTime;
	}
	
	public void setLastGetTime(long lastGetTime) {
		this.lastGetTime = lastGetTime;
	}
	public long getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(long onlineTime) {
		this.onlineTime = onlineTime;
	}

	
	public int getCurrentOnlineRewardId() {
		return currentOnlineRewardId;
	}
	public void setCurrentOnlineRewardId(int currentOnlineRewardId) {
		this.currentOnlineRewardId = currentOnlineRewardId;
	}
	public long getFirstRechargeTime() {
		return firstRechargeTime;
	}
	public void setFirstRechargeTime(long firstRechargeTime) {
		this.firstRechargeTime = firstRechargeTime;
	}
	public int getRenameTimes() {
		return renameTimes;
	}
	public void setRenameTimes(int renameTimes) {
		this.renameTimes = renameTimes;
	}
	public int getNewUser() {
		return newUser;
	}
	public void setNewUser(int newUser) {
		this.newUser = newUser;
	}
	
	
	public static HumanMiscInfoData convertFromHumanMisc(HumanMisc humanMisc1)
	{
		HumanMiscInfoData humanMiscInfoData =new HumanMiscInfoData();
		humanMiscInfoData.setOnlineTime(humanMisc1.getLastGetTime());
		//humanMiscInfoData.setOnlineTime(humanMisc1.getOnlineTime());
		humanMiscInfoData.setCurrentOnlineRewardId(humanMisc1.getCurrentOnlineRewardId());
		humanMiscInfoData.setFirstRechargeTime(humanMisc1.getFirstRechargeTime());
		humanMiscInfoData.setRenameTimes(humanMisc1.getRenameTimes());
		humanMiscInfoData.setNewUser(humanMisc1.getNewUser());
		//humanMiscInfoData.setLastGetTime(humanMisc1.getLastGetTime());
		return humanMiscInfoData;
	}
	

}
