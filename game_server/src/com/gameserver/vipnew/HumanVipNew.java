package com.gameserver.vipnew;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.db.model.HumanVipNewEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
/**
 * 
 * @author 郭君伟
 *
 */
public class HumanVipNew implements PersistanceObject<Long, HumanVipNewEntity> {
	
	private Human owner;
	
	private final LifeCycle lifeCycle;
	
	 private long id;
	 private long humanId;
	 private int vipLevel;
	 /**当前vip点 **/
	 private int curPoint;
	 private long updateTime;
	 private long createTime;
	 
	 
	 private boolean inDb;
	 
	
	public HumanVipNew() {
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	
	public Human getOwner() {
		Assert.notNull(owner);
		return owner;
	}
	
	public void setOwner(Human owner) {
		this.owner = owner;
	}
	
	@Override
	public void setDbId(Long id) {
		this.id = id;
	}

	@Override
	public Long getDbId() {
		return id;
	}
	
	

	public long getHumanId() {
		return humanId;
	}


	public void setHumanId(long humanId) {
		this.humanId = humanId;
	}


	public int getVipLevel() {
		return vipLevel;
	}


	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
		Globals.getClubService().updateHumanInfoToClub(owner);
	}


	public int getCurPoint() {
		return curPoint;
	}


	public void setCurPoint(int curPoint) {
		this.curPoint = curPoint;
	}


	public long getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}


	public long getCreateTime() {
		return createTime;
	}


	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}


	@Override
	public String getGUID() {
		return "vipNew#"+this.id;
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
		return humanId;
	}

	@Override
	public HumanVipNewEntity toEntity() {
		
		HumanVipNewEntity entity = new HumanVipNewEntity();
		entity.setId(id);
		entity.setHumanId(humanId);
		entity.setVipLevel(vipLevel);
		entity.setCurPoint(curPoint);
		entity.setUpdateTime(updateTime);
		entity.setCreateTime(createTime);
		
		return entity;
	}

	@Override
	public void fromEntity(HumanVipNewEntity entity) {
		this.id = entity.getId();
		this.humanId = entity.getHumanId();
		this.vipLevel = entity.getVipLevel();
		this.curPoint = entity.getCurPoint();
		this.updateTime = entity.getUpdateTime();
		this.createTime = entity.getCreateTime();
		this.setInDb(true);
		this.active();
	}
	
	/**
	 * 激活此2关系
	 */
	public void active(){
		getLifeCycle().activate();
	}
	
	

	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	@Override
	public void setModified() {
		onUpdate();
	}
	
	private void onUpdate()
	{
		if (owner != null) 
		{
			// TODO 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive())
			{
				// 物品的生命期处于活动状态,并且该宠物不是空的,则执行通知更新机制进行
				long now = Globals.getTimeService().now();
				this.updateTime = now ;
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}

}
