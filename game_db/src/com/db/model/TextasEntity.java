package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name="t_textas")
public class TextasEntity implements BaseEntity<Long> {

	  
	 /**主键 **/
	 private long id;
	/** 德州房间ID**/
	 private int texasId;
	 /**彩金池 **/
	 private long jackpot;
	 /**累计彩金池 **/
	 private long cumuJackpot;
	 /** 更新时间**/
	 private long updateTime;
	 /**创建时间 **/
	 private long createTime;

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
	public int getTexasId() {
		return texasId;
	}

	public void setTexasId(int texasId) {
		this.texasId = texasId;
	}

	@Column
	public long getJackpot() {
		return jackpot;
	}

	public void setJackpot(long jackpot) {
		this.jackpot = jackpot;
	}

	@Column
	public long getCumuJackpot() {
		return cumuJackpot;
	}

	public void setCumuJackpot(long cumuJackpot) {
		this.cumuJackpot = cumuJackpot;
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
