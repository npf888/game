package com.gameserver.human.manager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.common.LogReasons;
import com.common.constants.Loggers;
import com.core.util.StringUtils;
import com.core.util.TimeUtils;
import com.db.model.HumanEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.human.data.HumanInfoData;
import com.gameserver.human.msg.GCHumanDetailInfo;
import com.gameserver.ip.geoip.IPSearchService;
import com.gameserver.mail.enums.MailTypeEnum;
import com.gameserver.player.Player;
import com.gameserver.player.PlayerState;
import com.gameserver.player.enums.PlayerRoleEnum;
import com.gameserver.rank.RankLogic;
import com.gameserver.rank.enums.RankTypeEnum;
import com.gameserver.relation.RelationLogic;
import com.gameserver.slot.pojo.SngInfo;




/**
 * 角色初始化过程
 * @author Thinker
 * 
 */
public final class HumanInitManager
{
	private static final Logger logger = Loggers.humanLogger;
	/** 单例对象 */
	private static HumanInitManager instance = null;

	private HumanInitManager()
	{
		
	}

	/**
	 * 获取单例对象
	 * @return 
	 * 
	 */
	public static HumanInitManager getInstance() 
	{
		if(instance==null) instance = new HumanInitManager();
		return instance;
	}

	/**
	 * 加载玩家角色基本信息
	 */
	public void loadHuman(Human human, HumanEntity entity)
	{
		if (human == null || entity == null)
		{
			// 如果参数为空, 则直接退出!
			return;
		}
	

		human.setDbId(entity.getId());
		human.setInDb(true);
		human.setImg(entity.getImg());
		human.setNewGuyGift(entity.getNewGuyGift());
		human.setTodayView(entity.getTodayView());
		human.setName(entity.getName());
		human.setCouponExtraChip(entity.getCouponExtraChip());
		human.setCouponDurationDate(entity.getCouponDurationDate());
		// 设置为女性玩家角色
		human.setGirl(entity.getGirlFlag());
		
		//设置角色创建时间
		human.setCreateTime(entity.getCreateTime()==0?Globals.getTimeService().now():entity.getCreateTime());
		IPSearchService iPSearchService = Globals.getiPSearchService();
		String ipAndPort = entity.getLastLoginIp();
		if(!StringUtils.isEmpty(ipAndPort)){
			String[] ipPort = ipAndPort.split(":");
			human.setIpCountries(iPSearchService.searchCountryByIp(ipPort[0]));
		}
		human.setLevel(entity.getLevel());
		human.setCoupon(entity.getCoupon());
		human.setGold(entity.getGold());
		human.setDiamond(entity.getDiamond());
		human.setSceneId(entity.getSceneId());
		human.setLastLoginTime(entity.getLastLoginTime());
		long lastLogoutTime = entity.getLastLogoutTime();
		human.setLastLogoutTime(lastLogoutTime);
		human.setTotalMinute(entity.getTotalMinute());

		human.setCharm(entity.getCharm());
		human.setCurExp(entity.getCurExp());
		human.setFriendId(entity.getFriendId());
		human.setRequestFriendIds(entity.getRequestFriendIds());
		human.resetMaxExp();
		human.setRegularTime(entity.getRegularTime());
		human.setIsPay(entity.getIsPay());
		human.setDoubleExpEndTime(entity.getDoubleExpEndTime());
		String gameview = entity.getGameview();
		if(gameview != null && !gameview.trim().equals("")){
			human.setGameview(JSON.parseObject(gameview, HashSet.class));
		}
		human.setNewguide(entity.getNewguide() == null?"":entity.getNewguide());
		
		String addfriendIds = entity.getAddfriendIds();
		
		if(TimeUtils.isSameDay(lastLogoutTime, Globals.getTimeService().now()) && addfriendIds != null && !addfriendIds.trim().equals("")){
			human.setAddfriendIds(JSON.parseObject(addfriendIds,new TypeReference<HashMap<Long,String>>() {}));
			
		}
		
		String receivecode = entity.getReceivecode();
		if(receivecode != null && !receivecode.trim().equals("")){
			human.setReceivecode(JSON.parseObject(receivecode,new TypeReference<HashSet<String>>() {}));
			
		}
		human.setWatchTime(entity.getWatchTime());
		human.setWatchNum(entity.getWatchNum());
		human.setAge(entity.getAge());
		
		human.setSlotRotate(entity.getSlotRotate());
		human.setSlotSingleWin(entity.getSlotSingleWin());
		human.setSlotWin(entity.getSlotWin());
		human.setSlotWinNum(entity.getSlotWinNum());
		human.setClubId(entity.getClubId());
		Long signTs = entity.getClubSignTs();
		human.setClubSignTs(signTs==null?0:signTs);
		Long donateTs = entity.getClubDonateTs();
		human.setClubDonateTs(donateTs==null?0:donateTs);
		String snginfo = entity.getSnginfo();
		if(!StringUtils.isEmpty(snginfo)){
			human.setSnginfos(JSON.parseObject(snginfo,new TypeReference<ArrayList<SngInfo>>() {}));
		}
		human.setClientVersion(entity.getClientVersion());
		human.setBazooRoom(entity.getBazooRoom());
		human.setBazooGold(entity.getBazooGold());
		human.setBazooAgentDisplay(entity.getBazooAgentDisplay());
		human.setBazooRobotDisplay(entity.getBazooRobotDisplay());
		human.setBazooNewGuyProcess(entity.getBazooNewGuyProcess());
		int slotId = entity.getSlotId();
		String roomId = entity.getSlotRoomId();
		
		/*human.setSlotRoomId(roomId);
		human.getHumanSlotManager().setSlotId(slotId);*/
		
		Globals.getSlotRoomService().outPlayerLogin(human.getPlayer(),slotId,roomId);
		
		logger.info("玩家id["+human.getCharId()+"]玩家名["+human.getName()+"]玩家加载成功");
	}

	/**
	 * <pre>
	 * 异步加载玩家角色列表之后调用此方法
	 * 此方法在主线程中调用
	 * <pre>
	 *
	 */
	public void humanLogin(Human human)
	{	
		Player player = human.getPlayer();
		boolean isFirstLogin = human.getLastLoginTime() == 0 ? true : false;
		
		human.onLogin(isFirstLogin);
		
		int lastSceneId = human.getSceneId();
		
		if (lastSceneId <= 0) lastSceneId = Globals.getSceneService().getFirstCityId();
		// 那么进入玩家上一次所在场景
		player.setTargetSceneId(lastSceneId);
	
		player.setState(PlayerState.incoming);
		player.setState(PlayerState.entering);
		player.setState(PlayerState.gaming);
		
		// 激活此角色
		human.getLifeCycle().activate();
		
		human.setModified();
		
		logger.info("player " + player.getPassportName() + " checkAfterRoleLoad");
		// 因为涉及的到数据量可能较大,在加载完成后执行进入游戏的预处理,将相关的对象设置为Live
		human.checkAfterRoleLoad();
		
		// 数据加载完成之后初始化
		logger.info("player " + player.getPassportName() + " onInit");
		human.onInit(Globals.getTemplateService(),Globals.getLangService());

		human.afterLogin();
		
		if(player.getPlayerRoleEnum() == PlayerRoleEnum.ROBOT)
			Globals.getRobotService().robotJoin(player);
		
		human.resetChange();
		
//		//更新用户基本信息缓存
//		syncPlayerCache(human);
//		
		//更新用户数据进入排行版
		//updateHumanRank(human);
		
		// 通知消息
		noticeHuman(human);
		
		//登陆成就统计
//		human.getHumanAchievementManager().updateLogin();
//		human.getHumanAchievementManager().updateRank();
		
		//角色登陆日志
		//Globals.getLogService().sendDataOverviewLog(human, LogReasons.DataOverviewLogReason.DataOverview_type1, LogReasons.DataOverviewLogReason.DataOverview_type1.getReasonText(), 1);
		
		LogReasons.PlayerLoginLogReason reason = LogReasons.PlayerLoginLogReason.PLAYERLOGINLOG1;
		
		IPSearchService iPSearchService = Globals.getiPSearchService();
		Globals.getLogService().sendPlayerLoginLog(human, reason, reason.getReasonText(), player.getChannelType().getIndex(), player.getDeviceModel(), player.getClientVersion(),player.getCountries(),iPSearchService.searchCountryByIp(player.getIPAddrWhitoutPort()));
		
		LogReasons.PlayerKeepLogReason reasonkeep = LogReasons.PlayerKeepLogReason.PlayerKeep2;
		long createTime = human.getCreateTime();
		int day = TimeUtils.compareTime(TimeUtils.getTodayBeginTime(createTime), Globals.getTimeService().now(), TimeUtils.DAY);
		Globals.getLogService().sendPlayerKeepLog(human, reasonkeep, 
				reasonkeep.getReasonText(),day,player.getCountries(),
				iPSearchService.searchCountryByIp(player.getIPAddrWhitoutPort()),player.getChannelType().getIndex(),player.getDeviceModel(),player.getClientVersion());
	}
	
	
	/**
	 * 更新排行榜
	 * @param human
	 */
	private void updateHumanRank(Human human)
	{
		//RankLogic.getInstance().updateRank(RankTypeEnum.DIAMOND, human.getPassportId(), human.getDiamond()+human.getCoupon());
		RankLogic.getInstance().updateRank(RankTypeEnum.CHARM, human.getPassportId(), human.getCharm());
		RankLogic.getInstance().updateRank(RankTypeEnum.GOLD, human.getPassportId(), human.getGold());
		RankLogic.getInstance().updateRank(RankTypeEnum.TEXAS_WEEK_WIN, human.getPassportId(), human.getHumanTexasManager().getHumanTexas().getWeekWinCoins());
		RankLogic.getInstance().updateRank(RankTypeEnum.TEXAS_SNG_WEEK, human.getPassportId(), human.getHumanTexasManager().getHumanTexasSNG().getWeekScore());
		
	}

	/**
	 * 发送初始的消息接口
	 */
	private void noticeHuman(Human human)
	{		
		HumanInfoData data = Globals.getHumanService().getHumanInfo(human);
		
		
		if(human.getLastLogoutTime() == 0){
//			data.setGold(0);
		}
		//新手大礼包当天只显示一次的问题
		
		//发送角色详细信息
		human.sendMessage(new GCHumanDetailInfo(data));
		
		//发送德州数据
//		human.sendMessage(human.getHumanTexasManager().buildHumanTexasInfoData());
		//发送德州sng数据
		//human.sendMessage(human.getHumanTexasManager().buildHumanTexasSNGInfoData());
		//发送背包数据
//		human.sendMessage(human.getHumanBagManager().buildHumanBagInfoData());
		//发送月卡数据
//		human.sendMessage(human.getHumanMonthCardManager().buildHumanMonthCardInfoData());
		//发送周卡数据
//		human.sendMessage(human.getHumanWeekCardManager().buildHumanWeekCardInfoData());
		//发送签到数据
//		human.sendMessage(human.getHumanWeekSignInManager().buildHumanWeekSignInInfoData());
		//发送misc
		human.sendMessage(human.getHumanMiscManager().buildGCMiscInfoData());
		//发送 fb misc
		human.sendMessage(human.getHumanMiscManager().buildGCMiscFBInfoData());
		//发送vip
	    //human.sendMessage(human.getHumanVipManager().buildHumanVipInfoData());
		
		//发送VIP新系统数据
//		human.sendMessage(human.getHumanVipNewManager().buildVipData());
		
		//发送邮件数据
		human.sendMessage(human.getHumanMailManager().buildHumanMailInfoData(MailTypeEnum.SYSTEM));
		human.sendMessage(human.getHumanMailManager().buildHumanMailInfoData(MailTypeEnum.PLAYER));
		human.sendMessage(human.getHumanMailManager().buildHumanMailInfoData(MailTypeEnum.PLAYER_GIFT));
		human.sendMessage(human.getHumanMailManager().buildHumanMailInfoData(MailTypeEnum.COMPENSATION));
		//发送好友礼物
		human.sendMessage(RelationLogic.getInstance().buildFriendGiftListMsg(human));
		//发送好友请求
//		human.sendMessage(RelationLogic.getInstance().buildFriendRequestListMsg(human));
		
		//发送订单数据
//		human.sendMessage(human.getHumanRechargeManager().buildOrderInfoDataList());
		//发送任务
//		human.sendMessage(human.getHumanTaskManager().buildTaskInfoData());
//		human.sendMessage(human.getHumanTaskManager().buildTaskData());
		
		
		//发送活动数据
//		human.sendMessage(Globals.getActivityService().buildActivityList(human));
		//发送用户活动领取数据 显示前端   go-clam 自个的
//		human.sendMessage(human.getHumanActivityManager().buildGCHumanActivityRewardDataList());
		//发送用户活动进度数据  显示前端的 go-clam 整个条的
//		human.sendMessage(human.getHumanActivityManager().buildGCHunamnProgress());
		//用户登录的时候发送一下 功能
		Globals.getFunctionService().getFunction(human.getPlayer());
		
		
		//发送幸运转盘数据
//		human.sendMessage(human.getHumanLuckySpinManager().buildGCLuckySpinInfoData());
		
		/*if(outTime == 0 || !TimeUtils.isSameDay(outTime, Globals.getTimeService().now()) ){
			
		}*/
		
		//存钱罐
//		human.sendMessage(human.getHumanTreasuryManager().sendTreasury());
		
		
		//无双吹牛  发送签到状态等等 GCBazooHallStatus  主动推送 前端收不到  所以 就不在这里主动推了
//		human.sendMessage(human.getHumanBazooManager().getStatus());
		
		human.checkBeforeRoleEnter();
		//Player player = human.getPlayer();
		//Globals.getOnlinePlayerService().putPlayer(human.getName(), player);
		logger.info("玩家id["+human.getCharId()+"]玩家名["+human.getName()+"]初始消息发送成功");
	}
}
