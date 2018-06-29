package com.gameserver.bazoo.data;

public class ShowHandBet {

	private long passportId;
	private int bet;
	private long gold;
	//用户 自己的总金币
	private long personTotalGold;
	
	public long getPassportId() {
		return passportId;
	}
	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}
	public int getBet() {
		return bet;
	}
	public void setBet(int bet) {
		this.bet = bet;
	}
	public long getGold() {
		return gold;
	}
	public void setGold(long gold) {
		this.gold = gold;
	}
	public long getPersonTotalGold() {
		return personTotalGold;
	}
	public void setPersonTotalGold(long personTotalGold) {
		this.personTotalGold = personTotalGold;
	}
	
	
}
