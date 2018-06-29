package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_baccart_room_model")
public class BaccartRoomModelEntity implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3576371703184688896L;
	
	private long id;
	private int roomId;
	private long stock;
	private long jackpot;
	private int closed;
	private long updateTime;
	private long createTime;

	@Id
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Column
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	@Column
	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	@Column
	public long getJackpot() {
		return jackpot;
	}

	public void setJackpot(long jackpot) {
		this.jackpot = jackpot;
	}
	/**
	 * @return the closed
	 */
	public int getClosed() {
		return closed;
	}

	/**
	 * @param closed the closed to set
	 */
	public void setClosed(int closed) {
		this.closed = closed;
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
