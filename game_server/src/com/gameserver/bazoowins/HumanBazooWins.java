package com.gameserver.bazoowins;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.HumanBazooWinsEntity;
import com.gameserver.human.Human;

public class HumanBazooWins implements PersistanceObject<Long, HumanBazooWinsEntity>{
	
	private Human owner;
	private boolean inDb;
	private LifeCycle lifeCycle;
	
	private long id;
	private long passportId;
	private int modeType;
	private int winTimes;
	
	
	public HumanBazooWins(Human human){
		this.lifeCycle = new LifeCycleImpl(this);
		this.owner=human;
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
		return "HumanBazooWins#"+id;
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
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	public int getModeType() {
		return modeType;
	}

	public void setModeType(int modeType) {
		this.modeType = modeType;
	}

	public int getWinTimes() {
		return winTimes;
	}

	public void setWinTimes(int winTimes) {
		this.winTimes = winTimes;
	}

	@Override
	public HumanBazooWinsEntity toEntity() {
		HumanBazooWinsEntity entity = new HumanBazooWinsEntity();
		entity.setId(id);
		entity.setModeType(modeType);
		entity.setPassportId(passportId);
		entity.setWinTimes(winTimes);
		return entity;
	}
	@Override
	public void fromEntity(HumanBazooWinsEntity entity) {
		this.setId(entity.getId());
		this.setModeType(entity.getModeType());
		this.setPassportId(entity.getPassportId());
		this.setWinTimes(entity.getWinTimes());
		this.setInDb(true);
		this.active();
		
	}
	@Override
	public LifeCycle getLifeCycle() {
		return this.lifeCycle;
	}
	
	public void active(){
		getLifeCycle().activate();
	}
	
	public void onDelete()
	{
		this.lifeCycle.destroy();
		owner.getPlayer().getDataUpdater().addDelete(this.getLifeCycle());
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
