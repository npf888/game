package com.gameserver.achievement;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.HumanAchievementEntity;
import com.gameserver.achievement.pojo.CompleteState;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
/**
 * 郭君伟
 * @author 郭君伟
 *
 */
public class HumanAchievement implements PersistanceObject<Long, HumanAchievementEntity> {
	
	
	private Human owner;
	
	private final LifeCycle lifeCycle;
	
	 private boolean inDb;
	
	private long id;
	
	private long charId;
	
     /**key:成就ID value:完成状态 **/
	private Map<Integer,CompleteState> achValueMap = new HashMap<Integer,CompleteState>();
	
	/**key:大类型 value( key: 小类型 value:当前累计值)  两个参数的特殊对待**/
	private Map<Integer,Map<Integer,String>> achDateMap = new HashMap<Integer,Map<Integer,String>>();
	
	private long slotRotate;
	private long slotWin;
	private long slotSingleWin;
	private long slotWinNum;
	private long updateTime;
	private long createTime;
	
	public HumanAchievement() {
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

	
	
	public Human getOwner() {
		return owner;
	}


	public void setOwner(Human owner) {
		this.owner = owner;
	}


	public Map<Integer, CompleteState> getAchValueMap() {
		return achValueMap;
	}


	public void setAchValueMap(Map<Integer, CompleteState> achValueMap) {
		this.achValueMap = achValueMap;
	}


	public Map<Integer, Map<Integer, String>> getAchDateMap() {
		return achDateMap;
	}


	public void setAchDateMap(Map<Integer, Map<Integer, String>> achDateMap) {
		this.achDateMap = achDateMap;
	}


	public long getSlotRotate() {
		return slotRotate;
	}


	public void setSlotRotate(long slotRotate) {
		this.slotRotate = slotRotate;
	}


	public long getSlotWin() {
		return slotWin;
	}


	public void setSlotWin(long slotWin) {
		this.slotWin = slotWin;
	}


	public long getSlotSingleWin() {
		return slotSingleWin;
	}


	public void setSlotSingleWin(long slotSingleWin) {
		this.slotSingleWin = slotSingleWin;
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


	public void setCharId(long charId) {
		this.charId = charId;
	}


	public long getSlotWinNum() {
		return slotWinNum;
	}


	public void setSlotWinNum(long slotWinNum) {
		this.slotWinNum = slotWinNum;
	}


	@Override
	public String getGUID() {
		return "HumanAchievement"+this.id;
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
		return charId;
	}

	@Override
	public HumanAchievementEntity toEntity() {
		HumanAchievementEntity entity = new HumanAchievementEntity();
		entity.setId(id);
		entity.setCharId(charId);
		entity.setAchValue(JSON.toJSONString(achValueMap));
		entity.setAchDate(JSON.toJSONString(achDateMap));
		entity.setSlotRotate(slotRotate);
		entity.setSlotWin(slotWin);
		entity.setSlotSingleWin(slotSingleWin);
		entity.setUpdateTime(updateTime);
		entity.setCreateTime(createTime);
		return entity;
	}

	@Override
	public void fromEntity(HumanAchievementEntity entity) {
	     this.id = entity.getId();
	     this.charId = entity.getCharId();
	     
	     this.achValueMap = JSON.parseObject(entity.getAchValue(), new TypeReference<Map<Integer,CompleteState>>() {});
	     
	     this.achDateMap = JSON.parseObject(entity.getAchDate(),new TypeReference<Map<Integer, Map<Integer, String>>>() {});
	     this.slotRotate = entity.getSlotRotate();
	     this.slotSingleWin = entity.getSlotSingleWin();
	     this.slotWin = entity.getSlotWin();
	     this.updateTime = entity.getUpdateTime();
	     this.createTime = entity.getCreateTime();
	     this.slotWinNum = entity.getSlotWinNum();
	     this.setInDb(true);
		 this.active();
	}
	
	public void fromEntityCope(HumanAchievementEntity entity) {
		this.id = entity.getId();
		this.charId = entity.getCharId();
		
		this.achValueMap = JSON.parseObject(entity.getAchValue(), new TypeReference<Map<Integer,CompleteState>>() {});
		
		this.achDateMap = JSON.parseObject(entity.getAchDate(),new TypeReference<Map<Integer, Map<Integer, String>>>() {});
		this.slotRotate = entity.getSlotRotate();
		this.slotSingleWin = entity.getSlotSingleWin();
		this.slotWin = entity.getSlotWin();
		this.updateTime = entity.getUpdateTime();
		this.createTime = entity.getCreateTime();
		this.slotWinNum = entity.getSlotWinNum();
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
