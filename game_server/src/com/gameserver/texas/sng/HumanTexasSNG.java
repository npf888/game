package com.gameserver.texas.sng;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.db.model.HumanTexasSNGEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

/**
 * sng
 * @author wayne
 *
 */
public class HumanTexasSNG implements PersistanceObject<Long, HumanTexasSNGEntity>{
	private Human owner;
	private final LifeCycle lifeCycle;
	private long id;
	private long charId;
	
	private int joinTimes;
	private int goldNum;
	private int silverNum;
	private int copperNum;
	private int weekScore;
	private long updateTime;
	private long createTime;
	private boolean inDb;
	
	public HumanTexasSNG(){
		
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	public void setOwner(Human owner){
		this.owner =owner;
	}
	
	public Human getOwner() {
		Assert.notNull(owner);
		return owner;
	}
	
	
	@Override
	public LifeCycle getLifeCycle() {
	
		return lifeCycle;
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
	
	public long getCharId() {
		
		return charId;
	}
	

	public int getJoinTimes() {
		return joinTimes;
	}

	public void setJoinTimes(int joinTimes) {
		this.joinTimes = joinTimes;
	}

	public int getGoldNum() {
		return goldNum;
	}

	public void setGoldNum(int goldNum) {
		this.goldNum = goldNum;
	}

	public int getSilverNum() {
		return silverNum;
	}

	public void setSilverNum(int silverNum) {
		this.silverNum = silverNum;
	}

	public int getCopperNum() {
		return copperNum;
	}

	public void setCopperNum(int copperNum) {
		this.copperNum = copperNum;
	}

	public int getWeekScore() {
		return weekScore;
	}

	public void setWeekScore(int weekScore) {
		this.weekScore = weekScore;
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
	public void setDbId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public Long getDbId() {
	
		return this.id;
	}
	


	@Override
	public String getGUID() {

		return "texassng#"+this.id;
	}

	@Override
	public boolean isInDb() {
	
		return inDb;
	}
	

	@Override
	public void setInDb(boolean inDb) {
		// TODO Auto-generated method stub
		this.inDb = inDb;
	}
	

	@Override
	public HumanTexasSNGEntity toEntity()
	{
		HumanTexasSNGEntity entity = new HumanTexasSNGEntity();
		entity.setId(getDbId());
		entity.setCharId(getCharId());
		entity.setCopperNum(getCopperNum());
		entity.setGoldNum(getGoldNum());
		entity.setSilverNum(getSilverNum());
		entity.setWeekScore(getWeekScore());
		entity.setJoinTimes(getJoinTimes());
		entity.setCreateTime(getCreateTime());
		entity.setUpdateTime(getUpdateTime());
		
		return entity;
	}

	@Override
	public void fromEntity(HumanTexasSNGEntity entity)
	{
		this.id = entity.getId();
		this.charId = entity.getCharId();
		this.joinTimes=entity.getJoinTimes();
		this.goldNum= entity.getGoldNum();
		this.silverNum = entity.getSilverNum();
		this.copperNum = entity.getCopperNum();
		this.weekScore = entity.getWeekScore();
	
		
		this.updateTime = entity.getUpdateTime();
		this.createTime = entity.getCreateTime();
		this.setInDb(true);
		this.active();
	}

	@Override
	public void setModified()
	{
		onUpdate();
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

