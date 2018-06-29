package com.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_club")
public class ClubEntity implements BaseEntity<Long>{
	private static final long serialVersionUID = -7347903153515781343L;

	private long id;
	
	private int ico;
	
	private int club_limit;
	
	private int level;
	
	private int club_type;
	
	private int max_num;
	
	private int money;
	
	private int huoyue;
	
	private Date create_time;
	
	private String name;
	
	private String notice;
	
	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	@Column
	public int getMax_num() {
		return max_num;
	}

	public void setMax_num(int max_num) {
		this.max_num = max_num;
	}
	@Column
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	@Column
	public int getHuoyue() {
		return huoyue;
	}

	public void setHuoyue(int huoyue) {
		this.huoyue = huoyue;
	}

	
	@Column
	public int getIco() {
		return ico;
	}

	public void setIco(int ico) {
		this.ico = ico;
	}
	@Column
	public int getClub_limit() {
		return club_limit;
	}

	public void setClub_limit(int club_limit) {
		this.club_limit = club_limit;
	}
	@Column
	public int getClub_type() {
		return club_type;
	}

	public void setClub_type(int club_type) {
		this.club_type = club_type;
	}
	@Column
	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

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
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	
	
}
