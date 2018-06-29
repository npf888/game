package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name="t_human_task")
public class HumanTaskEntity implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7532375783920694336L;
	private long id;
	private long charId;
	private String dailyTaskPack;
	private String taskPack;
	private long updateTime;
	private long createTime;
	private String taskProgress;
	
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

	public String getDailyTaskPack() {
		return dailyTaskPack;
	}

	public void setDailyTaskPack(String dailyTaskPack) {
		this.dailyTaskPack = dailyTaskPack;
	}

	public String getTaskPack() {
		return taskPack;
	}

	public void setTaskPack(String taskPack) {
		this.taskPack = taskPack;
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
	public String getTaskProgress() {
		return taskProgress;
	}

	public void setTaskProgress(String taskProgress) {
		this.taskProgress = taskProgress;
	}
	
	

}
