package com.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;
/**
 * 用户每次攻击boss的 数据
 * @author JavaServer
 *
 */
@Entity
@Table(name = "t_human_attack_boss")
public class HumanAttackBossEntity implements BaseEntity<Long>{
	
	private static final long serialVersionUID = -3380175577169459554L;
	private long id;
	private long userId;
	private long bossId;
	private long attackBlood;
	private long attackTotalBlood;
	private long attackAllTotalBlood;
	private Date createTime;
	
	
	@Id
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id=id;
		
	}
	
	@Column
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	@Column
	public long getBossId() {
		return bossId;
	}
	public void setBossId(long bossId) {
		this.bossId = bossId;
	}
	
	@Column
	public long getAttackBlood() {
		return attackBlood;
	}
	public void setAttackBlood(long attackBlood) {
		this.attackBlood = attackBlood;
	}
	
	@Column
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column
	public long getAttackTotalBlood() {
		return attackTotalBlood;
	}
	public void setAttackTotalBlood(long attackTotalBlood) {
		this.attackTotalBlood = attackTotalBlood;
	}
	public long getAttackAllTotalBlood() {
		return attackAllTotalBlood;
	}
	public void setAttackAllTotalBlood(long attackAllTotalBlood) {
		this.attackAllTotalBlood = attackAllTotalBlood;
	}
	
	
	
	
}
