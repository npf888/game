package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name="t_notice")
public class NoticeEntity implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7453214060327461390L;
	
	private long id;
	private long startTime;
	private long endTime;
	private int dailyStartTime;
	private int dailyEndTime;
	private int intervalTime;
	private int isDelete;
	private String content;
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
		this.id= id;
	}

	@Column
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	@Column
	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	@Column
	public int getDailyStartTime() {
		return dailyStartTime;
	}

	public void setDailyStartTime(int dailyStartTime) {
		this.dailyStartTime = dailyStartTime;
	}

	@Column
	public int getDailyEndTime() {
		return dailyEndTime;
	}

	public void setDailyEndTime(int dailyEndTime) {
		this.dailyEndTime = dailyEndTime;
	}

	@Column
	public int getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(int interval) {
		this.intervalTime = interval;
	}

	@Column
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

}