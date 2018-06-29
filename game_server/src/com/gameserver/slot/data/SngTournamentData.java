package com.gameserver.slot.data;

/**
 * 竞赛简要信息
 * @author 郭君伟
 *
 */
public class SngTournamentData {
	
	/**竞速类型 **/
	private int tournamentType;
	/**老虎机结束时间块毫秒数 **/
	private long endTimeBlock;
	/**老虎机竞赛总人数 **/
	private int totalHuman;
	/**总奖池 **/
	private long sngJackpot;
	/**vip等级**/
	private int vipLevel;
	
	

	public int getTournamentType() {
		return tournamentType;
	}

	public void setTournamentType(int tournamentType) {
		this.tournamentType = tournamentType;
	}

	public long getEndTimeBlock() {
		return endTimeBlock;
	}

	public void setEndTimeBlock(long endTimeBlock) {
		this.endTimeBlock = endTimeBlock;
	}

	public int getTotalHuman() {
		return totalHuman;
	}

	public void setTotalHuman(int totalHuman) {
		this.totalHuman = totalHuman;
	}

	public long getSngJackpot() {
		return sngJackpot;
	}

	public void setSngJackpot(long sngJackpot) {
		this.sngJackpot = sngJackpot;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	
	
	
}
