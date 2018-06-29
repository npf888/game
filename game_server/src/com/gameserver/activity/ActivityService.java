package com.gameserver.activity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.common.AfterInitializeRequired;
import com.common.DestroyRequired;
import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.ActivityEntity;
import com.db.model.HumanActivityEntity;
import com.gameserver.activity.data.ActivityData;
import com.gameserver.activity.enums.ActivityConditionParamEnum;
import com.gameserver.activity.enums.ActivityTypeEnum;
import com.gameserver.activity.enums.DrawType;
import com.gameserver.activity.msg.GCActivityList;
import com.gameserver.activity.msg.GCHunamnProgressSingle;
import com.gameserver.activity.msg.GCStillHaveActivityGold;
import com.gameserver.activity.msg.GCUpdateActivity;
import com.gameserver.activity.msg.GCUpdateHumanActivityReward;
import com.gameserver.activity.pojo.GrandDayVO;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.common.unit.GameUnitList;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanActivityManager;
import com.gameserver.player.Player;
import com.gameserver.recharge.interfaces.IRechargeListener;
import com.gameserver.relation.Friend;
import com.gameserver.texas.interfaces.ITexasListener;
import com.gameserver.texas.template.TexasRoomTemplate;

/**
 * 活动服务
 * @author wayne
 *
 */
public class ActivityService implements InitializeRequired,DestroyRequired,AfterInitializeRequired,ITexasListener,IRechargeListener{

	private Logger logger = Loggers.activityLogger;
	
	private List<Activity> activityList = new ArrayList<Activity>();
	private List<HumanActivity> publicActivityList = new ArrayList<HumanActivity>();
	
	@Override
	public void afterInit() {
		Globals.getTexasService().addListener(this);
		Globals.getRechargeService().addListner(this);
	}

	@Override
	public void destroy() {
		Globals.getTexasService().removeListener(this);
		Globals.getRechargeService().removeListner(this);
	}

	@Override
	public void init() {
		loadActivityFromDB();
		loadPublicActivity();
	}
	
	private void loadPublicActivity() {
		//获取公共的活动
		List<HumanActivityEntity> commonHumanActivityEntityList = Globals.getDaoService().getHumanActivityDao().getAllHumanActivities(0);
		for(HumanActivityEntity entity:commonHumanActivityEntityList){
			Activity activity = Globals.getActivityService().getActivityById(entity.getActivityId());
			if(activity == null)
				continue;
			long now = Globals.getTimeService().now();
			if(activity.getStartTime() >now ||activity.getEndTime()<now){
				continue;
			}
			HumanActivity humanActivity = new HumanActivity();
			humanActivity.fromEntity(entity);
			publicActivityList.add(humanActivity);
		}
	}

	/**
	 * 从数据库加载活动信息
	 */
	private void loadActivityFromDB()
	{
		
		List<ActivityEntity> dbActivityList=Globals.getDaoService().getActivityDao().getAllActivities();
		if(dbActivityList==null) return;
		long now=Globals.getTimeService().now();
		for(ActivityEntity activityEntity:dbActivityList) 
		{
			if(activityEntity.getEndTime()<now)
			{
				continue;
			}
			Activity activity = new Activity();
			activity.fromEntity(activityEntity);
			
			activityList.add(activity);
		}
		
	}
	
	/**
	 * 测试活动
	 */
	@SuppressWarnings("unused")
	private void  test(){
		addBigWinnerActivity();
		addLuckyCardActivity();
	}
	
	/**
	 * 添加活动
	 * 测试用例
	 * @param activity
	 * @deprecated
	 */
	public void addActivity(Activity activity){
//		activity.active();
//		activity.setInDb(false);
//		activity.setModified();
		activityList.add(activity);
		GCUpdateActivity gcUpdateActivity = new GCUpdateActivity();
		
		gcUpdateActivity.setActivityDataList(ActivityData.convertFromActivity(activity));
		//发送活动添加信息
		for(Player player : Globals.getOnlinePlayerService().getOnlinePlayers()){
			player.sendMessage(gcUpdateActivity);
		}
	}
	
	/**
	 * 更新活动   --- 普通的活动
	 * @param activity
	 */
	public void updateActivity(Activity activity){
		Activity tempActivity = getActivityById(activity.getDbId());
		if(tempActivity==null){
			logger.debug("add activity["+activity.getDbId()+"]");
			activityList.add(0,activity);
			tempActivity = activity;
		}
		
		GCUpdateActivity gcUpdateActivity = new GCUpdateActivity();
		
		gcUpdateActivity.setActivityDataList(ActivityData.convertFromActivity(tempActivity));
		//发送活动添加信息
		for(Player player : Globals.getOnlinePlayerService().getOnlinePlayers()){
			Human human =player.getHuman();
			if(human==null){
				logger.warn("player["+player.getPassportId()+"] logout,but still in online");
				return;
			}
			human.getHumanActivityManager().checkActivity(tempActivity);
			player.sendMessage(player.getHuman().getHumanActivityManager().buildGCHumanActivityRewardData(tempActivity));
			player.sendMessage(player.getHuman().getHumanActivityManager().buildGCHunamnProgressSingle(tempActivity));
			player.sendMessage(gcUpdateActivity);
			/**
			 * 有新的活动推送
			 */
			GCStillHaveActivityGold gCStillHaveActivityGold = new GCStillHaveActivityGold();
			player.sendMessage(gCStillHaveActivityGold);
		}
	}
	
	/**
	 * 删除活动
	 */
	public void deleteActivity(long aId){
		Activity tempActivity = getActivityById(aId);
		if(tempActivity==null){
			return;
		}
//		long now = Globals.getTimeService().now();
//		if(tempActivity.getStartTime()<now){
//			logger.warn("活动不能删除，已经开始了");
//			return;
//		}
		activityList.remove(tempActivity);
		tempActivity.onDelete();
		logger.info("活动["+tempActivity.getDbId()+"]删除");
	}
	
	/**
	 * 
	 * @return
	 */
	
	public List<Activity> getActivityList(){
		return activityList;
	}
	
	public Activity getActivityById(long aId){
		for(Activity activity :activityList){
			if(activity.getDbId() == aId)
				return activity;
		}
		return null;
	}
	
	/**
	 * 活动列表 角色登陆时候发送
	 * @return
	 */
	public GCActivityList buildActivityList(Human human){
	//	ActivityData[] activityDataArr = new ActivityData[activityList.size()];
		long now = Globals.getTimeService().now();
		List<ActivityData> tempActivityDataList = new ArrayList<ActivityData>();
		for(int i=0;i<activityList.size();i++){
			Activity tempActivity = activityList.get(i);
			if(tempActivity.getStartTime() >now ||tempActivity.getEndTime()<now){
				continue;
			}
			/**
			 * 处理当前用户的累计金币
			 */
			ActivityData activityData = ActivityData.convertFromActivity(tempActivity);
			HumanActivity humanActivity = human.getHumanActivityManager().getActivityById(tempActivity.getDbId());
			HumanActivityData humanActivityData = humanActivity.getHumanActivityData();
			List<Long> playCountList =  humanActivityData.getPlayCountList();
			if(tempActivity.getActivityType() == ActivityTypeEnum.ActivityType15 
					|| tempActivity.getActivityType() == ActivityTypeEnum.ActivityType18
					|| tempActivity.getActivityType() == ActivityTypeEnum.ActivityType17){
				
				if(playCountList != null && playCountList.size()>0){
					activityData.setFullValue(playCountList.get(0)+"");
				}
			}
			tempActivityDataList.add(activityData);
			
		}
		GCActivityList gcActivityList = new GCActivityList();
		gcActivityList.setActivityDataList(tempActivityDataList.toArray(new ActivityData[tempActivityDataList.size()]));
		return gcActivityList;
	}
	
	

	@Override
	public void onTexasPlay(Human human, int roomId) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 德州扑克回掉
	 */
	@Override
	public void onTexasPlayFinished(Human human, int roomId,int handCardType, int win) {
		for(Activity activity : Globals.getActivityService().getActivityList()){
			//活动没激活
			if(!activity.isActive())
				continue;
			
			 human.getHumanActivityManager().checkActivity(activity);
			 
			 HumanActivity humanActivity = human.getHumanActivityManager().getActivityById(activity.getDbId());
			 
			 switch(activity.getActivityType()){
				case BIG_PLAYER:
					onBigWinnerActivity(activity,human,humanActivity,roomId,win);
					break;
				case LUCKY_CARD:
					onLuckyCardActivity(activity,human,humanActivity,roomId,handCardType);
					break;
				default:
					break;
			}
		}
		
	}
	
	/**
	 * 充值回掉
	 */
	@Override
	public void onRecharge(Human human, int productId) {
	
		for(Activity activity : Globals.getActivityService().getActivityList()){
			//活动没激活
			if(!activity.isActive())
				continue;
			 human.getHumanActivityManager().checkActivity(activity);
			 HumanActivity humanActivity = human.getHumanActivityManager().getActivityById(activity.getDbId());
				switch(activity.getActivityType()){
				case RECHARGE:
					onRechargeActivity(activity,human,humanActivity,productId);
				case CUMULATIVE_RECHARGE:
					onCumulativeRechargeActivity(activity,human,humanActivity,productId);
					break;
				default:
					break;
				}
		}
	}
	
	/**
	 * 大赢家
	 * @param activity 活动模板数据
	 * @param humanActivity 活动人物数据
	 * @param roomId  房间ID
	 * @param win 赢得钱数
	 */
	private void onBigWinnerActivity(Activity activity,Human human,HumanActivity humanActivity,int roomId,int win){
		if(win<=0)return;
		
		TexasRoomTemplate texasRoomTemplate= Globals.getTemplateService().get(roomId, TexasRoomTemplate.class);
		
		HumanActivityData tempWinnerHumanActivityData = humanActivity.getHumanActivityData();
		
		
		for(int i=0;i<activity.getRuleList().size();i++){
			
			//奖励规则
			ActivityRewardRule activityRewardRule = activity.getRuleList().get(i);
			
			if(texasRoomTemplate.getRoomType()== activityRewardRule.getParamsMap().get(String.valueOf(ActivityConditionParamEnum.TEXAS_ROOM_TYPE.getIndex()))){
				//满足条件累加
				tempWinnerHumanActivityData.increaseCount(i);
			}
		}
	
		if(CommonRule.getInstance().ifRewardStatusChanged(human, humanActivity)){
			
			humanActivity.setModified();
			
			sendChange(human,activity);
		}
		
	}
	
	/**
	 * 更新活动
	 * @param human
	 * @param activity
	 */
	private void sendChange(Human human,Activity activity){
		HumanActivityManager manager = human.getHumanActivityManager();
		GCUpdateHumanActivityReward gcupdate = manager.buildGCHumanActivityRewardData(activity);
		GCHunamnProgressSingle gcprogress = manager.buildGCHunamnProgressSingle(activity);
		human.sendMessage(gcupdate);
		human.sendMessage(gcprogress);
	}
	/**
	 * 更新活动 公共活动
	 * @param human
	 * @param activity
	 */
	@SuppressWarnings("unused")
	private void sendPublicChange(Human human,Activity activity){
		HumanActivityManager manager = human.getHumanActivityManager();
		GCUpdateHumanActivityReward gcupdate = manager.pubBuildGCHumanActivityRewardData(activity);
		GCHunamnProgressSingle gcprogress = manager.buildGCHunamnProgressSingle(activity);
		human.sendMessage(gcupdate);
		human.sendMessage(gcprogress);
	}
	
	/**
	 * 幸运 
	 * @param activity
	 * @param humanActivity
	 * @param roomId  房间号
	 * @param handCardType 牌型
	 */
	private void onLuckyCardActivity(Activity activity,Human human,HumanActivity humanActivity,int roomId,int handCardType){
		
	
		TexasRoomTemplate texasRoomTemplate= Globals.getTemplateService().get(roomId, TexasRoomTemplate.class);
		
		HumanActivityData tempLuckyCardHumanActivityData = humanActivity.getHumanActivityData();
		
		for(int i=0;i<activity.getRuleList().size();i++){
			ActivityRewardRule activityRewardRule = activity.getRuleList().get(i);
			if(texasRoomTemplate.getSmallBlind()>= activityRewardRule.getParamsMap().get(String.valueOf(ActivityConditionParamEnum.SMALL_BLIND.getIndex())) && handCardType== activityRewardRule.getParamsMap().get(String.valueOf(ActivityConditionParamEnum.LUCKY_CARD.getIndex()))){
				//累加记录次数
				tempLuckyCardHumanActivityData.increaseCount(i);
			}
		}
		
		if(CommonRule.getInstance().ifRewardStatusChanged(human, humanActivity)){
			
			humanActivity.setModified();
			
			sendChange(human,activity);
		}
		
	}
	
	/**
	 * 充值活动
	 * @param activity
	 * @param humanActivity
	 * @param productId
	 */
	private void onRechargeActivity(Activity activity,Human human,HumanActivity humanActivity,int productId){
	
		HumanActivityData tempHumanActivityData = humanActivity.getHumanActivityData();
		
		for(int i=0;i<activity.getRuleList().size();i++){
			ActivityRewardRule activityRewardRule = activity.getRuleList().get(i);
			
			if(productId== activityRewardRule.getParamsMap().get(String.valueOf(ActivityConditionParamEnum.RECHARGE_PRODUCT_ID.getIndex()))){
				tempHumanActivityData.increaseCount(i);
				humanActivity.setModified();
			}
		}
		
		if(CommonRule.getInstance().ifRewardStatusChanged(human, humanActivity)){
			humanActivity.setModified();
			sendChange(human,activity);
		}
		
	}
	
	/**
	 * ###############################################################################################################################
	 */
	/**
	 * 累计充值活动  new 条件 是 美元
	 * @param human
	 * @param productId
	 */
	public void onGrandRechargeDollarActivity(Human human, int dollar) {
		try{
			for(Activity activity : Globals.getActivityService().getActivityList()){
				//活动没激活
				if(!activity.isActive())
					continue;
				human.getHumanActivityManager().checkActivity(activity);
				HumanActivity humanActivity = human.getHumanActivityManager().getActivityById(activity.getDbId());
				switch(activity.getActivityType()){
				case ActivityType15:
					onGrandRechargeDollarActivity(activity,human,humanActivity,dollar);
					break;
				default:
					break;
				}
			}
		}catch(Exception e){
			logger.error("", e);
		}
	}
	private void onGrandRechargeDollarActivity(Activity activity,Human human,HumanActivity humanActivity,int dollar){
		
		HumanActivityData tempHumanActivityData = humanActivity.getHumanActivityData();
		for(int i=0;i<activity.getRuleList().size();i++){
			//真钱（美元）
			tempHumanActivityData.increaseGold(i,dollar);
			humanActivity.setModified();
		}
		
		sendUpdateActivitList(activity,tempHumanActivityData,human.getPlayer());
		if(CommonRule.getInstance().ifGrandRewardStatusChangedDollar(human, humanActivity,ActivityConditionParamEnum.Condition22)){
			humanActivity.setModified();
			sendChange(human,activity);
		}
		
	}
	/**
	 * 累计充值活动  new 条件 次数
	 * @param human
	 * @param productId
	 */
	public void onGrandRechargeActivity(Human human) {
		try{
			for(Activity activity : Globals.getActivityService().getActivityList()){
				//活动没激活
				if(!activity.isActive())
					continue;
				human.getHumanActivityManager().checkActivity(activity);
				HumanActivity humanActivity = human.getHumanActivityManager().getActivityById(activity.getDbId());
				switch(activity.getActivityType()){
				case ActivityType18:
					onGrandRechargeActivity(activity,human,humanActivity);
					break;
				default:
					break;
				}
			}
		}catch(Exception e){
			logger.error("", e);
		}
	}
	private void onGrandRechargeActivity(Activity activity,Human human,HumanActivity humanActivity){
		
		HumanActivityData tempHumanActivityData = humanActivity.getHumanActivityData();
		for(int i=0;i<activity.getRuleList().size();i++){
			//次数
			tempHumanActivityData.increaseCount(i);
			humanActivity.setModified();
		}
		sendUpdateActivitList(activity,tempHumanActivityData,human.getPlayer());
		
		if(CommonRule.getInstance().ifGrandRewardStatusChangedDollar(human, humanActivity,ActivityConditionParamEnum.Condition23)){
			humanActivity.setModified();
			sendChange(human,activity);
		}
		
	}
	
	/**
	 * 连续充值活动  new 比如说7天，中间不可有间断，否则从新计算
	 * @param activity
	 * @param humanActivity
	 * @param productId
	 */
	public void onSeriesRechargeActivity(Human human, int gold) {
		try{
			for(Activity activity : Globals.getActivityService().getActivityList()){
				//活动没激活
				if(!activity.isActive())
					continue;
				 human.getHumanActivityManager().checkActivity(activity);
				 HumanActivity humanActivity = human.getHumanActivityManager().getActivityById(activity.getDbId());
					switch(activity.getActivityType()){
					case ActivityType16:
						onSeriesRechargeActivity(activity,human,humanActivity);
						break;
					default:
						break;
					}
			}
		}catch(Exception e){
			logger.error("", e);
		}
	}
	private void onSeriesRechargeActivity(Activity activity,Human human,HumanActivity humanActivity){
		
		HumanActivityData tempHumanActivityData = humanActivity.getHumanActivityData();
		try {
		for(int i=0;i<activity.getRuleList().size();i++){
			//条件是连续 每天 不可间断
			GrandDayVO grandDayVO = humanActivity.getConditionsGrand();
				if(grandDayVO == null){
					tempHumanActivityData.increaseCount(i);
					humanActivity.setConditionsGrand(TimeUtils.formatYMDTime(new Date().getTime()));
					humanActivity.setModified();
				}else{
					long date = TimeUtils.getYMDTime(grandDayVO.getDate());
					long now = TimeUtils.getYMDTime(TimeUtils.formatYMDTime(new Date().getTime()));
					long difference = now-date;
					if(difference == 24*60*60*1000){
						tempHumanActivityData.increaseCount(i);
						humanActivity.setConditionsGrand(TimeUtils.formatYMDTime(new Date().getTime()));
						humanActivity.setModified();
					}else if(difference > 24*60*60*1000){
						tempHumanActivityData.increaseCountNew(i, 1l);
						humanActivity.setConditionsGrand(TimeUtils.formatYMDTime(new Date().getTime()));
						humanActivity.setModified();
					}
				}
		}
		sendUpdateActivitList(activity,tempHumanActivityData,human.getPlayer());
		if(CommonRule.getInstance().ifGrandRewardStatusChanged(human, humanActivity,ActivityConditionParamEnum.Condition24)){
			humanActivity.setModified();
			sendChange(human,activity);
		}
		} catch (ParseException e) {
			logger.error("", e);
		}
		
		
	}
	
	
	/**
	 * 全服累计充值活动 new
	 * @param activity
	 * @param humanActivity
	 * @param productId
	 */
	public void onCommonSeriesRechargeActivity(Human human, long gold) {
		try{
			for(Activity activity : Globals.getActivityService().getActivityList()){
				//活动没激活
				if(!activity.isActive()){
					continue;
				}
				HumanActivity humanActivity = checkPubActivity(activity,human);
				HumanActivity pubHumanActivity = getPubActivityByActivityId(activity.getDbId());
				if(humanActivity == null || pubHumanActivity == null){
					continue;
				}
				switch(activity.getActivityType()){
				case ActivityType17:
					onCommonSeriesRechargeActivity(activity,pubHumanActivity,humanActivity,human,gold);
					break;
				default:
					break;
				}
			}
		}catch(Exception e){
			logger.error("", e);
		}
	}
	private void onCommonSeriesRechargeActivity(Activity activity,HumanActivity  pubHumanActivity,HumanActivity humanActivity,Human human,long gold){
		
		//HumanActivityData 这个在这里 是公用的不属于个人
		HumanActivityData tempHumanActivityData = pubHumanActivity.getHumanActivityData();
		
		for(int i=0;i<activity.getRuleList().size();i++){
			tempHumanActivityData.increaseGold(i, gold);
			pubHumanActivity.setOwner(human);
			pubHumanActivity.active();
			pubHumanActivity.setModified();
		}
		sendUpdateActivitList(activity,tempHumanActivityData,human.getPlayer());
		if(CommonRule.getInstance().ifFullServerGrandRewardStatusChanged(human, pubHumanActivity,humanActivity,ActivityConditionParamEnum.Condition25)){
			humanActivity.setOwner(human);
			humanActivity.setModified();
			GameUnitList<Player> players = Globals.getOnlinePlayerService().getOnlinePlayers();
			for(Player player:players){
				/**
				 * 
				 * 
				 * 每个人都需要改一下 humanActivity 
				 * 让每个人都变成可以领取状态
				 * 
				 * 
				 **/
				//允许领取次数 已经领取次数
				HumanActivityData humanActivityData = pubHumanActivity.getHumanActivityData();
				HumanActivity perHumanActivity = player.getHuman().getHumanActivityManager().getActivityById(activity.getDbId());
		    	List<HumanRewardActivityDetailData> humanRewardActivityDataList = perHumanActivity.getHumanRewardActivityDetailDataList();
		    	//活动模板数据
		    	for(int i=0;i< activity.getRuleList().size();i++){
		    		//获取游戏规则
		    		ActivityRewardRule rule =  activity.getRuleList().get(i);
		    		//人物自己的数据
		    		HumanRewardActivityDetailData tempHumanRewardActivityDetailData = humanRewardActivityDataList.get(i);
		    		//累积值
		    		long drawCount = humanActivityData.getPlayCountList().get(i);	
		    		//规定的次数
		    		int count = rule.getParamsMap().get(String.valueOf(ActivityConditionParamEnum.Condition25.getIndex()));
		    		
		    		if(drawCount >= count){//累计规定的次数
		    			tempHumanRewardActivityDetailData.setAllowDrawCount(rule.getMaxDrows());
		    		}
		    	}
		    	sendChange(player.getHuman(),activity);
			}
		}
		
	}
	
	
	/**
	 * 更新当前的累计 金币
	 * 如果是个人的累计金币 tempHumanActivityData 这个就改成个人的，如果是 全体的 tempHumanActivityData这个就改成全体的
	 */
	private void sendUpdateActivitList(Activity activity,HumanActivityData tempHumanActivityData,Player player){
		List<Long> playCountList  = tempHumanActivityData.getPlayCountList();
		if(playCountList != null && playCountList.size() >0){
			GCActivityList gCActivityList = new GCActivityList();
			ActivityData activityData = ActivityData.convertFromActivity(activity);
			/**
			 * 只取第一个值    当前用户（或者是全服的）累计的金币
			 */
			activityData.setFullValue(playCountList.get(0)+"");
			ActivityData[] activityData1 = new ActivityData[1];
			activityData1[0]=activityData;
			gCActivityList.setActivityDataList(activityData1);
			player.sendMessage(gCActivityList);
		}
	}
	/**
	 * ###############################################################################################################################
	 */
	
	
	private void onCumulativeRechargeActivity(Activity activity,Human human,HumanActivity humanActivity,int productId){
		
	}
	
	/**
	 * 角色等级变化的时候调用
	 * @param human
	 */
	public void changLevel(Human human){
		for(Activity activity : Globals.getActivityService().getActivityList()){
			//活动没激活
			if(!activity.isActive())
				continue;
			
			 human.getHumanActivityManager().checkActivity(activity);
			 
			 HumanActivity humanActivity = human.getHumanActivityManager().getActivityById(activity.getDbId());
			 
			 switch(activity.getActivityType()){
				case ActivityType8:
					
				long level = human.getLevel();
				 
				HumanActivityData humanActivit = humanActivity.getHumanActivityData();
				
				for(int i=0;i<activity.getRuleList().size();i++){
					ActivityRewardRule activityRewardRule = activity.getRuleList().get(i);
					if(level >= activityRewardRule.getParamsMap().get(String.valueOf(ActivityConditionParamEnum.Condition10.getIndex()))){
						humanActivit.increaseCount(1);//等级达到就累加次数
						humanActivity.setModified();
					}
				}
				
				if(CommonRule.getInstance().ifRewardStatusChanged(human, humanActivity)){
					
					humanActivity.setModified();
					sendChange(human,activity);
				}
				
					break;
				default:
					break;
			}
		}
	}
	
	/**
	 * 角色等级变化的时候调用
	 * @param human
	 */
	public void changLevelNew(Human human){
		for(Activity activity : Globals.getActivityService().getActivityList()){
			//活动没激活
			if(!activity.isActive())
				continue;
			
			human.getHumanActivityManager().checkActivity(activity);
			
			HumanActivity humanActivity = human.getHumanActivityManager().getActivityById(activity.getDbId());
			
			switch(activity.getActivityType()){
			case ActivityType8:
				
				long level = human.getLevel();
				
				HumanActivityData humanActivit = humanActivity.getHumanActivityData();
				
				for(int i=0;i<activity.getRuleList().size();i++){
					//ActivityRewardRule activityRewardRule = activity.getRuleList().get(i);
					//if(level >= activityRewardRule.getParamsMap().get(String.valueOf(ActivityConditionParamEnum.Condition10.getIndex()))){
						humanActivit.increaseCountNew(i,(int)level);//等级达到就累加次数
						humanActivity.setModified();
					//}
				}
				
				if(CommonRule.getInstance().ifRewardStatusChangedSlot(human, humanActivity,ActivityConditionParamEnum.Condition10)){
					
					humanActivity.setModified();
					sendChange(human,activity);
				}
				
				break;
			default:
				break;
			}
		}
	}
	
	
	/**
	 * 每日登陆
	 * 每次登陆的时候调用
	 * 调用的时候已经判断过了
	 * @param human
	 */
	public void everyDay(Human human){
		for(Activity activity : Globals.getActivityService().getActivityList()){
			//活动没激活
			if(!activity.isActive())
				continue;
			
			 human.getHumanActivityManager().checkActivity(activity);
			 
			 HumanActivity humanActivity = human.getHumanActivityManager().getActivityById(activity.getDbId());
			 
			 switch(activity.getActivityType()){
				case ActivityType5:
					
				HumanActivityData humanActivit = humanActivity.getHumanActivityData();
				
				for(int i=0;i<activity.getRuleList().size();i++){
					humanActivit.increaseCount(i);//累计登陆天数都得累加
				}
				
				humanActivity.setModified();
				
				if(CommonRule.getInstance().ifRewardStatusChangedSlot(human, humanActivity,ActivityConditionParamEnum.Condition6)){
					
					humanActivity.setModified();
					sendChange(human,activity);
					
				}
				
					break;
				default:
					break;
			}
		}
		
		
	}
	
	/**
	 * 幸运老虎机
	 * @param human
	 * @param slotType
	 */
	public void luckySlot(Human human,int slotType){
		for(Activity activity : Globals.getActivityService().getActivityList()){
			//活动没激活
			if(!activity.isActive())
				continue;
			
			 human.getHumanActivityManager().checkActivity(activity);
			 
			 HumanActivity humanActivity = human.getHumanActivityManager().getActivityById(activity.getDbId());
			 
			 switch(activity.getActivityType()){
				case ActivityType7:
				HumanActivityData humanActivit = humanActivity.getHumanActivityData();
				boolean fly = false;
				for(int i=0;i<activity.getRuleList().size();i++){
					ActivityRewardRule activityRewardRule = activity.getRuleList().get(i);
					if(slotType == activityRewardRule.getParamsMap().get(String.valueOf(ActivityConditionParamEnum.Condition8.getIndex()))){
						humanActivit.increaseCount(i);
						fly = true;
					}
				}
				
				if(fly){
					humanActivity.setModified();
				}
				
				if(CommonRule.getInstance().ifRewardStatusChangedSlot(human, humanActivity,ActivityConditionParamEnum.Condition9)){
					humanActivity.setModified();
					sendChange(human,activity);
				}
				
					break;
				default:
					break;
			}
		}
		
	}
	
	
	/**
	 * 经验加成 百分百
	 * @return
	 */
	public  int isExpActivity(Human human){
		int num  = 0;
		for(Activity activity : Globals.getActivityService().getActivityList()){
			//活动没激活
			if(!activity.isActive())
				continue;
			
			 human.getHumanActivityManager().checkActivity(activity);
			 
			 switch(activity.getActivityType()){
				case ActivityType6:
				ActivityRewardRule activityRewardRule =  activity.getRuleList().get(0);
				num = activityRewardRule.getParamsMap().get(String.valueOf(ActivityConditionParamEnum.Condition7.getIndex()));
					break;
				default:
					break;
			}
		}
		
		return num;
	}
	
	
	
	/**
	 * 获取领取状态
	 * @param humanActivity
	 * @return
	 */
	public DrawType getDrawTypeForActivity(HumanActivity humanActivity){
		
		//允许领取次数  已经领取次数
		List<HumanRewardActivityDetailData>  tempHumanRewardActivityDetailDataList = humanActivity.getHumanRewardActivityDetailDataList();
		
		//活动模板数据
		Activity activity = Globals.getActivityService().getActivityById(humanActivity.getActivityId());
		
         DrawType tempDrawType = DrawType.ALREADYDRAW;//这个活动全部领取完毕 也就是完成了
		
		for(int i =0;i< tempHumanRewardActivityDetailDataList.size();i++){
			//模板
			@SuppressWarnings("unused")
			ActivityRewardRule activityRewardRule= activity.getActivityRewardRuleByIndex(i);
			//现实数据
			HumanRewardActivityDetailData tempHumanRewardActivityDetailData = tempHumanRewardActivityDetailDataList.get(i);
			
			//判断是否可以领取   allowDrawCount 为0 表示不可以领取 没有完成
			if(tempHumanRewardActivityDetailData.getAllowDrawCount() == 0){
				tempDrawType=  DrawType.NODRAW;
				continue;
			}
			
			if(tempHumanRewardActivityDetailData.getHasDrawCount() < tempHumanRewardActivityDetailData.getAllowDrawCount()){
				tempDrawType=  DrawType.ALLOWDRAW;//可以领取   只要有一个可以领取就认为 这个活动是可以领取的
				break;
			}
		}
		
		return tempDrawType;
		
	/*	DrawType tempDrawType = DrawType.NODRAW;
		
		for(int i =0;i< tempHumanRewardActivityDetailDataList.size();i++){
			//模板
			ActivityRewardRule activityRewardRule= activity.getActivityRewardRuleByIndex(i);
			//现实数据
			HumanRewardActivityDetailData tempHumanRewardActivityDetailData = tempHumanRewardActivityDetailDataList.get(i);
			
			//判断是否可以领取   allowDrawCount 为0 表示不可以领取
			if(tempHumanRewardActivityDetailData.getAllowDrawCount() == 0){
				tempDrawType=  DrawType.NODRAW;
				continue;
			}
			//判断是否还有可以领取的
			if(tempHumanRewardActivityDetailData.getHasDrawCount() == tempHumanRewardActivityDetailData.getAllowDrawCount()){
				if(tempHumanRewardActivityDetailData.getAllowDrawCount() == activityRewardRule.getMaxDrows()){
					tempDrawType=  DrawType.ALREADYDRAW;//已经领取   也就是这个活动已经结束了领取完所有的奖励了
				}else{
					tempDrawType = DrawType.NODRAW;
				}
				continue;
			}
			tempDrawType = DrawType.ALLOWDRAW;//还有领取次数
			break;
		}*/
		
		
	}
	
	
	/**
	 * 获取领取状态
	 * @param humanActivity
	 * @return
	 */
	public  int[] getDrawTypeForActivityNew(HumanActivity humanActivity){
		
		 List<Integer> list = new ArrayList<Integer>();
		
		//允许领取次数  已经领取次数
		List<HumanRewardActivityDetailData>  tempHumanRewardActivityDetailDataList = humanActivity.getHumanRewardActivityDetailDataList();

		for(int i =0;i< tempHumanRewardActivityDetailDataList.size();i++){
			
			//现实数据
			HumanRewardActivityDetailData tempHumanRewardActivityDetailData = tempHumanRewardActivityDetailDataList.get(i);
			
			//判断是否可以领取   allowDrawCount 为0 表示不可以领取 没有完成
			if(tempHumanRewardActivityDetailData.getAllowDrawCount() == 0){
				list.add(DrawType.NODRAW.getIndex());
			}else if(tempHumanRewardActivityDetailData.getHasDrawCount() < tempHumanRewardActivityDetailData.getAllowDrawCount()){
				list.add(DrawType.ALLOWDRAW.getIndex());
			}else{
				list.add(DrawType.ALREADYDRAW.getIndex());
			}
		}
		int[] num = new int[list.size()];
		 for(int index = 0;index < list.size();index++){
			 num[index] = list.get(index);
		 }
		
		return num;
	}
	/**
	 * 
	 * 最新添加的活动... ...
	 * 
	 */
	
	
	/**
	 * 连续游戏送大礼
	 * @param human
	 * @param slotType
	 */
	public void continuousPlayForGift(Human human){
		for(Activity activity : Globals.getActivityService().getActivityList()){
			//活动没激活
			if(!activity.isActive())
				continue;
			
			 human.getHumanActivityManager().checkActivity(activity);
			 
			 HumanActivity humanActivity = human.getHumanActivityManager().getActivityById(activity.getDbId());
			 
			 switch(activity.getActivityType()){
				case ActivityType9:
				HumanActivityData humanActivit = humanActivity.getHumanActivityData();
				boolean fly = false;
				for(int i=0;i<activity.getRuleList().size();i++){
					humanActivit.increaseCount(i);
					fly = true;
				}
				
				if(fly){
					humanActivity.setModified();
				}
				
				if(CommonRule.getInstance().ifRewardStatusChangedForMaxNum(human, humanActivity,ActivityConditionParamEnum.Condition11)){
					humanActivity.setModified();
					sendChange(human,activity);
				}
				
					break;
				default:
					break;
			}
		}
		
	}
	/**
	 * 消耗返利
	 * @param human
	 * @param slotType
	 */
	public void ConsumeForGift(Human human){
		for(Activity activity : Globals.getActivityService().getActivityList()){
			//活动没激活
			if(!activity.isActive())
				continue;
			//每天刷新  HumanRewardActivityDetailData 的两个值为零
			human.getHumanActivityManager().checkActivity(activity);
			
			HumanActivity humanActivity = human.getHumanActivityManager().getActivityById(activity.getDbId());
			//每天刷新 HumanActivityData 值为 零
			human.getHumanActivityManager().checkActivityPlayCountList(activity,humanActivity);
			
			Long currentBet = human.getHumanSlotManager().getCurrentBet();
			switch(activity.getActivityType()){
			case ActivityType10:
				HumanActivityData humanActivit = humanActivity.getHumanActivityData();
				boolean fly = false;
				for(int i=0;i<activity.getRuleList().size();i++){
					humanActivit.increaseGold(i,currentBet.intValue());
					fly = true;
				}
				
				if(fly){
					humanActivity.setModified();
				}
				
				if(CommonRule.getInstance().ifRewardStatusChangedForMaxNum(human, humanActivity,ActivityConditionParamEnum.Condition12)){
					humanActivity.setModified();
					sendChange(human,activity);
				}
				
				break;
			default:
				break;
			}
		}
		
	}
	//如果加过好友 ,就 过
	private boolean judgeEverAddedFriend(HumanActivity humanActivity,long passportId){
		//如果加过好友 ,就 过
		String friendIds = humanActivity.getFriendId();
		if(StringUtils.isBlank(friendIds)){
			return false;
		}
		String requestFriendId = String.valueOf(passportId);
		return friendIds.contains(requestFriendId);
	}
	/**
	 * 广交好友 每次登录的时候都要判断下是否有 申请的好友同意了
	 * 这种情况 主要是处理用户 不在线的情况
	 * @param human
	 * @param slotType
	 */
	public void loginCheckRequestFriend(Human human){
		
		List<Friend> friends = human.getHumanRelationManager().getFriendList();
		//申请的好友同意的个数
		List<Long> requestfriends = new ArrayList<Long>();
		//判断自己的申请是否被人同意了 同意了 就把 agree 给跟更新为1
		if(friends != null){
			for(Friend friend:friends){
				if(friend.getAgree() == 0){
					requestfriends.add(friend.getCharId());
					friend.setAgree(1);
					friend.setModified();
				}
			}
		}
		for(int i=0;i<requestfriends.size();i++){
			MakeFriendsForGift(human,requestfriends.get(i));
		}
			
	}
	/**
	 * 广交好友
	 * @param human
	 * @param slotType
	 */
	public void MakeFriendsForGift(Human human, long friendPassportId){
		for(Activity activity : Globals.getActivityService().getActivityList()){
			//活动没激活
			if(!activity.isActive())
				continue;
			/**被加用户**/
			 human.getHumanActivityManager().checkActivity(activity);
			 
			 HumanActivity humanActivity = human.getHumanActivityManager().getActivityById(activity.getDbId());
			 if(judgeEverAddedFriend(humanActivity,friendPassportId)){
				 continue;
			 }
			 switch(activity.getActivityType()){
				case ActivityType11:
				HumanActivityData humanActivit = humanActivity.getHumanActivityData();
				boolean fly = false;
				//如果增加好友 
				for(int i=0;i<activity.getRuleList().size();i++){
					humanActivit.increaseCount(i);
					fly = true;
				}
				
				if(fly){
					String f = humanActivity.getFriendId();
					String ff = (f==null?"":f);
					String fff = (ff+","+friendPassportId);
					humanActivity.setFriendId(fff);
					humanActivity.setModified();
				}
				
				if(CommonRule.getInstance().ifRewardStatusChangedForMaxNum(human, humanActivity,ActivityConditionParamEnum.Condition13)){
					humanActivity.setModified();
					sendChange(human,activity);
				}
				
					break;
				default:
					break;
			}
		}
		
	}
	
	/**
	 * 情有独钟
	 * @param human
	 * @param slotType
	 */
	public void preferenceFavorForGift(Human human,int slotType){
		for(Activity activity : Globals.getActivityService().getActivityList()){
			//活动没激活
			if(!activity.isActive())
				continue;
			
			 human.getHumanActivityManager().checkActivity(activity);
			 
			 HumanActivity humanActivity = human.getHumanActivityManager().getActivityById(activity.getDbId());
			 Long currentBet = human.getHumanSlotManager().getCurrentBet();
			 switch(activity.getActivityType()){
				case ActivityType12:
				HumanActivityData humanActivit = humanActivity.getHumanActivityData();
				boolean fly = false;
				for(int i=0;i<activity.getRuleList().size();i++){
					ActivityRewardRule activityRewardRule = activity.getRuleList().get(i);
					//如果是指定类型的老虎机
					if(slotType == activityRewardRule.getParamsMap().get(String.valueOf(ActivityConditionParamEnum.Condition14.getIndex()))){
						//转动次数
						if(activityRewardRule.getParamsMap().get(String.valueOf(ActivityConditionParamEnum.Condition15.getIndex())) > 0){
							humanActivit.increaseCount(i);
							fly = true;
						//消费金币
						}else if(activityRewardRule.getParamsMap().get(String.valueOf(ActivityConditionParamEnum.Condition20.getIndex())) > 0){
							humanActivit.getPlayCountList().add(currentBet);
							fly = true;
						}
					}
				}
				
				if(fly){
					humanActivity.setModified();
				}
				
				if(CommonRule.getInstance().ifRewardStatusChangedForMaxNum(human, humanActivity,ActivityConditionParamEnum.Condition15)){
					humanActivity.setModified();
					sendChange(human,activity);
				}
				
					break;
				default:
					break;
			}
		}
		
	}
	
	
	/**
	 * 我是大赢家
	 * @param human
	 * @param slotType
	 */
	public void IAmWinnerForGift(Human human,int freeTimes,int tempReward){
		for(Activity activity : Globals.getActivityService().getActivityList()){
			//活动没激活
			if(!activity.isActive())
				continue;
			
			 human.getHumanActivityManager().checkActivity(activity);
			 
			 HumanActivity humanActivity = human.getHumanActivityManager().getActivityById(activity.getDbId());
			 switch(activity.getActivityType()){
				case ActivityType13:
				HumanActivityData humanActivit = humanActivity.getHumanActivityData();
				boolean fly = false;
				for(int i=0;i<activity.getRuleList().size();i++){
					ActivityRewardRule activityRewardRule = activity.getRuleList().get(i);
					//转动次数
					if(activityRewardRule.getParamsMap().get(String.valueOf(ActivityConditionParamEnum.Condition16.getIndex())) > 0){
						humanActivit.getPlayCountList().add(Long.valueOf(freeTimes));
						fly = true;
					}else if(activityRewardRule.getParamsMap().get(String.valueOf(ActivityConditionParamEnum.Condition21.getIndex())) > 0){
						humanActivit.getPlayCountList().add(Long.valueOf(tempReward));
						fly = true;
					}
				}
				
				if(fly){
					humanActivity.setModified();
				}
				
				if(CommonRule.getInstance().ifRewardStatusChangedForMaxNum(human, humanActivity,ActivityConditionParamEnum.Condition16)){
					humanActivity.setModified();
					sendChange(human,activity);
				}
				
					break;
				default:
					break;
			}
		}
	}
	

	/**
	 * 极限追求
	 * @param human
	 * @param slotType
	 */
	public void extremePursuitForGift(Human human,int slotType,List<Integer> AppearedTurns){
		for(Activity activity : Globals.getActivityService().getActivityList()){
			//活动没激活
			if(!activity.isActive())
				continue;
			
			 human.getHumanActivityManager().checkActivity(activity);
			 
			 HumanActivity humanActivity = human.getHumanActivityManager().getActivityById(activity.getDbId());
			 
			 switch(activity.getActivityType()){
				case ActivityType14:
				HumanActivityData humanActivit = humanActivity.getHumanActivityData();
				boolean fly = false;
				for(int i=0;i<activity.getRuleList().size();i++){
					ActivityRewardRule activityRewardRule = activity.getRuleList().get(i);
					//大于1000就是金额，小于1000就是 转动次数
					if(slotType == activityRewardRule.getParamsMap().get(String.valueOf(ActivityConditionParamEnum.Condition17.getIndex()))){
						for(Integer perTurn:AppearedTurns){
							//出现过的元素中如果出现过这个元素（图标），就给他记录下来
							if(perTurn.intValue() == 
									activityRewardRule.getParamsMap().get(String.valueOf(ActivityConditionParamEnum.Condition18.getIndex()))){
								humanActivit.increaseCount(i);
								fly = true;
							}
							
						}
					}
				}
				
				if(fly){
					humanActivity.setModified();
				}
				
				if(CommonRule.getInstance().ifRewardStatusChangedForMaxNum(human, humanActivity,ActivityConditionParamEnum.Condition19)){
					humanActivity.setModified();
					sendChange(human,activity);
				}
				
					break;
				default:
					break;
			}
		}
		
	}
	
	
	/**
	 * 测试
	 * 测试用例
	 */
	public void addBigWinnerActivity(){
		long now = Globals.getTimeService().now();
		Activity activity = new Activity();
		activity.setTitle("测试大玩家");
		activity.setActivityDesc("测试大玩家");
		activity.setActivityType(ActivityTypeEnum.BIG_PLAYER);
		activity.setDbId(1L);
		activity.setCreateTime(now);
		activity.setInDb(true);
		activity.setStartTime(now);
		activity.setEndTime(now + TimeUtils.DAY);
		activity.setPic("");
		activity.setDaily(0);
		List<ActivityRewardRule> activityRewardRuleList = new ArrayList<ActivityRewardRule>();
		ActivityRewardRule activityRewardRule1 = new ActivityRewardRule();
		activityRewardRule1.setMaxDrows(1);
		activityRewardRule1.getParamsMap().put(String.valueOf( ActivityConditionParamEnum.TEXAS_ROOM_TYPE.getIndex()), 1);
		activityRewardRule1.getParamsMap().put(String.valueOf( ActivityConditionParamEnum.WIN_COUNT.getIndex()), 10);
		
		List<RandRewardData> randRewardDataList1 = new ArrayList<RandRewardData>();
		RandRewardData randRewardData1 = new RandRewardData();
		randRewardData1.setRewardId(Currency.GOLD.getIndex());
		randRewardData1.setRewardCount(1000);
		randRewardDataList1.add(randRewardData1);
		activityRewardRule1.setRewardList(randRewardDataList1);
		activityRewardRuleList.add(activityRewardRule1);
		
		ActivityRewardRule activityRewardRule2 = new ActivityRewardRule();
		activityRewardRule2.setMaxDrows(1);
	
		activityRewardRule2.getParamsMap().put(String.valueOf(ActivityConditionParamEnum.TEXAS_ROOM_TYPE.getIndex()), 2);
		activityRewardRule2.getParamsMap().put(String.valueOf(ActivityConditionParamEnum.WIN_COUNT.getIndex()), 10);
		
		List<RandRewardData> randRewardDataList2 = new ArrayList<RandRewardData>();
		RandRewardData randRewardData2 = new RandRewardData();
		randRewardData2.setRewardId(Currency.GOLD.getIndex());
		randRewardData2.setRewardCount(1000);
		randRewardDataList2.add(randRewardData2);
		activityRewardRule2.setRewardList(randRewardDataList2);
		activityRewardRuleList.add(activityRewardRule2);
		
		ActivityRewardRule activityRewardRule3 = new ActivityRewardRule();
		activityRewardRule3.setMaxDrows(1);

		activityRewardRule3.getParamsMap().put(String.valueOf(ActivityConditionParamEnum.TEXAS_ROOM_TYPE.getIndex()), 3);
		activityRewardRule3.getParamsMap().put(String.valueOf(ActivityConditionParamEnum.WIN_COUNT.getIndex()), 10);
		
		
		List<RandRewardData> randRewardDataList3 = new ArrayList<RandRewardData>();
		RandRewardData randRewardData3 = new RandRewardData();
		randRewardData3.setRewardId(Currency.GOLD.getIndex());
		randRewardData3.setRewardCount(1000);
		randRewardDataList3.add(randRewardData3);
		activityRewardRule3.setRewardList(randRewardDataList3);
		activityRewardRuleList.add(activityRewardRule3);
		
		activity.setRuleList(activityRewardRuleList);
		addActivity(activity);
	}
	
	/**
	 * 幸运牌型
	 * 测试用例
	 */
	public void addLuckyCardActivity(){
		long now = Globals.getTimeService().now();
		Activity activity = new Activity();
		activity.setTitle("测试幸运牌型");
		activity.setActivityDesc("测试幸运牌型");
		activity.setActivityType(ActivityTypeEnum.LUCKY_CARD);
		activity.setDbId(2L);
		activity.setCreateTime(now);
		activity.setInDb(true);
		activity.setStartTime(now);
		activity.setEndTime(now + TimeUtils.DAY);
		activity.setPic("");
		activity.setDaily(0);
		List<ActivityRewardRule> activityRewardRuleList = new ArrayList<ActivityRewardRule>();
		ActivityRewardRule activityRewardRule1 = new ActivityRewardRule();
		activityRewardRule1.setMaxDrows(1);
		activityRewardRule1.getParamsMap().put(String.valueOf(ActivityConditionParamEnum.LUCKY_CARD.getIndex()), 0);
		activityRewardRule1.getParamsMap().put(String.valueOf(ActivityConditionParamEnum.SMALL_BLIND.getIndex()), 2000);
		
		List<RandRewardData> randRewardDataList1 = new ArrayList<RandRewardData>();
		RandRewardData randRewardData1 = new RandRewardData();
		randRewardData1.setRewardId(Currency.GOLD.getIndex());
		randRewardData1.setRewardCount(1000);
		randRewardDataList1.add(randRewardData1);
		activityRewardRule1.setRewardList(randRewardDataList1);
		activityRewardRuleList.add(activityRewardRule1);
		
		ActivityRewardRule activityRewardRule2 = new ActivityRewardRule();
		activityRewardRule2.setMaxDrows(1);
	
		activityRewardRule2.getParamsMap().put(String.valueOf(ActivityConditionParamEnum.LUCKY_CARD.getIndex()), 1);
		activityRewardRule2.getParamsMap().put(String.valueOf(ActivityConditionParamEnum.SMALL_BLIND.getIndex()), 2000);
		
		List<RandRewardData> randRewardDataList2 = new ArrayList<RandRewardData>();
		RandRewardData randRewardData2 = new RandRewardData();
		randRewardData2.setRewardId(Currency.GOLD.getIndex());
		randRewardData2.setRewardCount(1000);
		randRewardDataList2.add(randRewardData2);
		activityRewardRule2.setRewardList(randRewardDataList2);
		activityRewardRuleList.add(activityRewardRule2);
		
		ActivityRewardRule activityRewardRule3 = new ActivityRewardRule();
		activityRewardRule3.setMaxDrows(1);

		activityRewardRule3.getParamsMap().put(String.valueOf(ActivityConditionParamEnum.LUCKY_CARD.getIndex()), 2);
		activityRewardRule3.getParamsMap().put(String.valueOf(ActivityConditionParamEnum.SMALL_BLIND.getIndex()), 2000);
		
		
		List<RandRewardData> randRewardDataList3 = new ArrayList<RandRewardData>();
		RandRewardData randRewardData3 = new RandRewardData();
		randRewardData3.setRewardId(Currency.GOLD.getIndex());
		randRewardData3.setRewardCount(1000);
		randRewardDataList3.add(randRewardData3);
		activityRewardRule3.setRewardList(randRewardDataList3);
		activityRewardRuleList.add(activityRewardRule3);
		
		activity.setRuleList(activityRewardRuleList);
		addActivity(activity);
	}

	//检查公共的单个活动
	public HumanActivity checkPubActivity(Activity activity,Human owner){
	
		try{
		
			//判断是否过期一星期
			if(activity.isEnd()){
				
				HumanActivity humanActivity = getPubActivityByActivityId(activity.getDbId());
				if(humanActivity==null){
					return null;
				}
				if(ifShouldDelete(activity))
				{
					logger.info("玩家["+owner.getPassportId()+"]活动["+activity.getDbId()+"]过期 活动数据删除");
					publicActivityList.remove(humanActivity);
					humanActivity.onDelete();
				}
				return null;
			}
			
			//判断是否激活
			if(activity.isActive()){
				HumanActivity humanActivity = owner.getHumanActivityManager().getActivityById(activity.getDbId());
				if(humanActivity == null){
					logger.info("玩家["+owner.getPassportId()+"]活动["+activity.getDbId()+"]活动数据不存在 添加活动数据");
					
					long now = Globals.getTimeService().now();
					humanActivity = new HumanActivity();
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
					 humanActivity.setCharId(owner.getPassportId());
					 humanActivity.setOwner(owner);
					 humanActivity.setInDb(false);
					 humanActivity.setCreateTime(now);
					 humanActivity.active();
					 humanActivity.setModified();
					 owner.getHumanActivityManager().getHumanActivityList().add(humanActivity);
					 return humanActivity;
				}
				
				/**
				 * 判断是否是隔天刷新
				 * 每天是否重复，如果用户 完成了活动，领取了一次，第二天 可以 把数据刷新，让用户在 去 玩，如果在满足了条件 ，可以再去领取，如此重复
				 * 
				 */
				if(activity.getDaily()==1){
					long now = Globals.getTimeService().now();
					if(TimeUtils.isSameDay(humanActivity.getUpdateTime(), now)){
						return humanActivity;
					}
					logger.info("玩家["+owner.getPassportId()+"]活动["+activity.getDbId()+"]活动数据过期  刷新活动数据");
					
					humanActivity.getHumanRewardActivityDetailDataList().clear();
					 for( ActivityRewardRule activityRewardRule: activity.getRuleList()){
						 humanActivity.getHumanRewardActivityDetailDataList().add(HumanRewardActivityDetailData.convertFromActivityRewardRule(activityRewardRule));
					 }
				}
				return humanActivity;
			}
		
		}catch(Exception e){
			logger.error("", e);
		}
		return null;
	
	}
	public HumanActivity getPubActivityByActivityId(long activityId){
		for(HumanActivity humanActivity :publicActivityList){
			if(humanActivity.getActivityId() == activityId){
				return humanActivity;
			}
		}
		return null;
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
	
	
	/**
	 * 公共的活动 领取奖励那边
	 * @param activityid
	 * @param index
	 * @return
	 */
	public int getAvailableRewardIndexNew(long activityId, int indexId) {
		HumanActivity humanActivity = getPubActivityByActivityId(activityId);
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
	
	
	
	public void getReward(long activityId, int ruleIndex) {
		HumanActivity humanActivity = getPubActivityByActivityId(activityId);
		HumanRewardActivityDetailData humanRewardActivityDetailData=humanActivity.getHumanRewardActivityDetailDataList().get(ruleIndex);
		humanRewardActivityDetailData.setHasDrawCount(humanRewardActivityDetailData.getHasDrawCount()+1);
		humanActivity.setModified();
		
	}
	
	
	
	
	
	
	
	
	
	
	

	public List<HumanActivity> getPublicActivityList() {
		return publicActivityList;
	}

	public void setPublicActivityList(List<HumanActivity> publicActivityList) {
		this.publicActivityList = publicActivityList;
	}

	

	

	
}
