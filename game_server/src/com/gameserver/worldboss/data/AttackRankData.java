package com.gameserver.worldboss.data;

public class AttackRankData {
	private String name;
	private long bossId;
	private long userId;
	private long attackTotalBlood;
	//表里边的 vip0 vip1 vip2 vip3 vip4 vip5 
	private int vipAdditionRate;
	//当前用户 打掉的血 占据 总血 的 比例   取百分号 前边的数
	private int attackRate;
	//排序字段，当前用户所在 第几位
	private int sortIndex;
	public long getBossId() {
		return bossId;
	}
	public void setBossId(long bossId) {
		this.bossId = bossId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getAttackTotalBlood() {
		return attackTotalBlood;
	}
	public void setAttackTotalBlood(long attackTotalBlood) {
		this.attackTotalBlood = attackTotalBlood;
	}
	public int getVipAdditionRate() {
		return vipAdditionRate;
	}
	public void setVipAdditionRate(int vipAdditionRate) {
		this.vipAdditionRate = vipAdditionRate;
	}
	public int getAttackRate() {
		return attackRate;
	}
	public void setAttackRate(int attackRate) {
		this.attackRate = attackRate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}
	

	
	
}
