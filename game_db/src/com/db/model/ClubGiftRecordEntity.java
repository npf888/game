package com.db.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_club_gift_record")
public class ClubGiftRecordEntity implements BaseEntity<Long>
{
	private static final long serialVersionUID = -3571075326687853589L;
	
	private long id;
	private String clubId;
	private long senderPassportId;
	private long ts;
	
	@Id
	@Override
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClubId() {
		return clubId;
	}
	public void setClubId(String clubId) {
		this.clubId = clubId;
	}
	public long getSenderPassportId() {
		return senderPassportId;
	}
	public void setSenderPassportId(long senderPassportId) {
		this.senderPassportId = senderPassportId;
	}
	public long getTs() {
		return ts;
	}
	public void setTs(long ts) {
		this.ts = ts;
	}
	
}