package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

import java.util.Date;

@Entity
@Table(name = "t_club_applys")
public class ClubApplyEntity implements BaseEntity<Long> {

	private static final long serialVersionUID = -559914095629334115L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Long id;
	private String club_id;
	private long player_id;
	private Date apply_time;

	@Override
	public Long getId()
	{
		return id;
	}

	@Override
	public void setId(Long id)
	{
		this.id = id;
	}
	
	@Column
	public String getClub_id() {
		return club_id;
	}

	public void setClub_id(String club_id) {
		this.club_id = club_id;
	}

	@Column
	public Date getApply_time() {
		return apply_time;
	}

	public void setApply_time(Date apply_time) {
		this.apply_time = apply_time;
	}


	@Column
	public long getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(long player_id) {
		this.player_id = player_id;
	}

}
