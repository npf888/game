package com.gameserver.activity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.CommonActivityEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
/**
 * 没有使用
 * @author JavaServer
 *
 */
public class CommonActivity implements PersistanceObject<Long, CommonActivityEntity>{
	private long id;
	private long activityId;
	private String conditions;
	private HumanActivityData humanActivityData;
	private List<HumanRewardActivityDetailData> humanRewardActivityDetailDataList = new ArrayList<HumanRewardActivityDetailData>();
	private long updateTime;
	private long createTime;
	/** 是否已经在数据库中 */
	private boolean inDb;
	private final LifeCycle lifeCycle;
	private Human owner;
	
	public CommonActivity(){
		this.lifeCycle = new LifeCycleImpl(this);
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
		return "commonActivity#"+this.id;
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
	public CommonActivityEntity toEntity() {
		CommonActivityEntity commonActivityEntity = new CommonActivityEntity();
			commonActivityEntity.setId(this.getDbId());
			commonActivityEntity.setActivityId(this.getActivityId());
			commonActivityEntity.setActivityDataPack(JSON.toJSONString(this.getHumanActivityData()));
			commonActivityEntity.setRewardActivityDataPack(JSON.toJSONString(this.getHumanRewardActivityDetailDataList()));
			commonActivityEntity.setUpdateTime(this.getUpdateTime());
			commonActivityEntity.setCreateTime(this.getCreateTime());
			commonActivityEntity.setConditions(this.conditions);
			return commonActivityEntity;
	}

	@Override
	public void fromEntity(CommonActivityEntity entity) {
			this.setDbId(entity.getId());
			this.setActivityId(entity.getActivityId());
			Activity activity = Globals.getActivityService().getActivityById(entity.getActivityId());
			this.setHumanActivityData(JSON.parseObject(entity.getActivityDataPack(), activity.getActivityType().getClazz()));
			this.setHumanRewardActivityDetailDataList(JSON.parseArray(entity.getRewardActivityDataPack(), HumanRewardActivityDetailData.class));
			this.setUpdateTime(entity.getUpdateTime());
			this.setCreateTime(entity.getCreateTime());
			this.setConditions(entity.getConditions());
			this.setInDb(true);
			this.active();
		
	}

	@Override
	public LifeCycle getLifeCycle() {
		return this.lifeCycle;
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
				this.updateTime = now ;
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}
	
	public void onDelete()
	{
		this.lifeCycle.destroy();
		owner.getPlayer().getDataUpdater().addDelete(this.getLifeCycle());
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}


	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	

	public HumanActivityData getHumanActivityData() {
		return humanActivityData;
	}

	public void setHumanActivityData(HumanActivityData humanActivityData) {
		this.humanActivityData = humanActivityData;
	}

	public List<HumanRewardActivityDetailData> getHumanRewardActivityDetailDataList() {
		return humanRewardActivityDetailDataList;
	}

	public void setHumanRewardActivityDetailDataList(
			List<HumanRewardActivityDetailData> humanRewardActivityDetailDataList) {
		this.humanRewardActivityDetailDataList = humanRewardActivityDetailDataList;
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
	public long getCharId() {
		return 0;
	}

	public Human getOwner() {
		return owner;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
	}

	
	
	
	
	
	
	

}
