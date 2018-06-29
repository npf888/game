package com.gameserver.treasury;

import java.util.Date;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.TimeUtils;
import com.db.model.HumanTreasuryEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
/**
 * 储钱罐
 * @author JavaServer
 *
 */
public class HumanTreasury implements PersistanceObject<Long, HumanTreasuryEntity> {

	
	
	private Human owner;
	private final LifeCycle lifeCycle;
	private long id;
	private long chartId;
	private int type;
	private long gold;
	private long maxTreasury;
	private int factorTreasury;
	private int vipPointTreasury;
	private Date createTime;
	private Date updateTime;
	/** 是否已经在数据库中 */
	private boolean inDb;
	
	public HumanTreasury(){
		this.lifeCycle = new LifeCycleImpl(this);
		this.lifeCycle.activate();
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


	public long getChartId() {
		return chartId;
	}

	public void setChartId(long chartId) {
		this.chartId = chartId;
	}


	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}


	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}

	public int getFactorTreasury() {
		return factorTreasury;
	}


	public void setFactorTreasury(int factorTreasury) {
		this.factorTreasury = factorTreasury;
	}


	public int getVipPointTreasury() {
		return vipPointTreasury;
	}

	public void setVipPointTreasury(int vipPointTreasury) {
		this.vipPointTreasury = vipPointTreasury;
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


	public long getMaxTreasury() {
		return maxTreasury;
	}

	public void setMaxTreasury(long maxTreasury) {
		this.maxTreasury = maxTreasury;
	}

	@Override
	public void setDbId(Long id) {
		this.id=id;
		
	}

	@Override
	public Long getDbId() {
		return id;
	}

	@Override
	public String getGUID() {
		return "humanTreasury#"+this.id;
	}

	@Override
	public boolean isInDb() {
		return inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.inDb=inDb;
		
	}

	@Override
	public long getCharId() {
		return chartId;
	}

	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}
	/**
	 * 激活此关系
	 */
	public void active() 
	{
		getLifeCycle().activate();
	}
	@Override
	public void setModified() {
		// TODO Auto-generated method stub
		onUpdate();
	}
	
	/**
	 * 关系被更新回调处理
	 */
	public void onUpdate()
	{

		if (owner != null) 
		{
			// TODO 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
			
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive())
			{
				// 物品的生命期处于活动状态,并且该宠物不是空的,则执行通知更新机制进行
				long now = Globals.getTimeService().now();
				this.updateTime = TimeUtils.formatYMDHMSTimeToDate(now);
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}

	@Override
	public HumanTreasuryEntity toEntity() {
		HumanTreasuryEntity humanTreasuryEntity = new HumanTreasuryEntity();
		humanTreasuryEntity.setId(getDbId());
		humanTreasuryEntity.setChartId(getChartId());
		humanTreasuryEntity.setGold(getGold());
		humanTreasuryEntity.setType(getType());
		humanTreasuryEntity.setFactorTreasury(getFactorTreasury());
		humanTreasuryEntity.setVipPointTreasury(getVipPointTreasury());
		humanTreasuryEntity.setCreateTime(getCreateTime());
		humanTreasuryEntity.setUpdateTime(getUpdateTime());
		humanTreasuryEntity.setMaxTreasury(getMaxTreasury());
		return humanTreasuryEntity;
	}

	@Override
	public void fromEntity(HumanTreasuryEntity entity) {
		this.setId(entity.getId());
		this.setChartId(entity.getChartId());
		this.setGold(entity.getGold());
		this.setType(entity.getType());
		this.setFactorTreasury(entity.getFactorTreasury());
		this.setVipPointTreasury(entity.getVipPointTreasury());
		this.setCreateTime(entity.getCreateTime());
		this.setUpdateTime(entity.getUpdateTime());
		this.setMaxTreasury(entity.getMaxTreasury());
		this.setInDb(true);
		this.active();
	}

}
