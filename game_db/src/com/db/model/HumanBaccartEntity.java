package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_human_baccart")
public class HumanBaccartEntity implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1311457506347662630L;
	private long id;
	private long charId;
	private long bankerNum;
	private long gameNum;
	private long winNum;
	private long beaconNum;
	private long lostNum;
	private int isAuto;
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
	public long getBankerNum() {
		return bankerNum;
	}

	public void setBankerNum(long bankerNum) {
		this.bankerNum = bankerNum;
	}

	@Column
	public long getGameNum() {
		return gameNum;
	}

	public void setGameNum(long gameNum) {
		this.gameNum = gameNum;
	}

	@Column
	public long getWinNum() {
		return winNum;
	}

	public void setWinNum(long winNum) {
		this.winNum = winNum;
	}

	public long getBeaconNum() {
		return beaconNum;
	}

	public void setBeaconNum(long beaconNum) {
		this.beaconNum = beaconNum;
	}

	@Column
	public long getLostNum() {
		return lostNum;
	}

	public void setLostNum(long lostNum) {
		this.lostNum = lostNum;
	}

	
	@Column
	public int getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(int isAuto) {
		this.isAuto = isAuto;
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

	public long getCharId() {
		return charId;
	}

	public void setCharId(long charId) {
		this.charId = charId;
	}



}
