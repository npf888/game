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
@Table(name = "t_human_friend_request")
public class FriendRequestEntity implements BaseEntity<Long>
{
	private static final long serialVersionUID = -7878273856314330567L;
	/** 主键 */
	private long id;
	/** 角色id */
	private long charId;
	/** 发送者id*/
	private long sendId;
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
	public long getSendId() {
		return sendId;
	}

	public void setSendId(long sendId) {
		this.sendId = sendId;
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
