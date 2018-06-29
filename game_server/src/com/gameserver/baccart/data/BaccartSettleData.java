package com.gameserver.baccart.data;

public class BaccartSettleData {
	private long playerId;
	private long winCoins;
	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	public long getWinCoins() {
		return winCoins;
	}
	public void setWinCoins(long winCoins) {
		this.winCoins = winCoins;
	}
	
	@Override
	public String toString(){
		return "玩家["+this.playerId+"],赢钱数["+winCoins+"]";
	}
}
