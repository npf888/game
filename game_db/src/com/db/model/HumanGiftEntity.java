package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_human_gift")
public class HumanGiftEntity implements BaseEntity<Long> {
	
	  
	  /** 主键 **/
	 private long id;
	 /** 角色ID **/
	 private long charId;
	 /**优惠弹出礼包 **/
	 private int giftid;
	 /**优惠弹出礼包弹出世间点 **/
	 private long refreshTime;
	 /** **/
	 private long updateTime;
	 /** **/
	 private long createTime;


	@Id
	@Column
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
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
	public int getGiftid() {
		return giftid;
	}

	public void setGiftid(int giftid) {
		this.giftid = giftid;
	}
	
	@Column
	public long getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(long refreshTime) {
		this.refreshTime = refreshTime;
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
