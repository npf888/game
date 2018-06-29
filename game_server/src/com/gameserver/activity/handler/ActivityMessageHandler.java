package com.gameserver.activity.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.constants.Loggers;
import com.gameserver.activity.Activity;
import com.gameserver.activity.ActivityRewardRule;
import com.gameserver.activity.HumanActivity;
import com.gameserver.activity.HumanActivityData;
import com.gameserver.activity.data.ActivityData;
import com.gameserver.activity.enums.ActivityTypeEnum;
import com.gameserver.activity.enums.VipPoint;
import com.gameserver.activity.msg.CGActivityList;
import com.gameserver.activity.msg.CGActivityProgress;
import com.gameserver.activity.msg.CGFunction;
import com.gameserver.activity.msg.CGGetActivityReward;
import com.gameserver.activity.msg.CGMonthWeekLeftTime;
import com.gameserver.activity.msg.CGStillHaveActivityGold;
import com.gameserver.activity.msg.GCActivityList;
import com.gameserver.activity.msg.GCFunctionLeftTime;
import com.gameserver.activity.msg.GCGetActivityReward;
import com.gameserver.activity.msg.GCMonthWeekLeftTime;
import com.gameserver.common.CommonLogic;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanActivityManager;
import com.gameserver.player.Player;
import com.gameserver.recharge.enums.CategoryEnum;

/**
 * 活动消息处理器
 * @author wayne
 *
 */
public class ActivityMessageHandler {
	
	private Logger logger = Loggers.activityLogger;
	/**
	 * 领取奖励
	 * @param player
	 * @param cgGetActivityReward
	 */
	public void handleGetActivityReward(Player player,
			CGGetActivityReward cgGetActivityReward) {
		
		Human human = player.getHuman();
		if(human == null)
			return;
		
		
		
		
		
		Activity activity = Globals.getActivityService().getActivityById(cgGetActivityReward.getActivityId());
		//活动不存在
		if(activity == null){
			logger.warn("玩家["+human.getPassportId()+"] 领取活动["+cgGetActivityReward.getActivityId()+"] 活动不存在");
			return;
		}
		
		//判断活动
		HumanActivityManager humanActivityManager = human.getHumanActivityManager();
		
		long activityid = cgGetActivityReward.getActivityId();
		
		int index = cgGetActivityReward.getIndexId();
	
		
		//判断哪一个可以领取
		int rresult= humanActivityManager.getAvailableRewardIndexNew(activityid,index,activity);
		
		//没有可领取的活动奖励
		if(rresult == 0){
			logger.warn("玩家["+human.getPassportId()+"] 领取活动["+cgGetActivityReward.getActivityId()+"] 没有可以领取的活动奖励");
		}else{
			ActivityRewardRule activityRewardRule =  activity.getActivityRewardRuleByIndex(index);
			
			//领取
			humanActivityManager.getReward(cgGetActivityReward.getActivityId(),index);
			
			
			/**
			 * 全服的赠送奖励  和 各个 赠送奖励不同，
			 * 全服的要根据vippoint来赠送 奖励
			 * 个人的不用
			 */
			if(activity.getActivityType() == ActivityTypeEnum.ActivityType17){
				List<RandRewardData> randRewardDataList = activityRewardRule.getRewardList();
				
				for(RandRewardData reward:randRewardDataList){
					if(reward.getRewardId() == VipPoint.POINT.value()){
						if(reward.getVippoint() == human.getHumanVipNewManager().getVipLv()){
							human.getHumanVipNewManager().addOnlyThePassPoint(reward.getRewardCount());
						}
					}
				}
				CommonLogic.getInstance().giveAllRandReward(human, randRewardDataList, LogReasons.GoldLogReason.ACTIVITY, LogReasons.DiamondLogReason.ACTIVITY, LogReasons.CharmLogReason.ACTIVITY,LogReasons.ItemLogReason.ACTIVITY_GIVEN, false);
			}else{
				List<RandRewardData> randRewardDataList = activityRewardRule.getRewardList();
				
				for(RandRewardData reward:randRewardDataList){
					if(reward.getRewardId() == VipPoint.POINT.value()){
						human.getHumanVipNewManager().addOnlyThePassPoint(reward.getRewardCount());
					}
				}
				CommonLogic.getInstance().giveRandReward(human, randRewardDataList, LogReasons.GoldLogReason.ACTIVITY, LogReasons.DiamondLogReason.ACTIVITY, LogReasons.CharmLogReason.ACTIVITY,LogReasons.ItemLogReason.ACTIVITY_GIVEN, false);
			}
			
			player.sendMessage(human.getHumanActivityManager().buildGCHumanActivityRewardData(activity));
			
		}
		GCGetActivityReward reward = new GCGetActivityReward();
		reward.setActivityId(activityid);
		reward.setIndexId(index);
		reward.setResult(rresult);
		player.sendMessage(reward);

	}
	
	

	public void handleActivityProgress(Player player, CGActivityProgress cgActivityProgress) {
		player.sendMessage(player.getHuman().getHumanActivityManager().buildGCHunamnProgress());
	}



	public void handleActivityList(Player player, CGActivityList cgActivityList) {
		List<Activity> activityList = Globals.getActivityService().getActivityList();
		long now = Globals.getTimeService().now();
		List<ActivityData> tempActivityDataList = new ArrayList<ActivityData>();
		for(int i=0;i<activityList.size();i++){
			Activity tempActivity = activityList.get(i);
			if(tempActivity.getStartTime() >now || tempActivity.getEndTime()<now){
				continue;
			}
			
			/**
			 * 处理当前用户的累计金币
			 */
			ActivityData activityData = ActivityData.convertFromActivity(tempActivity);
			HumanActivity humanActivity = player.getHuman().getHumanActivityManager().getActivityById(tempActivity.getDbId());
			HumanActivityData humanActivityData = humanActivity.getHumanActivityData();
			List<Long> playCountList =  humanActivityData.getPlayCountList();
//			if(tempActivity.getActivityType() == ActivityTypeEnum.ActivityType15 
//					|| tempActivity.getActivityType() == ActivityTypeEnum.ActivityType18
//					|| tempActivity.getActivityType() == ActivityTypeEnum.ActivityType17){
			
			//美元 要除以100
			if(tempActivity.getActivityType() == ActivityTypeEnum.ActivityType15 ){
				
				if(playCountList != null && playCountList.size()>0){
					double mm = playCountList.get(0).doubleValue()/100;
					activityData.setFullValue(mm+"");
				}
			}else{
				if(playCountList != null && playCountList.size()>0){
					activityData.setFullValue(playCountList.get(0)+"");
				}
			}
//			}
			tempActivityDataList.add(activityData);
		}
		GCActivityList gcActivityList = new GCActivityList();
		gcActivityList.setActivityDataList(tempActivityDataList.toArray(new ActivityData[tempActivityDataList.size()]));
		player.sendMessage(gcActivityList);
		
	}



	public void handleMonthWeekLeftTime(Player player,
			CGMonthWeekLeftTime cgMonthWeekLeftTime) {
		GCMonthWeekLeftTime  gCMonthWeekLeftTime = new GCMonthWeekLeftTime();
		Map<Integer,Long> leftTimeMap = player.getHuman().getHumanRegularTimeManager().getLeftTime();
		if(leftTimeMap.containsKey(0)){
			gCMonthWeekLeftTime.setWeekLeftTime(leftTimeMap.get(0));
		}else{
			gCMonthWeekLeftTime.setWeekLeftTime(0);
		}
		if(leftTimeMap.containsKey(1)){
			gCMonthWeekLeftTime.setMonthLeftTime(leftTimeMap.get(1));
		}else{
			gCMonthWeekLeftTime.setMonthLeftTime(0);
		}
		player.sendMessage(gCMonthWeekLeftTime);
	}



	public void handleStillHaveActivityGold(Player player,
			CGStillHaveActivityGold cgStillHaveActivityGold) {
		player.getHuman().getHumanActivityManager().buildStillActivityGold();
		
	}



	public void handleFunction(Player player, CGFunction cgFunction) {
		
		Globals.getFunctionService().getFunction(player);
	
	}

}
