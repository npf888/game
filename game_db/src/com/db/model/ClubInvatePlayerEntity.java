package com.db.model;

import com.core.orm.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_club_invate_players")
public class ClubInvatePlayerEntity implements BaseEntity<Long> {

	private static final long serialVersionUID = -559914095621334115L;

	private long owner_id;
	private long invate_player_id;
	private Date invate_time;
	@Id
	@Column
	public long getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(long owner_id) {
		this.owner_id = owner_id;
	}
	@Id
	@Column
	public long getInvate_player_id() {
		return invate_player_id;
	}

	public void setInvate_player_id(long invate_player_id) {
		this.invate_player_id = invate_player_id;
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
		return null;
	}

	@Override
	public void setId(Long aLong) {
	}
}
