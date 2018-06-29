package com.gameserver.worldboss.data;
/**
 *  当前的boss 更新时 刷新的数据
 * @author JavaServer
 *
 */
public class RefreshBossData {

	private long curBlood;
	private long skillTime;
	public long getCurBlood() {
		return curBlood;
	}
	public void setCurBlood(long curBlood) {
		this.curBlood = curBlood;
	}
	public long getSkillTime() {
		return skillTime;
	}
	public void setSkillTime(long skillTime) {
		this.skillTime = skillTime;
	}
	
	
}
