package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

/**
 * vip entity
 * @author wayne
 *
 */
@Entity
@Table(name = "t_human_vip")
public class HumanVipEntity implements BaseEntity<Long>{

	/** 主键 */
	private long id;
	/** 玩家角色ID */
	private long charId;
	/**等级*/
	private int level;
	/**上次领取时间*/
	private long getTime;
	/**购买时间*/
	private long buyTime;
	/**持续时间*/
	private int days;
	/** 更新时间 */
	private long updateTime;
	/** 创建时间 */
	private long createTime;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6260103932145565834L;

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
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Column
	public long getGetTime() {
		return getTime;
	}

	public void setGetTime(long getTime) {
		this.getTime = getTime;
	}

	@Column
	public long getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(long buyTime) {
		this.buyTime = buyTime;
	}

	/**
	 * @return the days
	 */
	@Column
	public int getDays() {
		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(int days) {
		this.days = days;
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


