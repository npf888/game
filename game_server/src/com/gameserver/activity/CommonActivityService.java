package com.gameserver.activity;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.dao.CommonActivityDao;
import com.db.model.CommonActivityEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

/**
 * 没有使用
 * 全服奖励的service
 * @author JavaServer
 *
 */
public class CommonActivityService implements InitializeRequired{
	
	private Logger logger = Loggers.activityLogger;
	List<CommonActivity> allCommonActivityList = new ArrayList<CommonActivity>();
	
	@Override
	public void init() {
		CommonActivityDao commonActivityDao = Globals.getDaoService().getCommonActivityDao();
		List<CommonActivityEntity> commonActivityEntityList  = commonActivityDao.getCommonActivity();
		for(CommonActivityEntity entity:commonActivityEntityList){
			CommonActivity  commonActivity = new CommonActivity();
			commonActivity.fromEntity(entity);
			allCommonActivityList.add(commonActivity);
		}
		
	}
	
	
	public CommonActivity getCommonActivityByActivityId(long activityId){
		for(CommonActivity entity :allCommonActivityList){
			if(entity.getActivityId() == activityId){
				return entity;
			}
		}
		return null;
	}
	

	
	//检查单个活动
		public void checkActivity(Activity activity,Human owner){
		
			//判断是否过期一星期
			if(activity.isEnd()){
				
				CommonActivity commonActivity = getCommonActivityByActivityId(activity.getDbId());
				if(commonActivity==null){
					return;
				}
				if(ifShouldDelete(activity))
				{
					logger.info("玩家["+owner.getPassportId()+"]活动["+activity.getDbId()+"]过期 活动数据删除");
					allCommonActivityList.remove(commonActivity);
					commonActivity.onDelete();
				}
				return;
			}
			
			//判断是否激活
			if(activity.isActive()){
				CommonActivity commonActivity =  getCommonActivityByActivityId(activity.getDbId());
				if(commonActivity == null){
					logger.info("玩家["+owner.getPassportId()+"]活动["+activity.getDbId()+"]活动数据不存在 添加活动数据");
					
					long now = Globals.getTimeService().now();
					commonActivity = new CommonActivity();
					 commonActivity.setDbId(Globals.getUUIDService().getNextUUID(now, UUIDType.HUMANACTIVITYID));
					 commonActivity.setActivityId(activity.getDbId());
				
					 HumanActivityData tempHumanActivityData = HumanActivityDataFactory.createHumanActivityData(activity.getActivityType(),activity.getRuleList());
					 if(tempHumanActivityData == null)
					 {
						 logger.warn("活动["+activity.getDbId()+"] 活动数据类型不存在");
						 return;
					 }
					 
					 commonActivity.setHumanActivityData(tempHumanActivityData);
					 
					 for( ActivityRewardRule activityRewardRule: activity.getRuleList()){
						 commonActivity.getHumanRewardActivityDetailDataList().add(HumanRewardActivityDetailData.convertFromActivityRewardRule(activityRewardRule));
					 }
					 
					 commonActivity.setOwner(owner);
					 commonActivity.setInDb(false);
					 commonActivity.setCreateTime(now);
					 commonActivity.active();
					 commonActivity.setModified();
					 allCommonActivityList.add(commonActivity);
					 return;
				}
				
				//判断是否是隔天刷新
				if(activity.getDaily()==1){
					long now = Globals.getTimeService().now();
					if(TimeUtils.isSameDay(commonActivity.getUpdateTime(), now)){
						return;
					}
					logger.info("玩家["+owner.getPassportId()+"]活动["+activity.getDbId()+"]活动数据过期  刷新活动数据");
					
					commonActivity.getHumanRewardActivityDetailDataList().clear();
					 for( ActivityRewardRule activityRewardRule: activity.getRuleList()){
						 commonActivity.getHumanRewardActivityDetailDataList().add(HumanRewardActivityDetailData.convertFromActivityRewardRule(activityRewardRule));
					 }
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
			if(expire>7*TimeUtils.DAY)
			{
				return true;
			}
			return false;
		}


		public List<CommonActivity> getAllCommonActivityList() {
			return allCommonActivityList;
		}


		public void setAllCommonActivityList(List<CommonActivity> allCommonActivityList) {
			this.allCommonActivityList = allCommonActivityList;
		}
		
		
		
}
