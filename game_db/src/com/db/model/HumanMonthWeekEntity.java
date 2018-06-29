package com.db.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;


@Entity
@Table(name = "t_human_month_week")
public class HumanMonthWeekEntity  implements BaseEntity<Long>{

	private static final long serialVersionUID = -3380175577169478688L;
	
	
	private long id;
	private long userId;
	private long continueTime;
	private Date startTime;
	private int mwType;
	/***当周 或者 当月 是否购买（一周 或者一个月只限购一次）***/
	private int isBuy;
	private Date createTime;
	private Date updateTime;
	
	
	
	
	
	
	public int getIsBuy() {
		return isBuy;
	}

	public void setIsBuy(int isBuy) {
		this.isBuy = isBuy;
	}

	public long getContinueTime() {
		return continueTime;
	}

	public void setContinueTime(long continueTime) {
		this.continueTime = continueTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getMwType() {
		return mwType;
	}

	public void setMwType(int mwType) {
		this.mwType = mwType;
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

	public void setId(long id) {
		this.id = id;
	}

	@Id
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}

}
