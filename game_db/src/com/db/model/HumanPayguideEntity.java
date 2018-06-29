package com.db.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_human_payguide")
public class HumanPayguideEntity implements BaseEntity<Long>{

	private static final long serialVersionUID = -559914092321566115L;
	
	
	private long id;
	private long userId;
	private int treasury;
	private int exp;
	private int preference;
	private int club;
	private int monthcard;
	private int coupon;
	private Date time;

	
	
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getTreasury() {
		return treasury;
	}

	public void setTreasury(int treasury) {
		this.treasury = treasury;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getPreference() {
		return preference;
	}

	public void setPreference(int preference) {
		this.preference = preference;
	}

	public int getClub() {
		return club;
	}

	public void setClub(int club) {
		this.club = club;
	}

	public int getMonthcard() {
		return monthcard;
	}

	public void setMonthcard(int monthcard) {
		this.monthcard = monthcard;
	}

	public int getCoupon() {
		return coupon;
	}

	public void setCoupon(int coupon) {
		this.coupon = coupon;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Id
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}

}
