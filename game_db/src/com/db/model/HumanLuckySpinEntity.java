package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_human_lucky_spin")
public class HumanLuckySpinEntity implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6861609450513732924L;

	private long id;
	private long charId;
	private long lastSpinTime;
	private int freeTimes;
	private int times;
	private String poolPack;
	private long updateTime;
	private long createTime;
	
	@Id
	@Column
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

	/**
	 * @return the charId
	 */
	@Column
	public long getCharId() {
		return charId;
	}

	/**
	 * @param charId the charId to set
	 */
	public void setCharId(long charId) {
		this.charId = charId;
	}

	/**
	 * @return the lastFreeTime
	 */
	@Column
	public long getLastSpinTime() {
		return lastSpinTime;
	}

	/**
	 * @param lastFreeTime the lastFreeTime to set
	 */
	public void setLastSpinTime(long lastSpinTime) {
		this.lastSpinTime = lastSpinTime;
	}

	/**
	 * @return the freeTimes
	 */
	@Column
	public int getFreeTimes() {
		return freeTimes;
	}

	/**
	 * @param freeTimes the freeTimes to set
	 */
	public void setFreeTimes(int freeTimes) {
		this.freeTimes = freeTimes;
	}

	/**
	 * @return the times
	 */
	@Column
	public int getTimes() {
		return times;
	}

	/**
	 * @param times the times to set
	 */
	public void setTimes(int times) {
		this.times = times;
	}

	/**
	 * @return the poolPack
	 */
	@Column
	public String getPoolPack() {
		return poolPack;
	}

	/**
	 * @param poolPack the poolPack to set
	 */
	public void setPoolPack(String poolPack) {
		this.poolPack = poolPack;
	}

	/**
	 * @return the updateTime
	 */
	@Column
	public long getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the createTime
	 */
	@Column
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

}
