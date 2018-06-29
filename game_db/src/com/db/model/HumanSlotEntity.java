package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name="t_human_slot")
public class HumanSlotEntity implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4713316885743095213L;
	
	private long id;
	private long charId;
	private long slotId;
	private long totalBet;
	private long totalRefund;
	private long totalJackpot;
	private String slotType23Jackpot;//这个字段包括5个奖池，每个奖池用 "," 分割
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
	 * @return the charId
	 */
	public long getCharId() {
		return charId;
	}

	/**
	 * @param charId the charId to set
	 */
	public void setCharId(long charId) {
		this.charId = charId;
	}



	/**
	 * @return the slotId
	 */
	public long getSlotId() {
		return slotId;
	}

	/**
	 * @param slotId the slotId to set
	 */
	public void setSlotId(long slotId) {
		this.slotId = slotId;
	}

	/**
	 * @return the totalBet
	 */
	@Column
	public long getTotalBet() {
		return totalBet;
	}

	/**
	 * @param totalBet the totalBet to set
	 */
	public void setTotalBet(long totalBet) {
		this.totalBet = totalBet;
	}

	/**
	 * @return the totalRefund
	 */
	@Column
	public long getTotalRefund() {
		return totalRefund;
	}

	/**
	 * @param totalRefund the totalRefund to set
	 */
	public void setTotalRefund(long totalRefund) {
		this.totalRefund = totalRefund;
	}
	
	
	@Column
	public long getTotalJackpot() {
		return totalJackpot;
	}

	public void setTotalJackpot(long totalJackpot) {
		this.totalJackpot = totalJackpot;
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


}
