package com.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_club_invate")
public class ClubInvateEntity  implements BaseEntity<Long> {

	private static final long serialVersionUID = -6852443221445723295L;
	private long club_id;
	private long player_id;
	private Date invate_time;
	
	@Id
	@Column
	public long getClub_id() {
		return club_id;
	}

	public void setClub_id(long club_id) {
		this.club_id = club_id;
	}
	@Id
	@Column
	public long getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(long player_id) {
		this.player_id = player_id;
	}
	@Column
	public Date getInvate_time() {
		return invate_time;
	}

	public void setInvate_time(Date invate_time) {
		this.invate_time = invate_time;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}

	
	
}
