package com.gameserver.slot.data;

public class GcRemoveSlotSlotList {
	/** 元素列表 */
	private int[] slotElementList;
	/** 收益 */
	private long rewardNum;
	/** 免费次数 */
	private int freeTimes;
	/** 累计奖金 */
	private long rewardSum;
	
	private int lineNum;
	
	
	
	public int getLineNum() {
		return lineNum;
	}
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}
	public int[] getSlotElementList() {
		return slotElementList;
	}
	public void setSlotElementList(int[] slotElementList) {
		this.slotElementList = slotElementList;
	}
	public long getRewardNum() {
		return rewardNum;
	}
	public void setRewardNum(long rewardNum) {
		this.rewardNum = rewardNum;
	}
	public int getFreeTimes() {
		return freeTimes;
	}
	public void setFreeTimes(int freeTimes) {
		this.freeTimes = freeTimes;
	}
	public long getRewardSum() {
		return rewardSum;
	}
	public void setRewardSum(long rewardSum) {
		this.rewardSum = rewardSum;
	}
	
	
	
	
}
