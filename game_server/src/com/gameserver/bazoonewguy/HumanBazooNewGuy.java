package com.gameserver.bazoonewguy;

import java.util.Date;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.HumanBazooNewGuyEntity;
import com.gameserver.human.Human;

public class HumanBazooNewGuy implements PersistanceObject<Long, HumanBazooNewGuyEntity>{
	
	private Human owner;
	private boolean inDb;
	private LifeCycle lifeCycle;
	
	private long id;
	private long userId;
	private Integer type;
	private Integer process;
	private Date createTime;
	private Date updateTime;

	public HumanBazooNewGuy(Human human){
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
		return "HumanBazooNewGuy#"+this.id;
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
		return this.userId;
	}
	public Human getOwner() {
		return owner;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getProcess() {
		return process;
	}

	public void setProcess(Integer process) {
		this.process = process;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void active(){
		getLifeCycle().activate();
	}
	
	
	@Override
	public HumanBazooNewGuyEntity toEntity() {
		HumanBazooNewGuyEntity entity = new HumanBazooNewGuyEntity();
		entity.setId(id);
		entity.setUserId(userId);
		entity.setType(type);
		entity.setProcess(process);
		entity.setCreateTime(createTime);
		entity.setUpdateTime(updateTime);
		return entity;
	}

	@Override
	public void fromEntity(HumanBazooNewGuyEntity entity) {
		this.setId(entity.getId());
		this.setUserId(entity.getUserId());
		this.setProcess(entity.getType());
		this.setType(entity.getType());
		this.setCreateTime(entity.getCreateTime());
		this.setUpdateTime(entity.getUpdateTime());
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
