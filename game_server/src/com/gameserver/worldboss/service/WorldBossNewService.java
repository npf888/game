package com.gameserver.worldboss.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.Loggers;
import com.core.schedule.LLScheduleEnum;
import com.core.util.TimeUtils;
import com.gameserver.common.Globals;
import com.gameserver.common.msg.GCMessage;
import com.gameserver.common.unit.GameUnitList;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.player.Player;
import com.gameserver.slot.Slot;
import com.gameserver.slot.SlotService;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.worldboss.data.AttackRankData;
import com.gameserver.worldboss.data.BossInfoData;
import com.gameserver.worldboss.data.LastWinHumanData;
import com.gameserver.worldboss.msg.GCBeforeStart;
import com.gameserver.worldboss.msg.GCBossStartEndInfo;
import com.gameserver.worldboss.msg.GCGetBossInfo;
import com.gameserver.worldboss.msg.GCGetRankInfo;
import com.gameserver.worldboss.msg.GCRefreshBossInfo;
import com.gameserver.worldboss.msg.GCReturnBlood;
import com.gameserver.worldboss.msg.GCSelfAttackBloodInfo;
import com.gameserver.worldboss.pojo.Boss;
import com.gameserver.worldboss.schedule.ScheduleBossNotice;
import com.gameserver.worldboss.schedule.ScheduleBossNoticeBefore;
import com.gameserver.worldboss.schedule.ScheduleBossStart;
import com.gameserver.worldboss.template.BossList;
import com.gameserver.worldboss.template.BossPropertyTemplate;
import com.gameserver.worldboss.template.BossTemplate;
import com.gameserver.worldboss.vo.AttackRankVO;

/**
 * 世界boss
 * @author JavaServer
 *
 */
public class WorldBossNewService implements InitializeRequired {

	private Logger logger = Loggers.WORLDBOSS;
	/**
	 * 开启 和 关闭
	 */
	public static final int ON = 1;
	public static final int OFF = 0;
	//阻止 配置文件 给 onoff 赋值 ，0：阻止 ，1：不阻止(默认)
	private int onoff = 0;
	private int stopBossTemplateVoluation = 0;
	/**
	 * 模板数据
	 */
	//每条数据 代表 定时 开启 一个世界boss
	List<BossTemplate> bossTemplateList  = new ArrayList<BossTemplate>();
	//boss 的种类 （就是 几个 大boss）
	List<BossPropertyTemplate> bossPropertyTemplateList  = new ArrayList<BossPropertyTemplate>();
	//前100名 会有宝箱(攻击死boss的奖励-配表)
	List<BossList> bossList  = new ArrayList<BossList>();
	//前100名 会有宝箱(攻击死boss的奖励-配表)
	List<BossList> nobossList  = new ArrayList<BossList>();
	
	
	/**
	 * 动态数据
	 */
	//面板 打开的用户 （全部是打开的 关闭的就删除了）
	Map<Long,Integer> openPanel = new HashMap<Long,Integer>();
	//上次的boss
	Boss boss = new Boss();
	//排行榜
	private List<AttackRankVO> attackRankVOList = new ArrayList<AttackRankVO>();
	
	
	@Override
	public void init() {}
	
	/*@Override
	public void init() {
		
		Map<Integer, BossTemplate> rBossTemplateMap = Globals.getTemplateService().getAll(BossTemplate.class);
		for(BossTemplate bt: rBossTemplateMap.values()){
			bossTemplateList.add(bt);
		}
		Map<Integer, BossPropertyTemplate> rBossPropertyTemplateMap = Globals.getTemplateService().getAll(BossPropertyTemplate.class);
		for(BossPropertyTemplate bt: rBossPropertyTemplateMap.values()){
			bossPropertyTemplateList.add(bt);
		}
		
		Map<Integer, BossList> rBossListMap = Globals.getTemplateService().getAll(BossList.class);
		for(BossList bt: rBossListMap.values()){
			if(bt.getRankListId()==1){
				bossList.add(bt);
			}else if(bt.getRankListId()==2){
				nobossList.add(bt);
			}
		}
		
		Collections.sort(bossTemplateList,new Comparator<BossTemplate>(){
			@Override
			public int compare(BossTemplate bt1, BossTemplate bt2) {
				String[] hourMin1 = bt1.getStarttime().split(":");
				int hour1 = Integer.valueOf(hourMin1[0]).intValue();
				int min1 = Integer.valueOf(hourMin1[1]).intValue();
				String[] hourMin2 = bt2.getStarttime().split(":");
				int hour2 = Integer.valueOf(hourMin2[0]).intValue();
				int min2 = Integer.valueOf(hourMin2[1]).intValue();
				if(hour1>hour2){
					return 1;
				}if(hour1 == hour2 && min1 >min2){
					return 1;
				}
				return -1;
			}
			
		});
		//初始化boss
		boss.init(bossPropertyTemplateList, bossTemplateList);
		
		
		initHowTOStartWorldBoss();
		
	}
*/
	
	/**
	 * 开启定时器
	 */
	public void initHowTOStartWorldBoss(){
		for(int i=0;i<bossTemplateList.size();i++){
			BossTemplate nbossTemplate= bossTemplateList.get(i);
			if(stopBossTemplateVoluation == 0){
				onoff=nbossTemplate.getOnoff();
			}else if(onoff == WorldBossNewService.OFF){
				continue;
			}
			String startTime = nbossTemplate.getStarttime();
			String[] hourMin = startTime.split(":");
			String hour = hourMin[0];
			String min = hourMin[1];
			
			Date now = new Date();
			//设置 提醒个定时器
			Calendar calendarBefore = Calendar.getInstance();
			calendarBefore.setTime(new Date());
			calendarBefore.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
			calendarBefore.set(Calendar.MINUTE,Integer.valueOf(min).intValue()-30);
			calendarBefore.set(Calendar.SECOND, 0);
			//如果 需要的 时间 已经过了，就设置 下一次
			if(calendarBefore.getTime().getTime() <= now.getTime()){
				calendarBefore.add(Calendar.DATE, 1);
			}
			logger.info("++++++比赛前 30 分钟 通知时间 设置："+TimeUtils.dateToTimeString(calendarBefore.getTime()));
			Globals.getScheduleService().scheduleOnce(new ScheduleBossNoticeBefore(calendarBefore,hour,min), LLScheduleEnum.WORLD_BOSS_NOTICE_BEFORE,calendarBefore.getTime());
			//设置 提醒个定时器
			Calendar calendarRemind = Calendar.getInstance();
			calendarRemind.setTime(new Date());
			calendarRemind.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
			calendarRemind.set(Calendar.MINUTE,Integer.valueOf(min).intValue()-5);
			calendarRemind.set(Calendar.SECOND, 0);
			//如果 需要的 时间 已经过了，就设置 下一次
			if(calendarRemind.getTime().getTime() <= now.getTime()){
				calendarRemind.add(Calendar.DATE, 1);
			}
			
			logger.info("++++++跑马灯通知时间 设置："+TimeUtils.dateToTimeString(calendarRemind.getTime()));
			//跑马灯
			Globals.getScheduleService().scheduleOnce(new ScheduleBossNotice(calendarRemind,hour,min), LLScheduleEnum.WORLD_BOSS_NOTICE_START,calendarRemind.getTime());
			
			//每天 几点开始 根据配置
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
			calendar.set(Calendar.MINUTE,Integer.valueOf(min));
			calendar.set(Calendar.SECOND, 0);
			if(calendar.getTime().getTime() <= now.getTime()){
				calendar.add(Calendar.DATE, 1);
			}
			
			logger.info("++++++游戏开始时间设置通知时间 设置："+TimeUtils.dateToTimeString(calendar.getTime()));
			//开始游戏
			Globals.getScheduleService().scheduleOnce(new ScheduleBossStart(nbossTemplate,calendar,hour,min), LLScheduleEnum.WORLD_BOSS_START,calendar.getTime());
		}
		
	}
	
	
	/**
	 * 比赛开始前30分钟
	 */
	public void bossBeforeStart() {
		GCBeforeStart gCBeforeStart = new GCBeforeStart();
		gCBeforeStart.setBossType(boss.getNextBossId());
		gCBeforeStart.setLeftTime(30*60);
		sendMessageToAllPeople(gCBeforeStart);
	}
	public void bossBeforeStart(Player player) {
		long leftTime = getNextLeftTime();
		long thiredMin = 30*60;
		if(boss.getStatus() == Boss.overdue){
			if(leftTime<=thiredMin){
				GCBeforeStart gCBeforeStart = new GCBeforeStart();
				gCBeforeStart.setBossType(boss.getNextBossId());
				gCBeforeStart.setLeftTime(Long.valueOf(leftTime).intValue());
				player.sendMessage(gCBeforeStart);
			}
		}else if(boss.getStatus() == Boss.future){
			if(leftTime<=thiredMin){
				GCBeforeStart gCBeforeStart = new GCBeforeStart();
				gCBeforeStart.setBossType(boss.getType());
				gCBeforeStart.setLeftTime(Long.valueOf(leftTime).intValue());
				player.sendMessage(gCBeforeStart);
			}
		}else if(boss.getStatus() == Boss.running){
			Date endTime = boss.getEndTime();
			long now = Globals.getTimeService().now();
			if(now< endTime.getTime()){
				GCBeforeStart gCBeforeStart = new GCBeforeStart();
				gCBeforeStart.setBossType(boss.getType());
				gCBeforeStart.setLeftTime(Long.valueOf((endTime.getTime()-now)/1000).intValue());
				player.sendMessage(gCBeforeStart);
			}
		}
	}
	
	/**
	 *
	 * 开启世界boss
	 ********************************************************************************************
	 */
	public void startWorldBoss(BossTemplate bossTemplate){
		//如果当前 boss 是 活的 就 不要创建
		if(boss.getStatus() == Boss.running){
			return;
		}else if(boss.getStatus() == Boss.future){
			boss.setStatus(Boss.running);
			boss.setModified();
		}else if(boss.getStatus() == Boss.overdue){
			//更新老的 boss
			boss.setStatus(Boss.overdue);
			if(boss.getLastBoss() != null){
				boss.setLastBossId(Long.valueOf(boss.getLastBoss().getBossId()).intValue());
			}
			//初始化 技能开始时间
			
			boss.setModified();
			//创建 新的boss给 然后赋值给 boss
			Boss newBoss = new Boss();
			newBoss.BuildBoss(boss,bossTemplate,bossPropertyTemplateList);
			newBoss.setModified();
			boss=newBoss;
		}
		
		refreshStart();
	}
	private void refreshStart() {
		GCBossStartEndInfo gCBossStartEndInfo = new GCBossStartEndInfo();
		gCBossStartEndInfo.setStartEndRuning(0);
		gCBossStartEndInfo.setBeKilled(0);
		gCBossStartEndInfo.setCurBossLeftTime(boss.getContinueTime()*60);
		gCBossStartEndInfo.setBossType(boss.getType());
		gCBossStartEndInfo.setNextBossLeftTime(0);
		sendMessageToOpenPanl(gCBossStartEndInfo);
	}
	
	private void sendMessageToOpenPanl(GCMessage gCMessage){
			//0是打开
		GameUnitList<Player> players = Globals.getOnlinePlayerService().getOnlinePlayers();
		if(players ==null || players.size() == 0){
			return;
		}
		for(Player player:players){
			player.sendMessage(gCMessage);
		}
	}
	/**
	 * 主动请求 进入老虎机 刷新 30分钟的那个
	 */
	public void cgGCBeforeStart(Player player){
		if(boss != null){
			GCBeforeStart gCBeforeStart = new GCBeforeStart();
			gCBeforeStart.setBossType(boss.getType());
			Date starttime = boss.getStartTime();
			long now = Globals.getTimeService().now();
			if(now < starttime.getTime()){
				long lefttime = starttime.getTime()-now;
				gCBeforeStart.setLeftTime(Long.valueOf(lefttime/1000).intValue());
				player.sendMessage(gCBeforeStart);
			}
		}
		
	}
	private long getNextLeftTime(){
		int index=0;
		long nowLong = Globals.getTimeService().now();
		for(int i=0;i<bossTemplateList.size();i++){
				String now = TimeUtils.formatYMDTime(nowLong);
				long bossLong = TimeUtils.stringToTime(now+" "+bossTemplateList.get(i).getStarttime()+":00").getTime();
				if(nowLong < bossLong){
					index=i;
					break;
				}
		}
		BossTemplate bossTemplate = bossTemplateList.get(index);
		String now = TimeUtils.formatYMDTime(nowLong);
		long bossLong = TimeUtils.stringToTime(now+" "+bossTemplate.getStarttime()+":00").getTime();
		long leftTime = 0;
		if(bossLong<nowLong){
			leftTime=(bossLong+24*60*60*1000-nowLong)/1000;
		}else{
			leftTime=(bossLong-nowLong)/1000;
		}
		return leftTime;
	}
	
	private BossTemplate getBossTemplate(){
		for(BossTemplate bossTemplate:bossTemplateList){
			if(boss.getBossTemplateId() == bossTemplate.getId()){
				return bossTemplate;
			}
		}
		return null;
	}
	
	/**
	 * 时间到了 boss 没有被 干死 就结束了
	 * 如果 被干死了  就不做处理
	 * @param bossTemplate 
	 */
	public void endBoss(){
		if(boss.getStatus() == Boss.running){
			
			/**
			 * boss日志
			 */
			Globals.getLogService().sendWorldBossLog(null, LogReasons.WorldBossLogReason.WorldBoss, "", 
					boss.getType(), TimeUtils.dateToTimeString(boss.getStartTime()), 2, -1, 0l,boss.getBossId());
			
			boss.setStatus(Boss.overdue);
			boss.setNextBossId(boss.getBossPropertyTemplate(boss.getBossTemplateId(),bossPropertyTemplateList).getId());
			boss.setModified();
			refreshEnd();
			//分发奖励   用的是 RewardNum2
			boss.getBossBroadcast().broadcast(boss.getRewardNum2(),boss);
			//击杀富豪榜（另一种奖励）
			boss.getBossBroadcast().broadcast1(attackRankVOList, bossList);
			attackRankVOList.clear();
		}
	}
	private void refreshEnd() {
		GCBossStartEndInfo gCBossStartEndInfo = new GCBossStartEndInfo();
		gCBossStartEndInfo.setStartEndRuning(1);
		gCBossStartEndInfo.setBeKilled(0);
		gCBossStartEndInfo.setCurBossLeftTime(0);
		gCBossStartEndInfo.setBossType(boss.getType());
		gCBossStartEndInfo.setNextBossLeftTime(getNextLeftTime());
		sendMessageToOpenPanl(gCBossStartEndInfo);
	}
	
	/**
	 *  1.（airtime）秒内，所有wild连线伤害降低百分之（）
	 *	2.恢复自身最大血量的百分之（）
	 *	3.（airtime）秒内，收到伤害降低百分之（）
	 *	4.（airtime）秒内，有（）概率免疫伤害。
	 *
	 *  回血
	 * @param calendarRollSkill 
	 */
	public void  bossBloodReturning(Calendar calendarRollSkill){
		if(boss.getStatus() != Boss.running){
			return;
		}
		
		
		logger.info("回血 执行中 ------------------execute:"+boss);
		//技能开始后的持续时间 秒
		boss.getBossBloodReturningUtils().bossBloodReturning(boss, calendarRollSkill, openPanel);
		GCReturnBlood gCReturnBlood = new GCReturnBlood();
		int skillTime = 0;
		//2:一次性技能的
		if(boss.getType() == 2){
			gCReturnBlood.setSkillType(1);
		//延时技能的
		}else{
			long now = Globals.getTimeService().now();
			boss.setSkill(0);
			for(BossTemplate bpt:bossTemplateList){
				if(bpt.getId() == boss.getBossTemplateId()){
					skillTime=bpt.getSkillstart();
				}
			}
			boss.setSkillEnd(now/1000+skillTime);
			gCReturnBlood.setSkillType(0);
		}
		gCReturnBlood.setSkill(boss.getSkill());
		gCReturnBlood.setSkillTime(skillTime);
		sendMessageToAllPeople(gCReturnBlood);
		
		
		/**
		 * boss日志
		 */
		Globals.getLogService().sendWorldBossLog(null, LogReasons.WorldBossLogReason.WorldBoss, "", 
				boss.getType(),null, 0, 1, 0l,boss.getBossId());
	}
	
	/**
	 * 结束回血
	 */
	public void bossBloodReturningWithAirtimeEND(){
		boss.getBossBloodReturningUtils().bossBloodReturningWithAirtimeEND(boss);
		GCReturnBlood gCReturnBlood = new GCReturnBlood();
		if(boss.getType() == 2){
			gCReturnBlood.setSkillType(1);
		}else{
			boss.setSkill(1);
			gCReturnBlood.setSkillType(0);
		}
		gCReturnBlood.setSkill(boss.getSkill());
//		gCReturnBlood.setSkillTime(boss.getAirtime());
		long now = Globals.getTimeService().now();
		gCReturnBlood.setSkillTime(Long.valueOf(boss.getSkillEnd()-now/1000).intValue());
		sendMessageToAllPeople(gCReturnBlood);
		
		/**
		 * boss日志
		 */
		Globals.getLogService().sendWorldBossLog(null, LogReasons.WorldBossLogReason.WorldBoss, "", 
				boss.getType(),null, 0, 2, 0l,boss.getBossId());
	}
	
	/**
	 * 主动请求回血的情况
	 */
	
	public GCReturnBlood returnBlood() {
		logger.info("主动请求 --回血------------------execute:"+boss);
		//技能开始后的持续时间 秒
		GCReturnBlood gCReturnBlood = new GCReturnBlood();
		if(boss.getType() == 2){
			gCReturnBlood.setSkillType(1);
		}else{
			gCReturnBlood.setSkillType(0);
		}
		gCReturnBlood.setSkill(boss.getSkill());
		long now = Globals.getTimeService().now();
		gCReturnBlood.setSkillTime(Long.valueOf(boss.getSkillEnd()-now/1000).intValue());
		return gCReturnBlood;
		
	}
	/**
	 * 下面
	 * 用户击杀
	 * @param list 
	 * 
	 */
	
	//击杀
	public void  attackBoss(Human human,long gold,int curBet,Slot slot, List<SlotConnectInfo> list,List<Integer> elementList,SlotService slotService,int slotType){
		if(boss.getStatus() != Boss.running){
			return;
		}
		boolean bossStatus = false;
		if(gold >0){
			bossStatus = human.getHumanAttackBossManager().attackBoss(boss, gold, curBet, slot,  getBossTemplate(), attackRankVOList,list,elementList,slotService,slotType,human);
		
			//给自己发送
			GCSelfAttackBloodInfo gCSelfAttackBloodInfo = new GCSelfAttackBloodInfo();
			gCSelfAttackBloodInfo.setSelfAttackBlood(Long.valueOf(human.getHumanAttackBossManager().getCurAttackBlood().get(0)).intValue());
			gCSelfAttackBloodInfo.setWinType(human.getHumanAttackBossManager().getAttackType());
			human.getHumanAttackBossManager().getCurAttackBlood().clear();
			human.getPlayer().sendMessage(gCSelfAttackBloodInfo);
		}
		//给所有人 发送
		refreshAll();
		//boss的血没有了
		if(bossStatus){
			
			/**
			 * boss日志
			 */
			Globals.getLogService().sendWorldBossLog(null, LogReasons.WorldBossLogReason.WorldBoss, "", 
					boss.getType(), TimeUtils.dateToTimeString(boss.getStartTime()), 2, -1, 0l,boss.getBossId());
			
			boss.setStatus(Boss.overdue);
			boss.setBeKilled(1);
			boss.setNextBossId(boss.getBossPropertyTemplate(boss.getBossTemplateId(),bossPropertyTemplateList).getId());
			boss.setModified();
			refreshKill();
			//跑马灯告诉所有人 谁杀死了 boss
			tellEveryBodyWhoKillBoss(human);
			//分发奖励
			boss.getBossBroadcast().broadcast(boss.getRewardNum1(),boss);
			//击杀富豪榜（另一种奖励）
			boss.getBossBroadcast().broadcast1(attackRankVOList, bossList);
		}
	}
	
	private void tellEveryBodyWhoKillBoss(Human human) {
		boss.getBossBroadcast().giveTHeHumanThing(getBossTemplate(), human);
		//boss 被谁杀死了 的 跑马灯
		Globals.getNoticeService().broadcastNoticeMulti(100465,boss.getNameId()+"",human.getName());
		String first = "";
		String second = "";
		String third = "";
		if(attackRankVOList.size()>3){
			for(int i=0;i<3;i++){
				AttackRankVO attackRankVO = attackRankVOList.get(i);
				if(i==0){
					first=attackRankVO.getName();
				}else if(i==1){
					second=attackRankVO.getName();
				}else if(i==2){
					third=attackRankVO.getName();
				}
			}
		}else{
			for(int i=0;i<attackRankVOList.size();i++){
				AttackRankVO attackRankVO = attackRankVOList.get(i);
				if(i==0){
					first=attackRankVO.getName();
				}else if(i==1){
					second=attackRankVO.getName();
				}else if(i==2){
					third=attackRankVO.getName();
				}
			}
		}
		//前三名 的跑马灯
		Globals.getNoticeService().broadcastNoticeMulti(100466, "1",first,"2",second,"3",third);
		
	}


	private void refreshKill() {
		GCBossStartEndInfo gCBossStartEndInfo = new GCBossStartEndInfo();
		gCBossStartEndInfo.setStartEndRuning(1);
		gCBossStartEndInfo.setCurBossLeftTime(0);
		gCBossStartEndInfo.setBeKilled(1);
		gCBossStartEndInfo.setBossType(boss.getType());
		gCBossStartEndInfo.setNextBossLeftTime(getNextLeftTime());
		sendMessageToAllPeople(gCBossStartEndInfo);
	}


	/**
	 * 刷新用户 和 boss
	 */
	private void refreshAll() {
			GameUnitList<Player> players = Globals.getOnlinePlayerService().getOnlinePlayers();
			for(Player player:players){
				if(player == null){
					continue;
				}
				player.sendMessage(getAttackRank(player));
				player.sendMessage(boss.getBossBloodReturningUtils().refreshBoss(boss,player,null));
			}
	}
	/**
	 * 发送消息 给所有人
	 */
	private void sendMessageToAllPeople(GCMessage message) {
		GameUnitList<Player> players = Globals.getOnlinePlayerService().getOnlinePlayers();
		for(Player player:players){
			player.sendMessage(message);
		}
		
	}


	/**
	 * *************************************************************************************************************************************
	 * *************************************************************************************************************************************
	 * *************************************************************************************************************************************
	 * 返回给前端的接口
	 * 
	 * 
	 */
	
	/**
	 * 伤害排行榜：依照当前玩家对boss造成的伤害值及百分比排行，显示前十名及自己的排名。 
	 */
	public GCGetRankInfo getAttackRank(Player player){
		List<AttackRankData> AttackRankDataList = new ArrayList<AttackRankData>();
		for(int i=0;i<attackRankVOList.size();i++){
			if(i>9){
				break;
			}
			AttackRankVO arVO = attackRankVOList.get(i);
			AttackRankData AttackRankData = new AttackRankData();
			AttackRankData.setAttackTotalBlood(arVO.getAttackTotalBlood());
			AttackRankData.setBossId(arVO.getBossId());
			AttackRankData.setUserId(arVO.getUserId());
			AttackRankData.setName(arVO.getName());
			AttackRankData.setAttackRate(arVO.getAttackRate());
			AttackRankData.setVipAdditionRate(arVO.getVipAdditionRate());
			AttackRankData.setSortIndex(arVO.getCurIndex());
			AttackRankDataList.add(AttackRankData);
		}
		
		
		AttackRankData[] attackRankData = new AttackRankData[AttackRankDataList.size()];
		for(int i=0;i<AttackRankDataList.size();i++){
			attackRankData[i]=AttackRankDataList.get(i);
		}
		GCGetRankInfo  gCGetRankInfo = new GCGetRankInfo();
		gCGetRankInfo.setAttackRankData(attackRankData);
		gCGetRankInfo.setCurIndex(0);//如果排行榜里没有用户就是0
		for(AttackRankVO AttackRankVO:attackRankVOList){
			if(AttackRankVO.getUserId() == player.getHuman().getPassportId()){
				gCGetRankInfo.setCurIndex(AttackRankVO.getCurIndex());
			}
		}
		return gCGetRankInfo;
	}
	
	
	/**
	 * 打开 或者关闭 世界boss
	 */
	public void openOrCloseWorldBoss(Human human,Integer type){
		if(type.intValue() == 0){//打开
			openPanel.put(human.getPassportId(), type);
		}else{//关闭
			if(openPanel.containsKey(human.getPassportId())){
				openPanel.remove(human.getPassportId());
			}
		}
	}
	
	/**
	 * 
	 * 1、上次排名第一的用户
	 *  返回两个boss
	 * 
	 */
	public GCGetBossInfo getLastWinPeopleAndTwoBoss(){
			List<BossInfoData> BossInfoDataList = new ArrayList<BossInfoData>();
			//当前
			BossInfoData bossInfoData = new BossInfoData();
			BossInfoDataList.add(bossInfoData.getSelf(boss));
			//上一个 没有拉到
			BossInfoData lastBossInfoData = new BossInfoData();
			BossInfoDataList.add(lastBossInfoData.getLastSelf(boss.getLastBoss()));
			
			
			LastWinHumanData[] lastWinHumanDataArr =new LastWinHumanData[1];
			lastWinHumanDataArr[0]=boss.getLastWinHumanData();
			
			BossInfoData[] bossInfoDataArr = new BossInfoData[BossInfoDataList.size()];
			for(int i=0;i<bossInfoDataArr.length;i++){
				bossInfoDataArr[i]=BossInfoDataList.get(i);
			}
			GCGetBossInfo gCGetBossInfo = new GCGetBossInfo();
			gCGetBossInfo.setBossInfoData(bossInfoDataArr);
			gCGetBossInfo.setLastWinHumanData(lastWinHumanDataArr);
			return gCGetBossInfo;
	}


	
	

	/**
	 * 主动请求 进入老虎机
	 */
	public GCBossStartEndInfo cgRefreshTime(){
		
		GCBossStartEndInfo gCBossStartEndInfo = new GCBossStartEndInfo();
		if(boss.getStatus() == Boss.overdue){
			gCBossStartEndInfo.setStartEndRuning(1);
			gCBossStartEndInfo.setCurBossLeftTime(0);
			gCBossStartEndInfo.setBeKilled(boss.getBeKilled());
			gCBossStartEndInfo.setNextBossLeftTime(getNextLeftTime());
			gCBossStartEndInfo.setBossType(boss.getType());
			return gCBossStartEndInfo;
		}else if(boss.getStatus() == Boss.running){
			gCBossStartEndInfo.setStartEndRuning(2);
			long now = Globals.getTimeService().now();
			long leftTime = boss.getEndTime().getTime()-now;
			gCBossStartEndInfo.setCurBossLeftTime(leftTime/1000);
			gCBossStartEndInfo.setBeKilled(0);
			gCBossStartEndInfo.setBossType(boss.getType());
			gCBossStartEndInfo.setNextBossLeftTime(getNextLeftTime());
			return gCBossStartEndInfo;
		}else if(boss.getStatus() == Boss.future){
			gCBossStartEndInfo.setStartEndRuning(1);
			long now = Globals.getTimeService().now();
			long leftTime = boss.getEndTime().getTime()-now;
			gCBossStartEndInfo.setCurBossLeftTime(leftTime/1000);
			gCBossStartEndInfo.setBeKilled(0);
			gCBossStartEndInfo.setBossType(boss.getType());
			gCBossStartEndInfo.setNextBossLeftTime(getNextLeftTime());
			return gCBossStartEndInfo;
		}
		return gCBossStartEndInfo;
		
	}
	/**
	 * 进入老虎机 调用
	 */
	public void  cgRefreshBoss(Player player){
		if(boss.getStatus() != Boss.running){
			return;
		}
		boss.getBossBloodReturningUtils().refreshBoss(boss,player,null);
	}
	/**
	 * 进入老虎机 调用
	 */
	public void refreshTime(){
		if(boss.getStatus() != Boss.running){
			return;
		}
		cgRefreshTime();
		
	}


	/**
	 * 获取当前boss
	 * @return
	 */
	public Boss getCurBoss() {
		return this.boss;
	}


	public GCRefreshBossInfo refreshBoss(Player player) {
		if(boss == null){
			return null;
		}
		long startTime = boss.getSkillLeftTime();
		long now = Globals.getTimeService().now();
		long leftTime = now-startTime;
		if(leftTime >=boss.getAirtime()){
			leftTime=0;
		}
		return boss.getBossBloodReturningUtils().refreshBoss(boss, player,Long.valueOf(leftTime).intValue());
	}

	
	
	
	
	
	

	public void clearAttackRankVOList(){
		getAttackRankVOList().clear();
	}
	public List<AttackRankVO> getAttackRankVOList() {
		return attackRankVOList;
	}


	public void setAttackRankVOList(List<AttackRankVO> attackRankVOList) {
		this.attackRankVOList = attackRankVOList;
	}


	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
