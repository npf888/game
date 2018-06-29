package com.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name="t_robot_statis_data")
public class RobotStatisDataEntity implements BaseEntity<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7453214060327461345L;
	private long id;
	private String slotName;
	private long slotId;
	private long slotType;
	private long chartId;
	private long rewardCount;
	private long rewardUsed;
	private long payCount;
	private long scatterNum;
	private long bonusNum;
	private long scatterTriggerCount;
	private long scatterTriggerFreeCount;
	private long bonusTriggerCount;
	private long bonusTriggerFreeCount;
	private Date createTime;
	private Date updateTime;


	
	@Id
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
	@Column
	public long getSlotId() {
		return slotId;
	}

	public void setSlotId(long slotId) {
		this.slotId = slotId;
	}
	@Column
	public long getChartId() {
		return chartId;
	}

	public void setChartId(long chartId) {
		this.chartId = chartId;
	}
	@Column
	public long getRewardCount() {
		return rewardCount;
	}

	public void setRewardCount(long rewardCount) {
		this.rewardCount = rewardCount;
	}
	@Column
	public long getRewardUsed() {
		return rewardUsed;
	}

	public void setRewardUsed(long rewardUsed) {
		this.rewardUsed = rewardUsed;
	}
	@Column
	public long getPayCount() {
		return payCount;
	}

	public void setPayCount(long payCount) {
		this.payCount = payCount;
	}
	@Column
	public long getScatterNum() {
		return scatterNum;
	}

	public void setScatterNum(long scatterNum) {
		this.scatterNum = scatterNum;
	}
	@Column
	public long getBonusNum() {
		return bonusNum;
	}

	public void setBonusNum(long bonusNum) {
		this.bonusNum = bonusNum;
	}
	@Column
	public long getScatterTriggerCount() {
		return scatterTriggerCount;
	}

	public void setScatterTriggerCount(long scatterTriggerCount) {
		this.scatterTriggerCount = scatterTriggerCount;
	}
	@Column
	public long getBonusTriggerCount() {
		return bonusTriggerCount;
	}

	public void setBonusTriggerCount(long bonusTriggerCount) {
		this.bonusTriggerCount = bonusTriggerCount;
	}
	@Column
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setId(long id) {
		this.id = id;
	}
	@Column
	public long getScatterTriggerFreeCount() {
		return scatterTriggerFreeCount;
	}

	public void setScatterTriggerFreeCount(long scatterTriggerFreeCount) {
		this.scatterTriggerFreeCount = scatterTriggerFreeCount;
	}
	@Column
	public long getSlotType() {
		return slotType;
	}

	public void setSlotType(long slotType) {
		this.slotType = slotType;
	}
	@Column
	public String getSlotName() {
		return slotName;
	}
	public void setSlotName(String slotName) {
		this.slotName = slotName;
	}
	@Column
	public long getBonusTriggerFreeCount() {
		return bonusTriggerFreeCount;
	}

	public void setBonusTriggerFreeCount(long bonusTriggerFreeCount) {
		this.bonusTriggerFreeCount = bonusTriggerFreeCount;
	}

	

}
