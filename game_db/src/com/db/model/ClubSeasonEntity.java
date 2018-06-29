package com.db.model;

import com.core.orm.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_club_season")
public class ClubSeasonEntity implements BaseEntity<Long>{
	private static final long serialVersionUID = -7347903153515781343L;

	private long id;
	
	private long end_time;

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
	public long getEnd_time() {
		return end_time;
	}

	public void setEnd_time(long end_time) {
		this.end_time = end_time;
	}

}
