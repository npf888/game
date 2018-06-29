package com.gameserver.worldboss.vo;
/**
 * 攻击富豪榜排名
 * @author JavaServer
 *
 */
public class AttackRankVO {
	private String name;
	private long bossId;
	private long userId;
	private long attackTotalBlood;
	//表里边的 vip0 vip1 vip2 vip3 vip4 vip5 
	private int vipAdditionRate;
	//当前用户 打掉的血 占据 总血 的 比例   取百分号 前边的数
	private int attackRate;
	//名次
	private int curIndex;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getCurIndex() {
		return curIndex;
	}
	public void setCurIndex(int curIndex) {
		this.curIndex = curIndex;
	}
	
	
	
}
