package com.gameserver.gift;

import java.util.Date;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.HumanNewComerEntity;
import com.gameserver.human.Human;

public class HumanNewComer implements PersistanceObject<Long, HumanNewComerEntity> {
	
	
	private Human owner;
	
	private final LifeCycle lifeCycle;
	/** false 数据库没有 执行save 操作 true 执行update **/
	private boolean inDb;
	
	
	
	private long id;
	
	//用户ID
	private long userId;
	//每天第一次登录时间（这个不会变）
	private Date startTime;
	//每天的 第一次登录时间 （这个会变，根据剩余时间）
	private Date perStartTime;
	
	public HumanNewComer(Human human){
		this.owner=human;
		this.lifeCycle = new LifeCycleImpl(this);
	}
	@Override
	public void setDbId(Long id) {
		this.id = id;
		
	}
	@Override
	public Long getDbId() {
		return id;
	}
	@Override
	public String getGUID() {
		return "HumanNewComer#"+this.id;
	}
	@Override
	public boolean isInDb() {
		return inDb;
	}
	@Override
	public void setInDb(boolean inDb) {
		this.inDb = inDb;
		
	}
	@Override
	public long getCharId() {
		return userId;
	}
	@Override
	public HumanNewComerEntity toEntity() {
		HumanNewComerEntity entity = new HumanNewComerEntity();
		entity.setId(id);
		entity.setUserId(userId);
		entity.setStartTime(startTime);
		entity.setPerStartTime(perStartTime);
		return entity;
	}
	@Override
	public void fromEntity(HumanNewComerEntity entity) {
		this.id = entity.getId();
		this.userId = entity.getUserId();
		this.startTime = entity.getStartTime();
		this.perStartTime = entity.getPerStartTime();
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
			// TODO 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive()){
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getPerStartTime() {
		return perStartTime;
	}
	public void setPerStartTime(Date perStartTime) {
		this.perStartTime = perStartTime;
	}

	
	
	
	
}
