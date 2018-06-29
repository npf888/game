package com.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;
@Entity
@Table(name = "t_human_bazoo_rank")
public class HumanBazooRankEntity implements BaseEntity<Long>{

	private long id;
	private long passportId;
	private String name;
	private String headImg;
	private long dayProfit;
	private long weekProfit;
	private long monthProfit;
	private Integer bazooAgentDisplay;
	private Integer bazooRobotDisplay;
	
	@Id
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
		
	}

	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	public long getDayProfit() {
		return dayProfit;
	}

	public void setDayProfit(long dayProfit) {
		this.dayProfit = dayProfit;
	}

	public long getWeekProfit() {
		return weekProfit;
	}

	public void setWeekProfit(long weekProfit) {
		this.weekProfit = weekProfit;
	}

	public long getMonthProfit() {
		return monthProfit;
	}

	public void setMonthProfit(long monthProfit) {
		this.monthProfit = monthProfit;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public Integer getBazooAgentDisplay() {
		return bazooAgentDisplay;
	}

	public void setBazooAgentDisplay(Integer bazooAgentDisplay) {
		this.bazooAgentDisplay = bazooAgentDisplay;
	}

	public Integer getBazooRobotDisplay() {
		return bazooRobotDisplay;
	}

	public void setBazooRobotDisplay(Integer bazooRobotDisplay) {
		this.bazooRobotDisplay = bazooRobotDisplay;
	}

	

	
	
	
}
