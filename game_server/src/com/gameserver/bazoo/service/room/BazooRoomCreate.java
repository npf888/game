package com.gameserver.bazoo.service.room;

import java.util.Date;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.BazooRoomCreateEntity;
import com.gameserver.common.Globals;

public class BazooRoomCreate implements PersistanceObject<Long, BazooRoomCreateEntity>{
	
	private boolean inDb;
	private LifeCycle lifeCycle;
	
	
	private long id;
	private String modeTypePriPubBet;
	private int number;
	private Date createTime;
	
	
	public BazooRoomCreate(){
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	public boolean isInDb() {
		return inDb;
	}
	public void setInDb(boolean inDb) {
		this.inDb = inDb;
	}
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(LifeCycle lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getModeTypePriPubBet() {
		return modeTypePriPubBet;
	}
	public void setModeTypePriPubBet(String modeTypePriPubBet) {
		this.modeTypePriPubBet = modeTypePriPubBet;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
		return "#BazooRoomCreate"+id;
	}
	@Override
	public long getCharId() {
		return 0;
	}
	
	public void active(){
		getLifeCycle().activate();
	}
	
	@Override
	public BazooRoomCreateEntity toEntity() {
		BazooRoomCreateEntity entity = new BazooRoomCreateEntity();
		entity.setId(id);
		entity.setModeTypePriPubBet(modeTypePriPubBet);
		entity.setNumber(number);
		entity.setCreateTime(new Date());
		return entity;
	}
	@Override
	public void fromEntity(BazooRoomCreateEntity entity) {
		this.setId(entity.getId());
		this.setModeTypePriPubBet(entity.getModeTypePriPubBet());
		this.setNumber(entity.getNumber());
		this.setCreateTime(entity.getCreateTime());
		this.setInDb(true);
		this.active();
		
	}
	@Override
	public void setModified() {
		onUpdate();
		
	}
	
	private void onUpdate()
	{
			
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive())
			{
				Globals.getGlobalLogicRunner().getGlobalDataUpdater().addUpdate(lifeCycle);
			}
	}
	
	
	
}
