package com.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

/**
 * 存钱罐
 * @author wayne
 *
 */
@Entity
@Table(name = "t_human_treasury")
public class HumanTreasuryEntity implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3450175577169459614L;
	private long id;
	private long chartId;
	private int type;
	private long gold;
	private int factorTreasury;
	private int vipPointTreasury;
	private long maxTreasury;
	private Date createTime;
	private Date updateTime;
	
	
	public long getChartId() {
		return chartId;
	}

	public void setChartId(long chartId) {
		this.chartId = chartId;
	}

	@Column
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	@Column
	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}
	@Column
	public int getFactorTreasury() {
		return factorTreasury;
	}

	public void setFactorTreasury(int factorTreasury) {
		this.factorTreasury = factorTreasury;
	}
	@Column
	public int getVipPointTreasury() {
		return vipPointTreasury;
	}

	public void setVipPointTreasury(int vipPointTreasury) {
		this.vipPointTreasury = vipPointTreasury;
	}
	@Column
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Id
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}

	public long getMaxTreasury() {
		return maxTreasury;
	}

	public void setMaxTreasury(long maxTreasury) {
		this.maxTreasury = maxTreasury;
	}

}
