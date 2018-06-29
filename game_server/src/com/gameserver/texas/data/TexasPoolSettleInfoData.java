package com.gameserver.texas.data;

/**
 * 结算数据
 * @author wayne
 *
 */
public class TexasPoolSettleInfoData {
	private TexasRoomPlayerSettleInfoData[] winnerList;

	public TexasRoomPlayerSettleInfoData[] getWinnerList() {
		return winnerList;
	}

	public void setWinnerList(TexasRoomPlayerSettleInfoData[] winnerList) {
		this.winnerList = winnerList;
	}
}
