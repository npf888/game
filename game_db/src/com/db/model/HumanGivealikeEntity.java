package com.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;
@Entity
@Table(name = "t_human_givealike")
public class HumanGivealikeEntity implements BaseEntity<Long> {
	
	
	private static final long serialVersionUID = -559914095629355615L;


	private long id;
	private long userId;
	private int slotType;
	private int slotBet;
	private String content;
	private Date createTime;
	
	@Id
	@Column
	@Override
	public Long getId() {
		return this.id;
	}
	@Override
	public void setId(Long id) {
		this.id=id;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public int getSlotType() {
		return slotType;
	}
	public void setSlotType(int slotType) {
		this.slotType = slotType;
	}
	
	public int getSlotBet() {
		return slotBet;
	}
	public void setSlotBet(int slotBet) {
		this.slotBet = slotBet;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	
	

	
	

}
