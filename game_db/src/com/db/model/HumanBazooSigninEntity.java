package com.db.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;
@Entity
@Table(name = "t_human_bazoo_signin")
public class HumanBazooSigninEntity implements BaseEntity<Long>{
	private static final long serialVersionUID = 1311457506389662630L;
	private long id;
	private long passportId;
	private int times;
	private Date signInTime;//签到时间
	
	
	
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

	public Date getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(Date signInTime) {
		this.signInTime = signInTime;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
	
	
	
	
	

}
