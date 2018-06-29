package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;


@Entity
@Table(name = "t_human_week_sign_in")
public class HumanWeekSignInEntity  implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6363793844311378252L;
	/** 主键 */
	private long id;
	/** 玩家角色ID */
	private long charId;
	/**持续时间*/
	private String signInPack;
	/** 更新时间 */
	private long updateTime;
	/** 创建时间 */
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
	public String getSignInPack() {
		return signInPack;
	}

	public void setSignInPack(String signInPack) {
		this.signInPack = signInPack;
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
