package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_human_collect")
public class HumanCollectEntity implements BaseEntity<Long> {

	
	  /** 主键 **/
      private long id;
	  /** 角色ID **/
	  private long humanId;
	 
	  private String carIds;
	
	  private String debris;
	  
	  private String slotspin;
	  
	  private String slotpoint;
	  
	  private long updateTime;
		
	  private long createTime;
		 
	@Id
	@Column
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Column
	public long getHumanId() {
		return humanId;
	}

	public void setHumanId(long humanId) {
		this.humanId = humanId;
	}

	
	@Column
	public String getCarIds() {
		return carIds;
	}

	public void setCarIds(String carIds) {
		this.carIds = carIds;
	}

	@Column
	public String getDebris() {
		return debris;
	}

	public void setDebris(String debris) {
		this.debris = debris;
	}
	
	
	@Column
	public String getSlotspin() {
		return slotspin;
	}

	public void setSlotspin(String slotspin) {
		this.slotspin = slotspin;
	}

	@Column
	public String getSlotpoint() {
		return slotpoint;
	}

	public void setSlotpoint(String slotpoint) {
		this.slotpoint = slotpoint;
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
