package com.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_club_invite")
public class ClubInviteEntity implements BaseEntity<Long> {
	private static final long serialVersionUID = -5952875450822459626L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Long id;
	private String club_id;
	private long player_id;
	private long invitee_id;
	private Date invite_time;

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
	public long getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(long player_id) {
		this.player_id = player_id;
	}
	@Column
	public Date getInvite_time() {
		return invite_time;
	}

	public void setInvite_time(Date invite_time) {
		this.invite_time = invite_time;
	}
	@Column
	public long getInvitee_id() {
		return invitee_id;
	}

	public void setInvitee_id(long invitee_id) {
		this.invitee_id = invitee_id;
	}

}
