package com.db.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

/**
 * 好友实例实体
 * @author Thinker
 */
@Entity
@Table(name = "t_human_friend")
public class FriendEntity implements BaseEntity<Long>
{
	private static final long serialVersionUID = -7878273856314330567L;
	/** 主键 */
	private long id;
	/** 角色id */
	private long charId;
	/** 好友id*/
	private long friendId;
	/**facebook*/
	private int facebook;
	/** 用户 发出的申请好友同意之后，在每次登录的时候都会判断下是否有好友同意了 他的申请，如果有就把该状态改为1，否则是0*/
	private long agree;
	/**礼物时间*/
	private long giftTime;
	/**更新时间*/
	private long updateTime;
	/**创建时间*/
	private long createTime;

	
	@Id
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id=id;
	}

	@Column
	public long getCharId() {
		return charId;
	}

	public void setCharId(long charId) {
		this.charId = charId;
	}

	@Column
	public long getFriendId() {
		return friendId;
	}

	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}

	/**
	 * @return the facebook
	 */
	public int getFacebook() {
		return facebook;
	}

	/**
	 * @param facebook the facebook to set
	 */
	public void setFacebook(int facebook) {
		this.facebook = facebook;
	}

	public long getGiftTime() {
		return giftTime;
	}

	public void setGiftTime(long giftTime) {
		this.giftTime = giftTime;
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
	public long getAgree() {
		return agree;
	}

	public void setAgree(long agree) {
		this.agree = agree;
	}



}
