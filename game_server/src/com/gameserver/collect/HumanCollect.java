package com.gameserver.collect;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.core.util.StringUtils;
import com.db.model.HumanCollectEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

/**
 * 
 * @author 郭君伟
 *
 */
public class HumanCollect implements PersistanceObject<Long, HumanCollectEntity> {

	private Human owner;
	
	private final LifeCycle lifeCycle;
	
	 private long id;
	 private long humanId;
	 
	 /**卷类型 ：数量 **/
	 private Map<Integer,Integer> carIds = new HashMap<Integer,Integer>();
	 
	 /**物品ID：数量 **/
	 private Map<Integer,Integer> debris = new HashMap<Integer,Integer>();
	 
	 /**老虎机ID：摇了多少次 **/
	 private Map<Integer,Integer> slotspin = new HashMap<Integer,Integer>();
	 /**老虎机ID：出现的位置 **/
	 private Map<Integer,Integer> slotpoint = new HashMap<Integer,Integer>();
	 
	
	 
	 private long updateTime;
	 private long createTime;
	 
	 
	 private boolean inDb;
	 
	
	public HumanCollect() {
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


	

	public Map<Integer, Integer> getCarIds() {
		return carIds;
	}


	public void setCarIds(Map<Integer, Integer> carIds) {
		this.carIds = carIds;
	}


	public Map<Integer, Integer> getDebris() {
		return debris;
	}


	public void setDebris(Map<Integer, Integer> debris) {
		this.debris = debris;
	}

	
	

	public Map<Integer, Integer> getSlotspin() {
		return slotspin;
	}


	public void setSlotspin(Map<Integer, Integer> slotspin) {
		this.slotspin = slotspin;
	}


	public Map<Integer, Integer> getSlotpoint() {
		return slotpoint;
	}


	public void setSlotpoint(Map<Integer, Integer> slotpoint) {
		this.slotpoint = slotpoint;
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
		return "HumanCollect#"+this.id;
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
	public HumanCollectEntity toEntity() {
		
		HumanCollectEntity entity = new HumanCollectEntity();
		entity.setId(id);
		entity.setHumanId(humanId);
		entity.setCarIds(JSON.toJSONString(carIds));
		entity.setDebris(JSON.toJSONString(debris));
		entity.setSlotspin(JSON.toJSONString(slotspin));
		entity.setSlotpoint(JSON.toJSONString(slotpoint));
		entity.setUpdateTime(updateTime);
		entity.setCreateTime(createTime);
		
		return entity;
	}

	@Override
	public void fromEntity(HumanCollectEntity entity) {
		this.id = entity.getId();
		this.humanId = entity.getHumanId();
		String carIdsStr = entity.getCarIds();
		if(!StringUtils.isEmpty(carIdsStr)){
			carIds = JSON.parseObject(carIdsStr, HashMap.class);
		}
		String debrisStr = entity.getDebris();
		if(!StringUtils.isEmpty(debrisStr)){
			debris = JSON.parseObject(debrisStr, HashMap.class);
		}
		
		String slotspinStr = entity.getSlotspin();
		if(!StringUtils.isEmpty(slotspinStr)){
			slotspin = JSON.parseObject(slotspinStr, HashMap.class);
		}
		String slotpointStr = entity.getSlotpoint();
		if(!StringUtils.isEmpty(slotpointStr)){
			slotpoint = JSON.parseObject(slotpointStr, HashMap.class);
		}
		
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
