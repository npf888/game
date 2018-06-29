package com.gameserver.vip;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.core.util.TimeUtils;
import com.db.model.HumanVipEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

/**
 * vip
 * @author wayne
 *
 */
public class HumanVip  implements PersistanceObject<Long, HumanVipEntity>{
	private Human owner;
	private final LifeCycle lifeCycle;
	private long id;
	private long charId;
	private boolean inDb;
	
	private int level;
	/**上次领取时间*/
	private long getTime;
	private long buyTime;
	private int days;
	
	/** 更新时间 */
	private long updateTime;
	/** 创建时间 */
	private long createTime;
	
	public HumanVip(){
		this.lifeCycle = new LifeCycleImpl(this);
		
	}
	public Human getOwner() {
		Assert.notNull(owner);
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
	
	public void setCharId(long charId) {
		this.charId = charId;
	}
	
	@Override
	public long getCharId() {
		// TODO Auto-generated method stub
		return charId;
	}
	
	@Override
	public void setDbId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
	@Override
	public Long getDbId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public String getGUID() {
		// TODO Auto-generated method stub
		return "vip#"+this.id;
	}

	@Override
	public boolean isInDb() {
		// TODO Auto-generated method stub
		return inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		// TODO Auto-generated method stub
		this.inDb = inDb;
	}


	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public long getGetTime() {
		return getTime;
	}

	public void setGetTime(long getTime) {
		this.getTime = getTime;
	}


	public long getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(long buyTime) {
		this.buyTime = buyTime;
	}
	/**
	 * @return the days
	 */
	public int getDays() {
		return days;
	}
	/**
	 * @param days the days to set
	 */
	public void setDays(int days) {
		this.days = days;
	}
	
	public long getEndTime(){
		return TimeUtils.getBeginOfDay(this.getBuyTime()+ TimeUtils.DAY*this.getDays())+TimeUtils.DAY-1;
		
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
	public LifeCycle getLifeCycle() {
		// TODO Auto-generated method stub
		return lifeCycle;
	}

	@Override
	public void setModified() {
		// TODO Auto-generated method stub
		onUpdate();
	}

	@Override
	public HumanVipEntity toEntity() {
		// TODO Auto-generated method stub
		HumanVipEntity entity = new HumanVipEntity();
		entity.setId(getDbId());
		entity.setCharId(getCharId());
		entity.setLevel(getLevel());
		entity.setGetTime(getGetTime());
		entity.setBuyTime(getBuyTime());
		entity.setDays(getDays());
		entity.setCreateTime(getCreateTime());
		entity.setUpdateTime(getUpdateTime());
		return entity;
	}

	@Override
	public void fromEntity(HumanVipEntity entity) {
		// TODO Auto-generated method stub
		this.id = entity.getId();
		this.charId = entity.getCharId();
		this.getTime = entity.getGetTime();
		this.level = entity.getLevel();
		this.buyTime = entity.getBuyTime();
		this.days = entity.getDays();
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

