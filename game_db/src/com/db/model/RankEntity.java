package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name="t_rank")
public class RankEntity implements BaseEntity<Long>{
	  
	  /**主键 **/
	  private long id;
	  /** 角色ID **/
	  private long charId;
	  /**今日单居最大赢取彩金 **/
	  private long daySingleJackpot;
	  /**今日总的累计彩金 **/
	  private long dayTotalJackpot;
	  /**注册到现在最大的彩金 **/
	  private long singleJackpot;
	  /**注册到现在累计彩金 **/
	  private long totalJackpot;
	  /**vip等级 **/
	  private int viplevel;
	  
	  private long updateTime;
	  
	  private long createTime;
	  
	  private String scoreList;
	  
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
	public long getCharId() {
		return charId;
	}
	public void setCharId(long charId) {
		this.charId = charId;
	}
	@Column
	public long getDaySingleJackpot() {
		return daySingleJackpot;
	}
	public void setDaySingleJackpot(long daySingleJackpot) {
		this.daySingleJackpot = daySingleJackpot;
	}
	@Column
	public long getDayTotalJackpot() {
		return dayTotalJackpot;
	}
	public void setDayTotalJackpot(long dayTotalJackpot) {
		this.dayTotalJackpot = dayTotalJackpot;
	}
	@Column
	public long getSingleJackpot() {
		return singleJackpot;
	}
	public void setSingleJackpot(long singleJackpot) {
		this.singleJackpot = singleJackpot;
	}
	
	@Column
	public long getTotalJackpot() {
		return totalJackpot;
	}
	public void setTotalJackpot(long totalJackpot) {
		this.totalJackpot = totalJackpot;
	}
	
	@Column
	public int getViplevel() {
		return viplevel;
	}
	public void setViplevel(int viplevel) {
		this.viplevel = viplevel;
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

	@Column
	public String getScoreList() {
		return scoreList;
	}

	public void setScoreList(String scoreList) {
		this.scoreList = scoreList;
	}
	
	

}
