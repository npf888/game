package com.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_club_note")
public class ClubNoteEntity implements BaseEntity<Long>{
	
	private static final long serialVersionUID = -7347903153515181343L;
	private long id;
	private Date create_time;
	private long player_id;
	private String msg;
	private int msg_type;
	private int zhiwu;
	private int money;
	private String ids;
	private int giftId;
	private long clubId;
	
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	@Column
	public long getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(long player_id) {
		this.player_id = player_id;
	}
	@Column
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Column
	public int getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(int msg_type) {
		this.msg_type = msg_type;
	}
	@Column
	public int getZhiwu() {
		return zhiwu;
	}
	public void setZhiwu(int zhiwu) {
		this.zhiwu = zhiwu;
	}
	@Column
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	@Column
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Id
	@Column
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
		
	}
	@Column
	public int getGiftId() {
		return giftId;
	}
	public void setGiftId(int giftId) {
		this.giftId = giftId;
	}
	@Column
	public long getClubId() {
		return clubId;
	}

	public void setClubId(long clubId) {
		this.clubId = clubId;
	}
}
