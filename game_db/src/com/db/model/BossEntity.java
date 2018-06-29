package com.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_boss")
public class BossEntity implements BaseEntity<Long>{
	
	private static final long serialVersionUID = -3380175577169459555L;
	
	private long id;
	private int nameId;
	private long bossId;
	private int descrip;
	private int type;

	private int airtime;

	private int wildreduce;

	private int recover1;

	private int damagereduce;

	private int avoid;

	private long blood;

	private int rewardNum1;

	private int rewardNum2;

	private long changingBlood;
	private long increaseBlood;
	
	private int lastBossId;
	private int nextBossId;
	private int status;
	private Date createTime;
	private Date updateTime;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 持续时间
	 */
	private long continueTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**是否 被击杀 1：被击杀- 0：没有被击杀**/
	private int beKilled;
	
	/**伤害排行榜**/
	private String attackRank;
	/**这个boss 开的是哪个时间段的bossTemplate 的ID**/
	private int bossTemplateId;
	
	@Id
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id=id;
	}
	
	@Column
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	@Column
	public int getAirtime() {
		return airtime;
	}
	public void setAirtime(int airtime) {
		this.airtime = airtime;
	}
	
	@Column
	public int getWildreduce() {
		return wildreduce;
	}
	public void setWildreduce(int wildreduce) {
		this.wildreduce = wildreduce;
	}
	
	@Column
	public int getRecover1() {
		return recover1;
	}
	public void setRecover1(int recover1) {
		this.recover1 = recover1;
	}
	
	@Column
	public int getDamagereduce() {
		return damagereduce;
	}
	public void setDamagereduce(int damagereduce) {
		this.damagereduce = damagereduce;
	}
	
	@Column
	public int getAvoid() {
		return avoid;
	}
	public void setAvoid(int avoid) {
		this.avoid = avoid;
	}
	
	@Column
	public long getBlood() {
		return blood;
	}
	public void setBlood(long blood) {
		this.blood = blood;
	}
	
	@Column
	public int getRewardNum1() {
		return rewardNum1;
	}
	public void setRewardNum1(int rewardNum1) {
		this.rewardNum1 = rewardNum1;
	}
	
	@Column
	public int getRewardNum2() {
		return rewardNum2;
	}
	public void setRewardNum2(int rewardNum2) {
		this.rewardNum2 = rewardNum2;
	}
	
	@Column
	public long getChangingBlood() {
		return changingBlood;
	}
	public void setChangingBlood(long changingBlood) {
		this.changingBlood = changingBlood;
	}
	
	@Column
	public long getIncreaseBlood() {
		return increaseBlood;
	}
	
	public void setIncreaseBlood(long increaseBlood) {
		this.increaseBlood = increaseBlood;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column
	public int getBeKilled() {
		return beKilled;
	}
	public void setBeKilled(int beKilled) {
		this.beKilled = beKilled;
	}
	
	@Column
	public String getAttackRank() {
		return attackRank;
	}
	public void setAttackRank(String attackRank) {
		this.attackRank = attackRank;
	}
	
	
	@Column
	public long getContinueTime() {
		return continueTime;
	}
	public void setContinueTime(long continueTime) {
		this.continueTime = continueTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public long getBossId() {
		return bossId;
	}
	public void setBossId(long bossId) {
		this.bossId = bossId;
	}
	public int getNameId() {
		return nameId;
	}
	public void setNameId(int nameId) {
		this.nameId = nameId;
	}
	public int getDescrip() {
		return descrip;
	}
	public void setDescrip(int descrip) {
		this.descrip = descrip;
	}
	public int getNextBossId() {
		return nextBossId;
	}
	public void setNextBossId(int nextBossId) {
		this.nextBossId = nextBossId;
	}
	public int getBossTemplateId() {
		return bossTemplateId;
	}
	public void setBossTemplateId(int bossTemplateId) {
		this.bossTemplateId = bossTemplateId;
	}
	public int getLastBossId() {
		return lastBossId;
	}
	public void setLastBossId(int lastBossId) {
		this.lastBossId = lastBossId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
	
}
