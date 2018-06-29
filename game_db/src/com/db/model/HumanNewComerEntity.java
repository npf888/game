package com.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_human_new_comer")
public class HumanNewComerEntity implements BaseEntity<Long>{

	private long id;
	
	//用户ID
	private long userId;
	//每天第一次登录时间（这个不会变）
	private Date startTime;
	//每天的 第一次登录时间 （这个会变，根据剩余时间）
	private Date perStartTime;
	@Id
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
		
	}
	@Column
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@Column
	public Date getPerStartTime() {
		return perStartTime;
	}

	public void setPerStartTime(Date perStartTime) {
		this.perStartTime = perStartTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setId(long id) {
		this.id = id;
	}

}
