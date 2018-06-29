package com.gameserver.slot.pojo;

public class SngInfo implements Comparable<SngInfo> {

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
	@Override
	public int compareTo(SngInfo o) {
		if(o.rewardTime > this.rewardTime){
			return 1;
		}
		return -1;
	}
	
	
}
