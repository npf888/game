package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_human_texas_sng")
public class HumanTexasSNGEntity implements BaseEntity<Long>{

	
	
	private static final long serialVersionUID = -2275178765939854450L;
	
	
	private long id;
	private long charId;
	private int joinTimes;
	private int goldNum;
	private int silverNum;
	private int copperNum;
	private int weekScore;
	private long updateTime;
	private long createTime;
	
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

	public long getCharId() {
		return charId;
	}

	public void setCharId(long charId) {
		this.charId = charId;
	}

	@Column
	public int getJoinTimes() {
		return joinTimes;
	}

	public void setJoinTimes(int joinTimes) {
		this.joinTimes = joinTimes;
	}

	@Column
	public int getGoldNum() {
		return goldNum;
	}

	public void setGoldNum(int goldNum) {
		this.goldNum = goldNum;
	}

	@Column
	public int getSilverNum() {
		return silverNum;
	}

	public void setSilverNum(int silverNum) {
		this.silverNum = silverNum;
	}

	@Column
	public int getCopperNum() {
		return copperNum;
	}

	
	public void setCopperNum(int copperNum) {
		this.copperNum = copperNum;
	}

	@Column
	public int getWeekScore() {
		return weekScore;
	}

	public void setWeekScore(int weekScore) {
		this.weekScore = weekScore;
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

}
