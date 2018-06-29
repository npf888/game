package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_human_item")
public class HumanItemEntity implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6698991987801497707L;
	
	private long id;
	private long fromPlayerId;
	private long charId;
	private int templateId;
	private int overlap;
	private long beginTime;
	private long duration;
	private int useing;//是否正在使用中 0：否，1：是
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
	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	@Column
	public int getOverlap() {
		return overlap;
	}

	public void setOverlap(int overlap) {
		this.overlap = overlap;
	}

	@Column
	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	@Column
	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
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
	public int getUseing() {
		return useing;
	}

	public void setUseing(int useing) {
		this.useing = useing;
	}

	public long getFromPlayerId() {
		return fromPlayerId;
	}

	public void setFromPlayerId(long fromPlayerId) {
		this.fromPlayerId = fromPlayerId;
	}

}


