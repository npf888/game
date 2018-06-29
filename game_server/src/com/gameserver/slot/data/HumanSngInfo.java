package com.gameserver.slot.data;

/**
 * 
 * @author 郭君伟
 *
 */
public class HumanSngInfo {

	/**竞赛类型 **/
	private int tournamentType;
	/**名次 **/
	private int rank;
	/**奖励数量 **/
	private long rewardNum;
	/**奖励时间 **/
	private long rewardTime;
	
	public int getTournamentType() {
		return tournamentType;
	}
	public void setTournamentType(int tournamentType) {
		this.tournamentType = tournamentType;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public long getRewardNum() {
		return rewardNum;
	}
	public void setRewardNum(long rewardNum) {
		this.rewardNum = rewardNum;
	}
	public long getRewardTime() {
		return rewardTime;
	}
	public void setRewardTime(long rewardTime) {
		this.rewardTime = rewardTime;
	}
	
	
	
}
