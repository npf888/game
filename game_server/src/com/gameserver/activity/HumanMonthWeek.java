package com.gameserver.activity;

import java.util.Date;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.HumanMonthWeekEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

public class HumanMonthWeek implements PersistanceObject<Long, HumanMonthWeekEntity>{
	
	
	private final LifeCycle lifeCycle;
	private Human owner;
	private boolean inDb; 
	
	private long id;
	private long userId;
	private Date startTime;
	private long continueTime;
	private int mwType;
	//周 或者 当月 是否购买（一周 或者一个月只限购一次）
	private int isBuy;
	private Date createTime;
	private Date updateTime;
	
	
	public HumanMonthWeek(Human owner){
		this.owner=owner;
		this.lifeCycle = new LifeCycleImpl(this);
	}
	public Human getOwner() {
		return owner;
	}

	public long getContinueTime() {
		return continueTime;
	}


	public void setContinueTime(long continueTime) {
		this.continueTime = continueTime;
	}




	public void setOwner(Human owner) {
		this.owner = owner;
	}




	public int getIsBuy() {
		return isBuy;
	}
	public void setIsBuy(int isBuy) {
		this.isBuy = isBuy;
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
	public int getMwType() {
		return mwType;
	}
	public void setMwType(int mwType) {
		this.mwType = mwType;
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
		return "humanMonthWeek#"+this.id;
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
		return this.owner.getPassportId();
	}
	@Override
	public HumanMonthWeekEntity toEntity() {
		HumanMonthWeekEntity entity = new HumanMonthWeekEntity();
		entity.setId(this.getDbId());
		entity.setUserId(this.getUserId());
		entity.setStartTime(this.getStartTime());
		entity.setMwType(this.getMwType());
		entity.setContinueTime(this.getContinueTime());
		entity.setUpdateTime(this.getUpdateTime());
		entity.setCreateTime(this.getCreateTime());
		entity.setIsBuy(this.getIsBuy());
		return entity;
	}
	@Override
	public void fromEntity(HumanMonthWeekEntity entity) {
		this.setDbId(entity.getId());
		this.setStartTime(entity.getStartTime());
		this.setUserId(entity.getUserId());
		this.setMwType(entity.getMwType());
		this.setUpdateTime(entity.getUpdateTime());
		this.setCreateTime(entity.getCreateTime());
		this.setContinueTime(entity.getContinueTime());
		this.setIsBuy(entity.getIsBuy());
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
	
	/**
	 * 关系被更新回调处理
	 */
	public void onUpdate()
	{

		if (owner != null) 
		{
			
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive())
			{
				// 物品的生命期处于活动状态,并且该宠物不是空的,则执行通知更新机制进行
				long now = Globals.getTimeService().now();
				this.updateTime = new Date(now) ;
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}
	
	

}
