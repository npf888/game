package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name="t_slot")
public class SlotEntity implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4713316885743095213L;
	
	private long id;
	private int slotType;
	private long jackpot;
	private long stock;
	private long spinTimes;
	private long cumuJackpot;
	private String slotType23Jackpot;
	
	private long jackpot1;
	private long jackpot2;
	private long jackpot3;
	private long jackpot4;
	private long jackpot5;
	private long cumuJackpot1;
	private long cumuJackpot2;
	private long cumuJackpot3;
	private long cumuJackpot4;
	private long cumuJackpot5;
	
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

	/**
	 * @return the slotType
	 */
	@Column
	public int getSlotType() {
		return slotType;
	}

	/**
	 * @param slotType the slotType to set
	 */
	public void setSlotType(int slotType) {
		this.slotType = slotType;
	}

	/**
	 * @return the jackpot
	 */
	@Column
	public long getJackpot() {
		return jackpot;
	}

	/**
	 * @param jackpot the jackpot to set
	 */
	public void setJackpot(long jackpot) {
		this.jackpot = jackpot;
	}

	/**
	 * @return the stock
	 */
	@Column
	public long getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(long stock) {
		this.stock = stock;
	}

	/**
	 * @return the spinTimes
	 */
	@Column
	public long getSpinTimes() {
		return spinTimes;
	}

	/**
	 * @param spinTimes the spinTimes to set
	 */
	public void setSpinTimes(long spinTimes) {
		this.spinTimes = spinTimes;
	}
	
	
	@Column
	public long getCumuJackpot() {
		return cumuJackpot;
	}

	public void setCumuJackpot(long cumuJackpot) {
		this.cumuJackpot = cumuJackpot;
	}

	/**
	 * @return the updateTime
	 */
	@Column
	public long getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	@Column
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the createTime
	 */
	@Column
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	@Column
	public String getSlotType23Jackpot() {
		return slotType23Jackpot;
	}

	public void setSlotType23Jackpot(String slotType23Jackpot) {
		this.slotType23Jackpot = slotType23Jackpot;
	}

	public long getJackpot1() {
		return jackpot1;
	}

	public void setJackpot1(long jackpot1) {
		this.jackpot1 = jackpot1;
	}

	public long getJackpot2() {
		return jackpot2;
	}

	public void setJackpot2(long jackpot2) {
		this.jackpot2 = jackpot2;
	}

	public long getJackpot3() {
		return jackpot3;
	}

	public void setJackpot3(long jackpot3) {
		this.jackpot3 = jackpot3;
	}

	public long getJackpot4() {
		return jackpot4;
	}

	public void setJackpot4(long jackpot4) {
		this.jackpot4 = jackpot4;
	}

	public long getJackpot5() {
		return jackpot5;
	}

	public void setJackpot5(long jackpot5) {
		this.jackpot5 = jackpot5;
	}

	public long getCumuJackpot1() {
		return cumuJackpot1;
	}

	public void setCumuJackpot1(long cumuJackpot1) {
		this.cumuJackpot1 = cumuJackpot1;
	}

	public long getCumuJackpot2() {
		return cumuJackpot2;
	}

	public void setCumuJackpot2(long cumuJackpot2) {
		this.cumuJackpot2 = cumuJackpot2;
	}

	public long getCumuJackpot3() {
		return cumuJackpot3;
	}

	public void setCumuJackpot3(long cumuJackpot3) {
		this.cumuJackpot3 = cumuJackpot3;
	}

	public long getCumuJackpot4() {
		return cumuJackpot4;
	}

	public void setCumuJackpot4(long cumuJackpot4) {
		this.cumuJackpot4 = cumuJackpot4;
	}

	public long getCumuJackpot5() {
		return cumuJackpot5;
	}

	public void setCumuJackpot5(long cumuJackpot5) {
		this.cumuJackpot5 = cumuJackpot5;
	}


	

	
}
