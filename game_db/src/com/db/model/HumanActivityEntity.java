package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

/**
 * 用户活动数据
 * @author wayne
 *
 */
@Entity
@Table(name = "t_human_activity")
public class HumanActivityEntity  implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7347903153510481143L;
	private long id;
	private long charId;
	private long activityId;
	private String friendId;
	private String conditions;
	private String activityDataPack;
	private String rewardActivityDataPack;
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

	@Column
	public long getCharId() {
		return charId;
	}

	public void setCharId(long charId) {
		this.charId = charId;
	}

	@Column
	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	@Column
	public String getActivityDataPack() {
		return activityDataPack;
	}

	public void setActivityDataPack(String activityDataPack) {
		this.activityDataPack = activityDataPack;
	}

	@Column
	public String getRewardActivityDataPack() {
		return rewardActivityDataPack;
	}

	public void setRewardActivityDataPack(String rewardActivityDataPack) {
		this.rewardActivityDataPack = rewardActivityDataPack;
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
	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	
	

}
