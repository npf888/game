package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;


@Entity
@Table(name = "t_human_misc")
public class HumanMiscEntity implements BaseEntity<Long>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4670106916111782762L;
	private long id;
	private long charId;
	private long lastGetTime;
	private long onlineTime;
	private int currentOnlineRewardId;
	private long firstRechargeTime;
	private int newUser;
	private int renameTimes;
	private int adRewards;
	private String fbInvitePack;
	private String fbInviteRewardPack;
	private int fbReward;
	private long updateTime;
	private long createTime;
	private int fbThumb;
	private long refreshTime;
	
	@Id
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
	
	@Column
	public long getCharId() {
		return charId;
	}

	public void setCharId(long charId) {
		this.charId = charId;
	}

	@Column
	public long getLastGetTime() {
		return lastGetTime;
	}

	public void setLastGetTime(long lastGetTime) {
		this.lastGetTime = lastGetTime;
	}

	@Column
	public long getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(long onlineTime) {
		this.onlineTime = onlineTime;
	}

	@Column
	public int getCurrentOnlineRewardId() {
		return currentOnlineRewardId;
	}

	public void setCurrentOnlineRewardId(int currentOnlineRewardId) {
		this.currentOnlineRewardId = currentOnlineRewardId;
	}

	public long getFirstRechargeTime() {
		return firstRechargeTime;
	}

	public void setFirstRechargeTime(long firstRechargeTime) {
		this.firstRechargeTime = firstRechargeTime;
	}

	@Column
	public int getNewUser() {
		return newUser;
	}

	public void setNewUser(int newUser) {
		this.newUser = newUser;
	}
	
	@Column
	public int getRenameTimes() {
		return renameTimes;
	}

	public void setRenameTimes(int renameTimes) {
		this.renameTimes = renameTimes;
	}

	/**
	 * @return the adRewards
	 */
	@Column
	public int getAdRewards() {
		return adRewards;
	}

	/**
	 * @param adRewards the adRewards to set
	 */
	public void setAdRewards(int adRewards) {
		this.adRewards = adRewards;
	}

	/**
	 * @return the fbInvitePack
	 */
	@Column
	public String getFbInvitePack() {
		return fbInvitePack;
	}

	/**
	 * @param fbInvitePack the fbInvitePack to set
	 */
	public void setFbInvitePack(String fbInvitePack) {
		this.fbInvitePack = fbInvitePack;
	}
	
	@Column
	public String getFbInviteRewardPack() {
		return fbInviteRewardPack;
	}

	/**
	 * @param fbInvitePack the fbInvitePack to set
	 */
	public void setFbInviteRewardPack(String fbInviteRewardPack) {
		this.fbInviteRewardPack = fbInviteRewardPack;
	}
	

	/**
	 * @return the fbReward
	 */
	@Column
	public int getFbReward() {
		return fbReward;
	}

	/**
	 * @param fbReward the fbReward to set
	 */
	public void setFbReward(int fbReward) {
		this.fbReward = fbReward;
	}

	@Column
	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	@Column
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	@Column
	public int getFbThumb() {
		return fbThumb;
	}

	public void setFbThumb(int fbThumb) {
		this.fbThumb = fbThumb;
	}

	@Column
	public long getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(long refreshTime) {
		this.refreshTime = refreshTime;
	}
	
	

}
