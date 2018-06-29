package com.gameserver.texas.data;

public class TexasRoomPlayerSettleCardsInfoData {
	private int pos;
	private String cardListStr;
	private int firstCard;
	private int secondCard;
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public String getCardListStr() {
		return cardListStr;
	}
	public void setCardListStr(String cardListStr) {
		this.cardListStr = cardListStr;
	}
	public int getFirstCard() {
		return firstCard;
	}
	public void setFirstCard(int firstCard) {
		this.firstCard = firstCard;
	}
	public int getSecondCard() {
		return secondCard;
	}
	public void setSecondCard(int secondCard) {
		this.secondCard = secondCard;
	}
}
