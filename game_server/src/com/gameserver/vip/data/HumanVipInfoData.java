package com.gameserver.vip.data;

import com.gameserver.vip.HumanVip;

/**
 * vip
 * @author wayne
 *
 */
public class HumanVipInfoData {
	private long lastGetTime;
	private long buyTime;
	private int level;
	private int days;
	
	public void setLastGetTime(long lastGetTime) {
		// TODO Auto-generated method stub
		this.lastGetTime = lastGetTime;
	}

	public long getLastGetTime() {
		// TODO Auto-generated method stub
		return lastGetTime;
	}

	public long getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(long buyTime) {
		this.buyTime = buyTime;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public static HumanVipInfoData convertFromHumanVip(HumanVip vip){
		HumanVipInfoData humanVipInfoData = new HumanVipInfoData();
		humanVipInfoData.setLastGetTime(vip.getGetTime());
		humanVipInfoData.setLevel(vip.getLevel());
		humanVipInfoData.setBuyTime(vip.getBuyTime());
		humanVipInfoData.setDays(vip.getDays());
		return humanVipInfoData;
	}
}
