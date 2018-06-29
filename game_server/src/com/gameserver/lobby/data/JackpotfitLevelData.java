package com.gameserver.lobby.data;

/**
 * 获取适合自己等级
 * @author 郭君伟
 *
 */
public class JackpotfitLevelData {

	/**
	 * 游戏类型
	 */
	private int gameType;
	/**
	 * 彩金
	 */
	private long jackpot;
	
	public int getGameType() {
		return gameType;
	}
	public void setGameType(int gameType) {
		this.gameType = gameType;
	}
	public long getJackpot() {
		return jackpot;
	}
	public void setJackpot(long jackpot) {
		this.jackpot = jackpot;
	}
	
	
}
