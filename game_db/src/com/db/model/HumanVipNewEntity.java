package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

/**
 * 
 * @author 郭君伟
 *
 */
@Entity
@Table(name = "t_human_vip_new")
public class HumanVipNewEntity implements BaseEntity<Long> {
	
	
	private long id;
	/** 角色ID **/
	private long humanId;
	/** vip等级  **/
	private int vipLevel;
	/** 当前vip点  **/
	private int curPoint;
	/** 更新时间 **/
	private long updateTime;
	/** 创建时间  **/
	private long createTime;
	
	
	@Id
	@Override
	public Long getId() {
		
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Column
	public long getHumanId() {
		return humanId;
	}

	public void setHumanId(long humanId) {
		this.humanId = humanId;
	}

	@Column
	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	@Column
	public int getCurPoint() {
		return curPoint;
	}

	public void setCurPoint(int curPoint) {
		this.curPoint = curPoint;
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
