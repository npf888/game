package com.gameserver.activity;


import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.db.model.HumanActivityEntity;
import com.gameserver.activity.pojo.GrandDayVO;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

/**
 * 活动个人动态数据
 * @author wayne
 *
 */
public class HumanActivity implements PersistanceObject<Long, HumanActivityEntity> {

	/** 生命期 */
	private Human owner;
	private final LifeCycle lifeCycle;
	private long id;
	private long charId;
	private long activityId;
	private String friendId;
	private String conditions;
	private HumanActivityData humanActivityData;
	private List<HumanRewardActivityDetailData> humanRewardActivityDetailDataList = new ArrayList<HumanRewardActivityDetailData>();
	private long updateTime;
	private long createTime;
	/** 是否已经在数据库中 */
	private boolean inDb;
	
	
	public HumanActivity(){
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
		// TODO Auto-generated method stub
		this.id =id;
	}

	@Override
	public Long getDbId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getGUID() {
		// TODO Auto-generated method stub
		return "humanactivity#"+this.id;
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

	@Override
	public long getCharId() {
		// TODO Auto-generated method stub
		return charId;
	}
	
	public void setCharId(long charId){
		this.charId= charId;
	}
	


	


	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
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

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	@Override
	public HumanActivityEntity toEntity() {
		// TODO Auto-generated method stub
		HumanActivityEntity humanActivityEntity = new HumanActivityEntity();
		humanActivityEntity.setCharId(this.getCharId());
		humanActivityEntity.setId(this.getDbId());
		humanActivityEntity.setActivityId(this.getActivityId());
		humanActivityEntity.setActivityDataPack(JSON.toJSONString(this.getHumanActivityData()));
		humanActivityEntity.setRewardActivityDataPack(JSON.toJSONString(this.getHumanRewardActivityDetailDataList()));
		humanActivityEntity.setUpdateTime(this.getUpdateTime());
		humanActivityEntity.setCreateTime(this.getCreateTime());
		humanActivityEntity.setFriendId(this.getFriendId());
		humanActivityEntity.setConditions(this.conditions);
		return humanActivityEntity;
	}

	@Override
	public void fromEntity(HumanActivityEntity entity) {
		// TODO Auto-generated method stub
	
		this.setCharId(entity.getCharId());
		this.setDbId(entity.getId());
		this.setActivityId(entity.getActivityId());
		Activity activity = Globals.getActivityService().getActivityById(entity.getActivityId());
		this.setHumanActivityData(JSON.parseObject(entity.getActivityDataPack(), activity.getActivityType().getClazz()));
		this.setHumanRewardActivityDetailDataList(JSON.parseArray(entity.getRewardActivityDataPack(), HumanRewardActivityDetailData.class));
		this.setUpdateTime(entity.getUpdateTime());
		this.setCreateTime(entity.getCreateTime());
		this.setFriendId(entity.getFriendId());
		this.setConditions(entity.getConditions());
		this.setInDb(true);
		this.active();
	}

	@Override
	public LifeCycle getLifeCycle() {
		// TODO Auto-generated method stub
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
				this.updateTime = now ;
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}
	
	/**
	 * 物品被删除时调用,恢复默认值,并触发删除机制
	 * 
	 */
	public void onDelete()
	{
		this.lifeCycle.destroy();
		this.getOwner().getPlayer().getDataUpdater().addDelete(this.getLifeCycle());
	}

	
	private  List<GrandDayVO> getConditionsGrandList(){
		
		List<GrandDayVO> grandDayVOS = JSONObject.parseArray(this.conditions, GrandDayVO.class);
		return grandDayVOS;
	}
	/**
	 * 连续累计充值的条件
	 * @return
	 */
	public GrandDayVO getConditionsGrand(){
		
		List<GrandDayVO> grandDayVOS = JSONObject.parseArray(this.conditions, GrandDayVO.class);
		if(grandDayVOS != null && grandDayVOS.size()>0){
			return grandDayVOS.get(grandDayVOS.size()-1);
		}
		return null;
	}
	public void setConditionsGrand(String now){
		 List<GrandDayVO> grandDayVOList= getConditionsGrandList();
		 if(grandDayVOList != null && grandDayVOList.size() >0){
			 GrandDayVO grandDayVO = new GrandDayVO();
			 grandDayVO.setDate(now);
			 grandDayVOList.add(grandDayVO);
		 }else{
			 grandDayVOList = new ArrayList<GrandDayVO>();
			 GrandDayVO grandDayVO = new GrandDayVO();
			 grandDayVO.setDate(now);
			 grandDayVOList.add(grandDayVO);
		 }
		this.conditions=JSONArray.toJSONString(grandDayVOList);
		
	}
	public void setConditionsOne(String now){
		List<GrandDayVO> grandDayVOList = new ArrayList<GrandDayVO>();
		GrandDayVO grandDayVO = new GrandDayVO();
		grandDayVO.setDate(now);
		grandDayVOList.add(grandDayVO);
		this.conditions=JSONArray.toJSONString(grandDayVOList);
	}
}
