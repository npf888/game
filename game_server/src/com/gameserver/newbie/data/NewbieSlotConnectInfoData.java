package com.gameserver.newbie.data;

public class NewbieSlotConnectInfoData {
	private int payLineId;
	private int[] posList;
	private int payId;
	public int getPayLineId() {
		return payLineId;
	}
	public void setPayLineId(int payLineId) {
		this.payLineId = payLineId;
	}
	public int[] getPosList() {
		return posList;
	}
	public void setPosList(int[] posList) {
		this.posList = posList;
	}
	public int getPayId() {
		return payId;
	}
	public void setPayId(int payId) {
		this.payId = payId;
	}
}
