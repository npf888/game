package com.gameserver.guide.pojo;

import java.util.Date;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.HumanPayguideEntity;
import com.gameserver.human.Human;

public class HumanPayguide implements PersistanceObject<Long, HumanPayguideEntity> {

	private long id;
	private long userId;
	private int treasury;
	private int exp;
	private int preference;
	private int club;
	private int monthcard;
	private int coupon;
	private Date time;
	private Human owner;
	
	private final LifeCycle lifeCycle;
	/** false 数据库没有 执行save 操作 true 执行update **/
	private boolean inDb;
	public HumanPayguide(Human human){
		this.owner=human;
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getTreasury() {
		return treasury;
	}

	public void setTreasury(int treasury) {
		this.treasury = treasury;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getPreference() {
		return preference;
	}

	public void setPreference(int preference) {
		this.preference = preference;
	}

	public int getClub() {
		return club;
	}

	public void setClub(int club) {
		this.club = club;
	}

	public int getMonthcard() {
		return monthcard;
	}

	public void setMonthcard(int monthcard) {
		this.monthcard = monthcard;
	}

	public int getCoupon() {
		return coupon;
	}

	public void setCoupon(int coupon) {
		this.coupon = coupon;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Human getOwner() {
		return owner;
	}


	public void setOwner(Human owner) {
		this.owner = owner;
	}


	@Override
	public void setDbId(Long id) {
		this.id=id;
		
	}

	@Override
	public Long getDbId() {
		return this.id;
	}

	@Override
	public String getGUID() {
		return "HumanPayguide#"+this.id;
	}

	@Override
	public boolean isInDb() {
		return this.inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.inDb=inDb;
		
	}

	@Override
	public long getCharId() {
		return owner.getPassportId();
	}

	@Override
	public HumanPayguideEntity toEntity() {
		HumanPayguideEntity entity = new HumanPayguideEntity();
		entity.setId(this.getId());
		entity.setUserId(this.getUserId());
		entity.setTreasury(this.getTreasury());
		entity.setPreference(this.getPreference());
		entity.setMonthcard(this.getMonthcard());
		entity.setExp(this.getExp());
		entity.setCoupon(this.getCoupon());
		entity.setClub(this.getClub());
		entity.setTime(this.getTime());
		return entity;
	}

	@Override
	public void fromEntity(HumanPayguideEntity entity) {
		this.setId(entity.getId());
		this.setUserId(entity.getUserId());
		this.setTreasury(entity.getTreasury());
		this.setPreference(entity.getPreference());
		this.setMonthcard(entity.getMonthcard());
		this.setExp(entity.getExp());
		this.setCoupon(entity.getCoupon());
		this.setClub(entity.getClub());
		this.setTime(entity.getTime());
		this.setInDb(true);
		this.active();
	}

	public void active() {
		this.lifeCycle.activate();
		
	}




	@Override
	public LifeCycle getLifeCycle() {
		return this.lifeCycle;
	}

	@Override
	public void setModified() {
		onUpdate();
	}

	private void onUpdate(){
		
		if (owner != null) {
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive()){
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}

}
