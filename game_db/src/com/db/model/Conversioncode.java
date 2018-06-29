package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_conversioncode")
public class Conversioncode implements BaseEntity<Long> {

	private long id;
	 
	private String conversionCode;
	
	private long gold;
	/**结束时间 **/
	private long endTime;
	/**是否有效 1 表示无效的 **/
	private int isdelete;
	/**0：通用型-每人可用一次；1：特殊型-只能给一个人用**/
	private int codeType;
	
	private long updateTime;
	
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
	public String getConversionCode() {
		return conversionCode;
	}

	public void setConversionCode(String conversionCode) {
		this.conversionCode = conversionCode;
	}

	@Column
	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}

	@Column
	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	@Column
	public int getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
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

	public void setId(long id) {
		this.id = id;
	}

	public int getCodeType() {
		return codeType;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}
	
	

}
