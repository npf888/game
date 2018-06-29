package com.gameserver.baccart;



import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.db.model.HumanBaccartEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

/**
 * 百家乐
 * @author wayne
 *
 */
public class HumanBaccart  implements PersistanceObject<Long, HumanBaccartEntity>{

	
	private Human owner;
	private final LifeCycle lifeCycle;
	private long id;
	private long charId;
	private long bankerNum;
	private long gameNum;
	private long winNum;
	private long beaconNum;
	private long lostNum;
	private int isAuto;
	private long updateTime;
	private long createTime;
	private boolean inDb;
	
	public HumanBaccart()
	{
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
	public LifeCycle getLifeCycle() {
	
		return lifeCycle;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBankerNum() {
		return bankerNum;
	}


	public void setBankerNum(long bankerNum) {
		this.bankerNum = bankerNum;
	}


	public long getGameNum() {
		return gameNum;
	}


	public void setGameNum(long gameNum) {
		this.gameNum = gameNum;
	}


	public long getWinNum() {
		return winNum;
	}


	public void setWinNum(long winNum) {
		this.winNum = winNum;
	}


	public long getBeaconNum() {
		return beaconNum;
	}


	public void setBeaconNum(long beaconNum) {
		this.beaconNum = beaconNum;
	}


	public long getLostNum() {
		return lostNum;
	}


	public void setLostNum(long lostNum) {
		this.lostNum = lostNum;
	}


	public int getIsAuto() {
		return isAuto;
	}


	public void setIsAuto(int isAuto) {
		this.isAuto = isAuto;
	}


	public void setCharId(long charId) {
		this.charId = charId;
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

		return "baccart#"+this.id;
	}

	@Override
	public boolean isInDb() {
	
		return inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		
		this.inDb = inDb;
	}


	public long getCharId() {
	
		return charId;
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
	
	
	@Override
	public HumanBaccartEntity toEntity() {
		// TODO Auto-generated method stub
		HumanBaccartEntity entity = new HumanBaccartEntity();
		entity.setId(getDbId());
		entity.setCharId(getCharId());
		entity.setBankerNum(getBankerNum());
		entity.setGameNum(getGameNum());
		entity.setLostNum(getLostNum());
		entity.setWinNum(getWinNum());
		entity.setBeaconNum(getBeaconNum());
		entity.setIsAuto(getIsAuto());
		entity.setCreateTime(getCreateTime());
		entity.setUpdateTime(getUpdateTime());
		
		return entity;
	}

	@Override
	public void fromEntity(HumanBaccartEntity entity) {
		// TODO Auto-generated method stub
	
		this.id = entity.getId();
		this.charId = entity.getCharId();
		this.bankerNum = entity.getBankerNum();
		this.gameNum = entity.getGameNum();
		this.lostNum = entity.getLostNum();
		this.winNum = entity.getWinNum();
		this.beaconNum = entity.getBeaconNum();
		this.isAuto = entity.getIsAuto();
		this.updateTime = entity.getUpdateTime();
		this.createTime = entity.getCreateTime();
		this.setInDb(true);
		this.active();
	
	}



}
