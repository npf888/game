package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name="t_robot_complement")
public class RobotComplementEntity implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7453214060327461390L;
	
	private long id;
	private long robotId;
	private long complement;
	private long updateTime;
	private long createTime;

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
	public long getRobotId() {
		return robotId;
	}

	public void setRobotId(long robotId) {
		this.robotId = robotId;
	}
	
	@Column
	public long getComplement() {
		return complement;
	}

	public void setComplement(long complement) {
		this.complement = complement;
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
