package com.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_club_member_list")
public class ClubMemberEntity implements BaseEntity<Long> {
	
	private static final long serialVersionUID = -7347903143515781343L;
	
	private long id;
	
	private long club_id;
	
	private long sign_time;
	
	private long donate_time;
	
	private Date join_time;

	private int zhiwu;
	
	private int gongxian;
	
	private int huoyue;
	
	private int sex;
	
	private int vote_agree;
	
	private String agree_ids = "";

	private String refuse_ids = "";
	
	@Column
	public String getRefuse_ids() {
		return refuse_ids;
	}

	public void setRefuse_ids(String refuse_ids) {
		this.refuse_ids = refuse_ids;
	}

	@Column
	public String getAgree_ids() {
		return agree_ids;
	}

	public void setAgree_ids(String agree_ids) {
		this.agree_ids = agree_ids;
	}

	@Column
	public int getVote_agree() {
		return vote_agree;
	}

	public void setVote_agree(int vote_agree) {
		this.vote_agree = vote_agree;
	}

	@Column
	public long getClub_id() {
		return club_id;
	}

	public void setClub_id(long club_id) {
		this.club_id = club_id;
	}
	
	@Column
	public long getSign_time() {
		return sign_time;
	}

	public void setSign_time(long sign_time) {
		this.sign_time = sign_time;
	}

	@Column
	public Date getJoin_time() {
		return join_time;
	}

	public void setJoin_time(Date join_time) {
		this.join_time = join_time;
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
	public int getZhiwu() {
		return zhiwu;
	}

	public void setZhiwu(int zhiwu) {
		this.zhiwu = zhiwu;
	}
	@Column
	public int getGongxian() {
		return gongxian;
	}

	public void setGongxian(int gongxian) {
		this.gongxian = gongxian;
	}
	@Column
	public int getHuoyue() {
		return huoyue;
	}

	public void setHuoyue(int huoyue) {
		this.huoyue = huoyue;
	}

	@Column
	public long getDonate_time() {
		return donate_time;
	}

	public void setDonate_time(long donate_time) {
		this.donate_time = donate_time;
	}
	@Column
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
	
}
