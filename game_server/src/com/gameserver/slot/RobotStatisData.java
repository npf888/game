package com.gameserver.slot;

import java.util.Date;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.RobotStatisDataEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

public class RobotStatisData implements PersistanceObject<Long, RobotStatisDataEntity>{
	private Human owner;
	private long id;
	private String slotName;
	private long slotId;
	private long slotType;
	private long chartId;
	private long rewardCount;
	private long rewardUsed;
	private long payCount;
	private long scatterNum;
	private long bonusNum;
	private long scatterTriggerCount;
	private long scatterTriggerFreeCount;
	private long bonusTriggerCount;
	private long bonusTriggerFreeCount;
	private Date createTime;
	private Date updateTime;
	private boolean inDb;
	private final LifeCycle lifeCycle;
	
	
	public RobotStatisData(){
		this.lifeCycle = new LifeCycleImpl(this);
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
		return "robotStatisData#"+this.id;
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
	public RobotStatisDataEntity toEntity() {
		RobotStatisDataEntity entity = new RobotStatisDataEntity();
		entity.setSlotName(getSlotName());
		entity.setId(getDbId());
		entity.setSlotId(getSlotId());
		entity.setSlotType(getSlotType());
		entity.setChartId(getCharId());
		
		entity.setBonusNum(getBonusNum());
		entity.setBonusTriggerCount(getBonusTriggerCount());
		entity.setScatterNum(getScatterNum());
		entity.setScatterTriggerCount(getScatterTriggerCount());
		entity.setScatterTriggerFreeCount(getScatterTriggerFreeCount());
		entity.setBonusTriggerFreeCount(getBonusTriggerFreeCount());
		entity.setRewardCount(getRewardCount());
		entity.setRewardUsed(getRewardUsed());
		entity.setPayCount(getPayCount());
		
		entity.setCreateTime(getCreateTime());
		entity.setUpdateTime(getUpdateTime());
		return entity;
	}
	@Override
	public void fromEntity(RobotStatisDataEntity entity) {
		this.id = entity.getId();
		this.slotName = entity.getSlotName();
		this.slotId = entity.getSlotId();
		this.slotType = entity.getSlotType();
		this.chartId = entity.getChartId();
		this.bonusNum = entity.getBonusNum();
		this.bonusTriggerCount = entity.getBonusTriggerCount();
		this.bonusTriggerFreeCount=entity.getBonusTriggerFreeCount();
		this.scatterNum = entity.getScatterNum();
		this.scatterTriggerCount =entity.getScatterTriggerCount();
		this.scatterTriggerFreeCount =entity.getScatterTriggerFreeCount();
		this.rewardCount=entity.getRewardCount();
		this.rewardUsed=entity.getRewardUsed();
		this.payCount=entity.getPayCount();
		this.setCreateTime(entity.getCreateTime());
		this.setUpdateTime(entity.getUpdateTime());
		this.setInDb(true);
		this.active();
	}
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
				this.updateTime = new Date(now) ;
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}
	@Override
	public long getCharId() {
		return chartId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSlotId() {
		return slotId;
	}

	public void setSlotId(long slotId) {
		this.slotId = slotId;
	}

	public long getChartId() {
		return chartId;
	}

	public void setChartId(long chartId) {
		this.chartId = chartId;
	}

	public long getRewardCount() {
		return rewardCount;
	}

	public void setRewardCount(long rewardCount) {
		this.rewardCount = rewardCount;
	}

	public long getRewardUsed() {
		return rewardUsed;
	}

	public void setRewardUsed(long rewardUsed) {
		this.rewardUsed = rewardUsed;
	}

	public long getPayCount() {
		return payCount;
	}

	public void setPayCount(long payCount) {
		this.payCount = payCount;
	}

	public long getScatterNum() {
		return scatterNum;
	}

	public void setScatterNum(long scatterNum) {
		this.scatterNum = scatterNum;
	}

	public long getBonusNum() {
		return bonusNum;
	}

	public void setBonusNum(long bonusNum) {
		this.bonusNum = bonusNum;
	}

	public long getScatterTriggerCount() {
		return scatterTriggerCount;
	}

	public void setScatterTriggerCount(long scatterTriggerCount) {
		this.scatterTriggerCount = scatterTriggerCount;
	}

	public long getBonusTriggerCount() {
		return bonusTriggerCount;
	}

	public void setBonusTriggerCount(long bonusTriggerCount) {
		this.bonusTriggerCount = bonusTriggerCount;
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

	public long getScatterTriggerFreeCount() {
		return scatterTriggerFreeCount;
	}

	public void setScatterTriggerFreeCount(long scatterTriggerFreeCount) {
		this.scatterTriggerFreeCount = scatterTriggerFreeCount;
	}

	public long getSlotType() {
		return slotType;
	}

	public void setSlotType(long slotType) {
		this.slotType = slotType;
	}

	public String getSlotName() {
		return slotName;
	}

	public void setSlotName(String slotName) {
		this.slotName = slotName;
	}

	public long getBonusTriggerFreeCount() {
		return bonusTriggerFreeCount;
	}

	public void setBonusTriggerFreeCount(long bonusTriggerFreeCount) {
		this.bonusTriggerFreeCount = bonusTriggerFreeCount;
	}

	
	

}
