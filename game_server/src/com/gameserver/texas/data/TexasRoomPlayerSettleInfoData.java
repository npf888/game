package com.gameserver.texas.data;

/**
 * 德州玩家结算数据
 * @author wayne
 *
 */
public class TexasRoomPlayerSettleInfoData {
	private int pos;
	private long winBet;
	private int handCardType;
	
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public long getWinBet() {
		return winBet;
	}
	public void setWinBet(long winBet) {
		this.winBet = winBet;
	}
	public int getHandCardType() {
		return handCardType;
	}
	public void setHandCardType(int handCardType) {
		this.handCardType = handCardType;
	}
	

}
