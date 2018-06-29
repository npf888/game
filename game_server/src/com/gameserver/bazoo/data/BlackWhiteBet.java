package com.gameserver.bazoo.data;

public class BlackWhiteBet {

	private long passportId;
	private int[] bet;
	private int[] winPassportIds;
	private long gold;
	//用户 自己的总金币
	
	public long getPassportId() {
		return passportId;
	}
	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}
	public long getGold() {
		return gold;
	}
	public void setGold(long gold) {
		this.gold = gold;
	}
	public int[] getBet() {
		return bet;
	}
	public void setBet(int[] bet) {
		this.bet = bet;
	}
	public int[] getWinPassportIds() {
		return winPassportIds;
	}
	public void setWinPassportIds(int[] winPassportIds) {
		this.winPassportIds = winPassportIds;
	}
	
	
	
	
}
