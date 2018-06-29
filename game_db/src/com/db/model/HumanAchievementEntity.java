package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_human_achievement")
public class HumanAchievementEntity implements BaseEntity<Long>{

	private long id;
	private long charId;
	private String achValue;
	private String achDate;
	private long slotRotate;
	private long slotWin;
	private long slotSingleWin;
	private long updateTime;
	private long createTime;
	private long slotWinNum;
	
	@Id
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
	public String getAchValue() {
		return achValue;
	}
	
	public void setAchValue(String achValue) {
		this.achValue = achValue;
	}
	
	@Column
	public String getAchDate() {
		return achDate;
	}
	public void setAchDate(String achDate) {
		this.achDate = achDate;
	}
	
	@Column
	public long getSlotRotate() {
		return slotRotate;
	}
	public void setSlotRotate(long slotRotate) {
		this.slotRotate = slotRotate;
	}
	
	@Column
	public long getSlotWin() {
		return slotWin;
	}
	public void setSlotWin(long slotWin) {
		this.slotWin = slotWin;
	}
	
	@Column
	public long getSlotSingleWin() {
		return slotSingleWin;
	}
	public void setSlotSingleWin(long slotSingleWin) {
		this.slotSingleWin = slotSingleWin;
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
	public long getSlotWinNum() {
		return slotWinNum;
	}
	public void setSlotWinNum(long slotWinNum) {
		this.slotWinNum = slotWinNum;
	}
	
	

}
