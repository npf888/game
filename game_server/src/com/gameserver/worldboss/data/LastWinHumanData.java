package com.gameserver.worldboss.data;

import java.util.List;

import com.db.model.HumanAttackBossEntity;
import com.db.model.HumanEntity;
import com.db.model.HumanVipNewEntity;

/**
 * 上一次攻击最多的那个 家伙
 * @author JavaServer
 *
 */
public class LastWinHumanData {

	private long userId;
	private long level;
	private int vipLevel;
	private String country;
	private String head;
	private String name;
	private long attackTotalGold;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getLevel() {
		return level;
	}
	public void setLevel(long level) {
		this.level = level;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public long getAttackTotalGold() {
		return attackTotalGold;
	}
	public void setAttackTotalGold(long attackTotalGold) {
		this.attackTotalGold = attackTotalGold;
	}
	
	public int getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LastWinHumanData getSelf(HumanAttackBossEntity humanAttackBossEntity,List<HumanEntity> humanEntity, HumanVipNewEntity humanVipNewEntity){
		
		if(humanEntity != null && humanEntity.size() >0){
			HumanEntity human = humanEntity.get(0);
			this.setCountry(human.getCountries());
			this.setHead(human.getImg());
			this.setUserId(human.getPassportId());
			this.setLevel(human.getLevel());
			this.setVipLevel(humanVipNewEntity.getVipLevel());
			this.setName(human.getName());
		}
		this.setAttackTotalGold(humanAttackBossEntity.getAttackAllTotalBlood());
		
		return this;
	}
public LastWinHumanData getSelf(long AttackAllTotalBlood,List<HumanEntity> humanEntity, HumanVipNewEntity humanVipNewEntity){
		
		if(humanEntity != null && humanEntity.size() >0){
			HumanEntity human = humanEntity.get(0);
			this.setCountry(human.getCountries());
			this.setHead(human.getImg());
			this.setUserId(human.getPassportId());
			this.setLevel(human.getLevel());
			this.setVipLevel(humanVipNewEntity.getVipLevel());
			this.setName(human.getName());
		}
		this.setAttackTotalGold(AttackAllTotalBlood);
		
		return this;
	}
}
