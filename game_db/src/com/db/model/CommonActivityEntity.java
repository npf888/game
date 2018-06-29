package com.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_common_activity")
public class CommonActivityEntity implements BaseEntity<Long>{
	private static final long serialVersionUID = -7347903153510481343L;
	private long id;
	private long activityId;
	private String conditions;
	private String activityDataPack;
	private String rewardActivityDataPack;
	private long updateTime;
	private long createTime;
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getActivityId() {
		return activityId;
	}
	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public String getActivityDataPack() {
		return activityDataPack;
	}
	public void setActivityDataPack(String activityDataPack) {
		this.activityDataPack = activityDataPack;
	}
	public String getRewardActivityDataPack() {
		return rewardActivityDataPack;
	}
	public void setRewardActivityDataPack(String rewardActivityDataPack) {
		this.rewardActivityDataPack = rewardActivityDataPack;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
