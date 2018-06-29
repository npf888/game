package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.HumanActivityEntity;
import com.gameserver.activity.Activity;
import com.gameserver.activity.ActivityRewardRule;
import com.gameserver.activity.CommonRule;
import com.gameserver.activity.HumanActivity;
import com.gameserver.activity.HumanActivityData;
import com.gameserver.activity.HumanActivityDataFactory;
import com.gameserver.activity.HumanRewardActivityDetailData;
import com.gameserver.activity.data.HumanActivitySmallData;
import com.gameserver.activity.data.HumanSimpleRewardInfoData;
import com.gameserver.activity.enums.ActivityConditionParamEnum;
import com.gameserver.activity.enums.ActivityTypeEnum;
import com.gameserver.activity.msg.GCHumanActivityRewardDataList;
import com.gameserver.activity.msg.GCHunamnProgress;
import com.gameserver.activity.msg.GCHunamnProgressSingle;
import com.gameserver.activity.msg.GCStillHaveActivityGold;
import com.gameserver.activity.msg.GCUpdateHumanActivityReward;
import com.gameserver.common.Globals;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.human.Human;



/**
 * 活动管理器
 * @author wayne
 *
 */
public class HumanActivityManager implements RoleDataHolder, InitializeRequired{

	private Logger logger = Loggers.activityLogger;
	private Human owner;
	private List<HumanActivity> humanActivityList = new ArrayList<HumanActivity>();
	private final int ACTIVITY_DATA_REMAIN = 7;
	
	/**
	 * 构造器
	 * 
	 * @param owner
	 */
	public HumanActivityManager(Human owner) {
		this.owner = owner;
		
	}
	
	public Human getOwner(){
		return this.owner;
	}
	
	public List<HumanActivity> getHumanActivityList(){
		return humanActivityList;
	}
	
	public void load(){
		List<HumanActivityEntity> humanActivityEntityList = Globals.getDaoService().getHumanActivityDao().getAllHumanActivities(owner.getPassportId());
		
		if(humanActivityEntityList!=null&&humanActivityEntityList.size()>0){
			for(HumanActivityEntity humanActivityEntity:humanActivityEntityList){
				Activity activity = Globals.getActivityService().getActivityById(humanActivityEntity.getActivityId());
				if(activity == null)
					continue;
				HumanActivity humanActivity=new HumanActivity();
				humanActivity.setOwner(owner);
				humanActivity.fromEntity(humanActivityEntity);
				humanActivityList.add(humanActivity);
			}
		}
		
	
	}
	
	@Override
	public void init() {
		
		checkAllActivity();
	}

	@Override
	public void checkAfterRoleLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkBeforeRoleEnter() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 用户登录时
	 * 看看是否还有 活动 的奖励未领取， 有的话通知用户，没有 拉倒
	 */
	public void buildStillActivityGold(){
		boolean existActivityStillGold = false;
		for(HumanActivity humanActivity: humanActivityList){
			List<HumanRewardActivityDetailData> humanRewardActivityDetailDataList = humanActivity.getHumanRewardActivityDetailDataList();
			for(HumanRewardActivityDetailData HumanRewardActivityDetailData :humanRewardActivityDetailDataList){
				if(HumanRewardActivityDetailData.getAllowDrawCount()>HumanRewardActivityDetailData.getHasDrawCount()){
					existActivityStillGold=true;
					break;
				}
			}
		}
		if(existActivityStillGold){
			GCStillHaveActivityGold gCStillHaveActivityGold = new GCStillHaveActivityGold();
			owner.getPlayer().sendMessage(gCStillHaveActivityGold);
		}
	}
	
	//检查活动
	public void checkAllActivity(){
	
		for(Activity activity : Globals.getActivityService().getActivityList()){
			checkActivity(activity);
		}
	}
	
	
	
	//检查单个活动
	public void checkActivity(Activity activity){
	
		//判断是否过期一星期
		if(activity.isEnd()){
			
			HumanActivity humanActivity = getActivityById(activity.getDbId());
			if(humanActivity==null){
				return;
			}
			if(ifShouldDelete(activity))
			{
				logger.info("玩家["+owner.getPassportId()+"]活动["+activity.getDbId()+"]过期 活动数据删除");
				humanActivityList.remove(humanActivity);
				humanActivity.onDelete();
			}
			return;
		}
		
		//判断是否激活
		if(activity.isActive()){
			HumanActivity humanActivity = getActivityById(activity.getDbId());
			if(humanActivity == null){
				logger.info("玩家["+owner.getPassportId()+"]活动["+activity.getDbId()+"]活动数据不存在 添加活动数据");
				
				long now = Globals.getTimeService().now();
				 humanActivity = new HumanActivity();
				 humanActivity.setDbId(Globals.getUUIDService().getNextUUID(now, UUIDType.HUMANACTIVITYID));
				 humanActivity.setActivityId(activity.getDbId());
				 humanActivity.setCharId(owner.getPassportId());
			
				 HumanActivityData tempHumanActivityData = HumanActivityDataFactory.createHumanActivityData(activity.getActivityType(),activity.getRuleList());
				 if(tempHumanActivityData == null)
				 {
					 logger.warn("活动["+activity.getDbId()+"] 活动数据类型不存在");
					 return;
				 }
				 
				 humanActivity.setHumanActivityData(tempHumanActivityData);
				 
				 for( ActivityRewardRule activityRewardRule: activity.getRuleList()){
					 humanActivity.getHumanRewardActivityDetailDataList().add(HumanRewardActivityDetailData.convertFromActivityRewardRule(activityRewardRule));
				 }
				 
				 humanActivity.setOwner(owner);
				 humanActivity.setInDb(false);
				 humanActivity.setCreateTime(now);
				 humanActivity.active();
				 humanActivity.setModified();
				 humanActivityList.add(humanActivity);
				 return;
			}
			
			//判断是否是隔天刷新
			if(activity.getDaily()==1){
				long now = Globals.getTimeService().now();
				if(TimeUtils.isSameDay(humanActivity.getUpdateTime(), now)){
					return;
				}
				logger.info("玩家["+owner.getPassportId()+"]活动["+activity.getDbId()+"]活动数据过期  刷新活动数据");
				
				 humanActivity.getHumanRewardActivityDetailDataList().clear();
				 for( ActivityRewardRule activityRewardRule: activity.getRuleList()){
					 humanActivity.getHumanRewardActivityDetailDataList().add(HumanRewardActivityDetailData.convertFromActivityRewardRule(activityRewardRule));
				 }
			}
			
		}	
	
	}

	public void checkActivityPlayCountList(Activity activity,HumanActivity humanActivity) {
		//判断是否是隔天刷新
		if(activity.getDaily()==1){
			long now = Globals.getTimeService().now();
			if(TimeUtils.isSameDay(humanActivity.getUpdateTime(), now)){
				return;
			}
			logger.info("玩家["+owner.getPassportId()+"]活动["+activity.getDbId()+"]活动数据过期  刷新活动数据");
			
			HumanActivityData humanActivit = humanActivity.getHumanActivityData();
			List<Long> playCountList = humanActivit.getPlayCountList();
			for(Long ss :playCountList){
				ss = 0l;
			}
		}
		
	}
	/**
	 * 是否删除
	 * @param activity true:已经删除
	 */
	private boolean ifShouldDelete(Activity activity){
		long now = Globals.getTimeService().now();
	
		long expire = now-activity.getEndTime();
		if(expire>ACTIVITY_DATA_REMAIN*TimeUtils.DAY)
		{
			return true;
		}
		return false;
	}
	

	/**
	 * 活动奖励领取状态数据
	 * @return
	 */
	public GCHumanActivityRewardDataList buildGCHumanActivityRewardDataList(){
		List<HumanActivity> pubHumanActivityList = Globals.getActivityService().getPublicActivityList();
		GCHumanActivityRewardDataList gcHumanActivityRewardDataList = new GCHumanActivityRewardDataList();
		HumanSimpleRewardInfoData[] humanSimpleActivityRewardDataArr = new HumanSimpleRewardInfoData[humanActivityList.size()+pubHumanActivityList.size()];
		for(int i=0;i<humanActivityList.size();i++){
			HumanSimpleRewardInfoData tempHumanSimpleRewardActivityData = new HumanSimpleRewardInfoData();
			tempHumanSimpleRewardActivityData.setStateListist(Globals.getActivityService().getDrawTypeForActivityNew(humanActivityList.get(i)));
			tempHumanSimpleRewardActivityData.setActivityId(humanActivityList.get(i).getActivityId());
			humanSimpleActivityRewardDataArr[i] = tempHumanSimpleRewardActivityData;
		}
		for(int i=0;i<pubHumanActivityList.size();i++){
			/**
			 * 如果是公共活动 需要判断下 这个活动到底 可不可以领取
			 */
			Activity activity = Globals.getActivityService().getActivityById(pubHumanActivityList.get(i).getActivityId());
			if(activity == null){
				continue;
			}
			HumanActivity humanActivity=Globals.getActivityService().checkPubActivity(activity,owner);
			if(humanActivity == null){
				continue;
			}
			if(CommonRule.getInstance().ifFullServerGrandRewardStatusChanged(owner, pubHumanActivityList.get(i),humanActivity,ActivityConditionParamEnum.Condition25)){
				humanActivity.setOwner(owner);
				humanActivity.setModified();
			}
			HumanSimpleRewardInfoData tempHumanSimpleRewardActivityData = new HumanSimpleRewardInfoData();
			tempHumanSimpleRewardActivityData.setStateListist(Globals.getActivityService().getDrawTypeForActivityNew(humanActivity));
			tempHumanSimpleRewardActivityData.setActivityId(pubHumanActivityList.get(i).getActivityId());
			humanSimpleActivityRewardDataArr[humanActivityList.size()+i] = tempHumanSimpleRewardActivityData;
			
		}
		gcHumanActivityRewardDataList.setHumanSimpleRewardInfoData(humanSimpleActivityRewardDataArr);
		return gcHumanActivityRewardDataList;
	}
	
	/**
	 * 每个活动的进度
	 * @return
	 */
	public GCHunamnProgress buildGCHunamnProgress(){
		GCHunamnProgress progress = new GCHunamnProgress();
		//大家 共有的活动
		List<HumanActivity> pubHumanActivityList = Globals.getActivityService().getPublicActivityList();
		//用户专属的活动
		HumanActivitySmallData[] activitySmallData = new HumanActivitySmallData[humanActivityList.size()+pubHumanActivityList.size()];
		for(int i=0;i<humanActivityList.size();i++){
			HumanActivity humanActivity = humanActivityList.get(i);
			HumanActivitySmallData smallData = new HumanActivitySmallData();
			
			List<Long> list = humanActivity.getHumanActivityData().getPlayCountList();
			 int[] num = new int[list.size()];
			 for(int index = 0;index < list.size();index++){
				 num[index] = list.get(index).intValue();
			 }
			smallData.setSmallValue(num);
			smallData.setActivityId(humanActivity.getActivityId());
			activitySmallData[i] = smallData;
		}
		//大家 共有的活动
		for(int i=0;i<pubHumanActivityList.size();i++){
			HumanActivity commonActivity = pubHumanActivityList.get(i);
			HumanActivitySmallData smallData = new HumanActivitySmallData();
			
			List<Long> list = commonActivity.getHumanActivityData().getPlayCountList();
			 int[] num = new int[list.size()];
			 for(int index = 0;index < list.size();index++){
				 num[index] = list.get(index).intValue();
			 }
			smallData.setSmallValue(num);
			smallData.setActivityId(commonActivity.getActivityId());
			activitySmallData[humanActivityList.size()+i] = smallData;
		}
		progress.setHumanActivitySmallData(activitySmallData);
		return progress;
	}
	/**
	 * 每个活动的进度
	 * @return
	 */
	public GCHunamnProgressSingle buildGCHunamnProgressSingle(Activity activity){
		GCHunamnProgressSingle progress = new GCHunamnProgressSingle();
		HumanActivity humanActivity = this.getActivityById(activity.getDbId());
		
		HumanActivitySmallData smallData = new HumanActivitySmallData();
		smallData.setActivityId(activity.getDbId());
		
		List<Long> list = humanActivity.getHumanActivityData().getPlayCountList();
		 int[] num = new int[list.size()];
		 for(int index = 0;index < list.size();index++){
			 num[index] = list.get(index).intValue();
		 }
		smallData.setSmallValue(num);
		smallData.setSmallValue(num);
		
		progress.setHumanActivitySmallData(smallData);
		return progress;
	}
	
	
	/**
	 * 活动奖励数据
	 * @return
	 */
	public GCUpdateHumanActivityReward buildGCHumanActivityRewardData(Activity activity){
		GCUpdateHumanActivityReward gcUpdateHumanActivityReward = new GCUpdateHumanActivityReward();
		HumanActivity humanActivity = this.getActivityById(activity.getDbId());
		HumanSimpleRewardInfoData info = new HumanSimpleRewardInfoData();
		info.setStateListist(Globals.getActivityService().getDrawTypeForActivityNew(humanActivity));
		info.setActivityId(activity.getDbId());
		gcUpdateHumanActivityReward.setHumanSimpleRewardInfoData(info);
		return gcUpdateHumanActivityReward;
	}
	/**
	 * 公共 活动奖励数据
	 * @return
	 */
	public GCUpdateHumanActivityReward pubBuildGCHumanActivityRewardData(Activity activity){
		GCUpdateHumanActivityReward gcUpdateHumanActivityReward = new GCUpdateHumanActivityReward();
		HumanActivity humanActivity = Globals.getActivityService().getPubActivityByActivityId(activity.getDbId());
		HumanSimpleRewardInfoData info = new HumanSimpleRewardInfoData();
		info.setStateListist(Globals.getActivityService().getDrawTypeForActivityNew(humanActivity));
		info.setActivityId(activity.getDbId());
		gcUpdateHumanActivityReward.setHumanSimpleRewardInfoData(info);
		return gcUpdateHumanActivityReward;
	}
	
	
	
	/**
	 * 活动数据
	 * @param activityId
	 * @return
	 */
	public HumanActivity getActivityById(long activityId){
		for(HumanActivity humanActivity : humanActivityList){
			if(humanActivity.getActivityId() == activityId)
				return humanActivity;
		}
		return null;
	}
	public HumanActivity getActivityById(long activityId,Activity activity){
		for(HumanActivity humanActivity : humanActivityList){
			if(humanActivity.getActivityId() == activityId)
				return humanActivity;
		}
		if(activity.getActivityType() == ActivityTypeEnum.ActivityType17){
			long now = Globals.getTimeService().now();
			HumanActivity humanActivity = new HumanActivity();
			humanActivity.setDbId(Globals.getUUIDService().getNextUUID(now, UUIDType.HUMANACTIVITYID));
			humanActivity.setActivityId(activity.getDbId());
		
			 HumanActivityData tempHumanActivityData = HumanActivityDataFactory.createHumanActivityData(activity.getActivityType(),activity.getRuleList());
			 if(tempHumanActivityData == null)
			 {
				 logger.warn("活动["+activity.getDbId()+"] 活动数据类型不存在");
				 return null;
			 }
			 
			 humanActivity.setHumanActivityData(tempHumanActivityData);
			 
			 for( ActivityRewardRule activityRewardRule: activity.getRuleList()){
				 humanActivity.getHumanRewardActivityDetailDataList().add(HumanRewardActivityDetailData.convertFromActivityRewardRule(activityRewardRule));
			 }
			 
			 humanActivity.setOwner(owner);
			 humanActivity.setInDb(false);
			 humanActivity.setCreateTime(now);
			 humanActivity.active();
			 humanActivity.setModified();
			 humanActivityList.add(humanActivity);
			 return humanActivity;
		}
		return null;
	}
	
	/**
	 * 获取可领取的奖励
	 * @param activityId
	 * @return
	 */
	public int getAvailableRewardIndex(long activityId){
		HumanActivity humanActivity = getActivityById(activityId);
		if(humanActivity == null)
			return -1;
		
		List<HumanRewardActivityDetailData>  tempHumanRewardActivityDetailDataList = humanActivity.getHumanRewardActivityDetailDataList();
	
		for(int i =0;i< tempHumanRewardActivityDetailDataList.size();i++){
			
			HumanRewardActivityDetailData tempHumanRewardActivityDetailData = tempHumanRewardActivityDetailDataList.get(i);
			//判断是否可以领取 为0 不允许领取
			if(tempHumanRewardActivityDetailData.getAllowDrawCount() == 0){
				continue;
			}
			
			//判断是否还有可以领取的 相当表示次数已经消耗完了
			if(tempHumanRewardActivityDetailData.getHasDrawCount() == tempHumanRewardActivityDetailData.getAllowDrawCount()){
				continue;
			}
			return i;
			
		}
		
		return -1;
	}
	
	
	/**
	 * 获取可领取的奖励
	 * @param activityId
	 * @param activity 
	 * @return
	 */
	public int getAvailableRewardIndexNew(long activityId,int indexId, Activity activity){
		HumanActivity humanActivity = getActivityById(activityId,activity);
		if(humanActivity == null){
			return 0;
		}
			
		List<HumanRewardActivityDetailData>  tempHumanRewardActivityDetailDataList = humanActivity.getHumanRewardActivityDetailDataList();
		if(indexId > (tempHumanRewardActivityDetailDataList.size()-1)){
			return 0;
		}
		HumanRewardActivityDetailData tempHumanRewardActivityDetailData = tempHumanRewardActivityDetailDataList.get(indexId);
		//判断是否可以领取 为0 不允许领取
		if(tempHumanRewardActivityDetailData.getAllowDrawCount() == 0){
			return 0;
		}
		
		//判断是否还有可以领取的 相当表示次数已经消耗完了
		if(tempHumanRewardActivityDetailData.getHasDrawCount() == tempHumanRewardActivityDetailData.getAllowDrawCount()){
			return 0;
		}
		return 1;
	}
	
	
	
	/**
	 * 领取奖励
	 * @param activityId
	 * @param ruleIndex
	 */
	public void getReward(long activityId,int ruleIndex){
		HumanActivity humanActivity = getActivityById(activityId);
		HumanRewardActivityDetailData humanRewardActivityDetailData=humanActivity.getHumanRewardActivityDetailDataList().get(ruleIndex);
		humanRewardActivityDetailData.setHasDrawCount(humanRewardActivityDetailData.getHasDrawCount()+1);
		humanActivity.setModified();
		
	}

	
	
}
