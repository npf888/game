package com.gameserver.bazoosignin;

import java.util.Date;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.HumanBazooSigninEntity;
import com.gameserver.human.Human;

public class HumanBazooSignIn implements PersistanceObject<Long, HumanBazooSigninEntity>{
	private Human owner;
	private final LifeCycle lifeCycle;
	private boolean inDb;
	
	private long id;
	private long passportId;
	private int times;
	private Date signInTime;//签到时间
	
	
	public  HumanBazooSignIn(Human human) {
		this.lifeCycle = new LifeCycleImpl(this);
		this.owner=human;
	}
	
	
	public Human getOwner() {
		return owner;
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
		return "BazooSignIn#"+this.id;
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
		return this.passportId;
	}

	public long getPassportId() {
		return passportId;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}


	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}


	public Date getSignInTime() {
		return signInTime;
	}


	public void setSignInTime(Date signInTime) {
		this.signInTime = signInTime;
	}

	public void active(){
		getLifeCycle().activate();
	}
	

	@Override
	public HumanBazooSigninEntity toEntity() {
		HumanBazooSigninEntity entity = new HumanBazooSigninEntity();
		entity.setId(id);
		entity.setPassportId(passportId);
		entity.setSignInTime(signInTime);
		entity.setTimes(times);
		return entity;
	}

	@Override
	public void fromEntity(HumanBazooSigninEntity entity) {
		this.setDbId(entity.getId());
		this.setPassportId(entity.getPassportId());
		this.setSignInTime(entity.getSignInTime());
		this.setTimes(entity.getTimes());
		this.setInDb(true);
		this.active();
	}

	@Override
	public LifeCycle getLifeCycle() {
		return this.lifeCycle;
	}

	@Override
	public void setModified() {
		onUpdate();
		
	}
	
	private void onUpdate()
	{
		if (owner != null) 
		{
			
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive())
			{
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}

}
