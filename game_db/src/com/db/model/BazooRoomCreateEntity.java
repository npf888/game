package com.db.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_bazoo_room_create")
public class BazooRoomCreateEntity implements BaseEntity<Long>{
	
	
	private static final long serialVersionUID = 3576371703184688698L;
	
	
	private long id;
	private String modeTypePriPubBet;
	private int number;
	private Date createTime;

	@Id
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}

	public String getModeTypePriPubBet() {
		return modeTypePriPubBet;
	}

	public void setModeTypePriPubBet(String modeTypePriPubBet) {
		this.modeTypePriPubBet = modeTypePriPubBet;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
