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
import com.common.constants.Loggers;
import com.core.schedule.LLScheduleEnum;
import com.core.util.TimeUtils;
import com.gameserver.common.Globals;
import com.gameserver.common.msg.GCMessage;
import com.gameserver.common.unit.GameUnitList;
import com.gameserver.human.Human;
import com.gameserver.player.Player;
import com.gameserver.slot.Slot;
import com.gameserver.worldboss.data.AttackRankData;
import com.gameserver.worldboss.data.BossInfoData;
import com.gameserver.worldboss.data.LastWinHumanData;
import com.gameserver.worldboss.msg.GCBeforeStart;
import com.gameserver.worldboss.msg.GCBossStartEndInfo;
import com.gameserver.worldboss.msg.GCGetBossInfo;
import com.gameserver.worldboss.msg.GCGetRankInfo;
import com.gameserver.worldboss.msg.GCRefreshBossInfo;
import com.gameserver.worldboss.msg.GCReturnBlood;
import com.gameserver.worldboss.pojo.Boss;
import com.gameserver.worldboss.schedule.ScheduleBossNotice;
import com.gameserver.worldboss.schedule.ScheduleBossNoticeBefore;
import com.gameserver.worldboss.schedule.ScheduleBossStart;
import com.gameserver.worldboss.template.BossList;
import com.gameserver.worldboss.template.BossPropertyTemplate;
import com.gameserver.worldboss.template.BossTemplate;
import com.gameserver.worldboss.util.BossBloodReturningUtils;
import com.gameserver.worldboss.util.BossBroadcast;
import com.gameserver.worldboss.util.BossInitOld;
import com.gameserver.worldboss.util.BossUtils;
import com.gameserver.worldboss.vo.AttackRankVO;

/**
 * 世界boss
 * 旧 的service 不用了
 * @author JavaServer
 *
 */
public class WorldBossService implements InitializeRequired {

	private Logger logger = Loggers.WORLDBOSS;
	/**
	 * 初始化工具类
	 */
	BossBloodReturningUtils bossBloodReturningUtils = null;
	BossBroadcast bossBroadcast = null;
	BossUtils bossUtils = null;
	BossInitOld bossInit = null;
	/**
	 * 开启 和 关闭
	 */
	public static final int ON = 1;
	public static final int OFF = 0;
	//当前的 boss是否还活着
	private boolean bossStillAlive = false;
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
	//前100名 会有宝箱
	List<BossList> bossList  = new ArrayList<BossList>();
	
	
	/**
	 * 动态数据
	 */
	//面板 打开的用户 （全部是打开的 关闭的就删除了）
	Map<Long,Integer> openPanel = new HashMap<Long,Integer>();
	
	//上一次 被选中的世界boss
	BossPropertyTemplate lastBossPropertyTemplate = null; 
	Boss boss = null;
	//开启的世界boss 属于哪个时间段
	BossTemplate bossTemplate = null;
	
	//上次的boss
	Boss lastBoss = null;
	//上次赢得那个人的信息
	LastWinHumanData lastWinHumanData = null;
	
	
	//排行榜
	private List<AttackRankVO> attackRankVOList = new ArrayList<AttackRankVO>();
	//前十名
	List<AttackRankVO> tenAttackRankVOList = new ArrayList<AttackRankVO>();
	//当前用户所在 排行榜的 位置
	@Override
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
			bossList.add(bt);
		}

		bossBloodReturningUtils = new BossBloodReturningUtils();
		bossBroadcast = new BossBroadcast();
		bossUtils = new BossUtils();
		bossInit = new BossInitOld();
		
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
		bossStillAlive=bossInit.initBoss(boss, lastBoss, bossStillAlive, lastWinHumanData, bossPropertyTemplateList, lastBossPropertyTemplate, attackRankVOList,bossTemplateList);
		if(attackRankVOList.size() < 10){
			tenAttackRankVOList.addAll(attackRankVOList);
		}else{
			for(int i=0;i<10;i++){
				tenAttackRankVOList.add(attackRankVOList.get(i));
			}
		}
		boss = bossInit.getrBoss();
		lastBoss = bossInit.getRlastBoss();
		lastBossPropertyTemplate = bossInit.getrLastBossPropertyTemplate();
		lastWinHumanData=bossInit.getrLastWinHumanData();
		bossTemplate=bossInit.getNbossTemplate();
		
		
		initHowTOStartWorldBoss();
		
	}

	
	/**
	 * 开启定时器
	 */
	public void initHowTOStartWorldBoss(){
		attackRankVOList.clear();
		for(int i=0;i<bossTemplateList.size();i++){
			BossTemplate nbossTemplate= bossTemplateList.get(i);
			if(stopBossTemplateVoluation == 0){
				onoff=nbossTemplate.getOnoff();
			}else if(onoff == WorldBossService.OFF){
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
			calendarBefore.set(Calendar.MINUTE,Integer.valueOf(min).intValue()-6); 
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
		if(boss == null){
			//开启世界boss
			boss = new Boss();
			lastBossPropertyTemplate = boss.buildBoss(lastBoss,bossTemplate,bossPropertyTemplateList,lastBossPropertyTemplate);
		}
		GCBeforeStart gCBeforeStart = new GCBeforeStart();
		gCBeforeStart.setBossType(boss.getType());
		gCBeforeStart.setLeftTime(30);
		sendMessageToAllPeople(gCBeforeStart);
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
	/**
	 *
	 * 开启世界boss
	 ********************************************************************************************
	 */
	public void startWorldBoss(BossTemplate bossTemplate){
		//如果当前 boss 是 活的 就 不要创建
		if(bossStillAlive){
			return;
		}
		if(boss == null){
			//开启世界boss
			boss = new Boss();
			lastBossPropertyTemplate = boss.buildBoss(lastBoss,bossTemplate,bossPropertyTemplateList,lastBossPropertyTemplate);
		}
		bossStillAlive=true;
		this.bossTemplate=bossTemplate;
		refreshTime(0);
	}
	
	
	
	/**
	 * 时间到了 boss 没有被 干死 就结束了
	 * 如果 被干死了  就不做处理
	 * @param bossTemplate 
	 */
	public void endBoss(BossTemplate bossTemplate){
		if(bossStillAlive){
			bossStillAlive=false;
			boss.setNextBossId(boss.getBossPropertyTemplate(lastBossPropertyTemplate,bossPropertyTemplateList).getId());
			boss.setModified();
			refreshTime(1);
			lastBoss=boss;
			boss=null;
			attackRankVOList.clear();
			//分发奖励   用的是 RewardNum2
			bossBroadcast.broadcast(lastBoss.getRewardNum2(),lastBoss);
		}
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
		if(!bossStillAlive){
			return;
		}
		Loggers.WORLDBOSS.info("回血------------------execute:"+boss);
		//技能开始后的持续时间 秒
		bossBloodReturningUtils.bossBloodReturning(boss, calendarRollSkill, openPanel);
		GCReturnBlood gCReturnBlood = new GCReturnBlood();
		gCReturnBlood.setSkill(0);
		gCReturnBlood.setSkillTime(lastBossPropertyTemplate.getAirtime());
		sendMessageToAllPeople(gCReturnBlood);
	}
	
	/**
	 * 结束回血
	 */
	public void bossBloodReturningWithAirtimeEND(){
		bossBloodReturningUtils.bossBloodReturningWithAirtimeEND(boss);
		GCReturnBlood gCReturnBlood = new GCReturnBlood();
		gCReturnBlood.setSkill(1);
		gCReturnBlood.setSkillTime(lastBossPropertyTemplate.getAirtime());
		sendMessageToAllPeople(gCReturnBlood);
	}
	
	/**
	 * 下面
	 * 用户击杀
	 * 
	 */
	
	//击杀
	public void  attackBoss(Human human,long gold,int curBet,Slot slot){
		if(!bossStillAlive){
			return;
		}
	
		/*boolean bossStatus = human.getHumanAttackBossManager().attackBoss(boss, gold, curBet, slot, bossUtils, bossTemplate, attackRankVOList, tenAttackRankVOList);
		
		if(gold >0){
			//给自己发送
			GCSelfAttackBloodInfo gCSelfAttackBloodInfo = new GCSelfAttackBloodInfo();
			gCSelfAttackBloodInfo.setSelfAttackBlood(Long.valueOf(human.getHumanAttackBossManager().getHumanAttackBoss().getAttackTotalBlood()).intValue());
			gCSelfAttackBloodInfo.setWinType(human.getHumanAttackBossManager().getAttackType());
			human.getPlayer().sendMessage(gCSelfAttackBloodInfo);
		}
		//给所有人 发送
		refreshAll();
		//boss的血没有了
		if(bossStatus){
			bossStillAlive=false;
			boss.setBeKilled(1);
			boss.setModified();
			lastBoss=boss;
			boss=null;
			refreshTime(1);
			//分发奖励
			bossBroadcast.broadcast(lastBoss.getRewardNum1(),lastBoss);
			//击杀富豪榜（另一种奖励）
			bossBroadcast.broadcast1(attackRankVOList, bossList);
		}*/
	}
	
	/**
	 * 刷新用户 和 boss
	 */
	@SuppressWarnings("unused")
	private void refreshAll() {
			GameUnitList<Player> players = Globals.getOnlinePlayerService().getOnlinePlayers();
			for(Player player:players){
				player.sendMessage(getAttackRank(player));
				player.sendMessage(bossBloodReturningUtils.refreshBoss(boss,player,null));
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
		for(AttackRankVO arVO:tenAttackRankVOList){
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
		
		if(boss != null){
			BossInfoData bossInfoData = new BossInfoData();
			BossInfoDataList.add(bossInfoData.getSelf(boss));
		}
		if(lastBoss != null){
			BossInfoData bossInfoData = new BossInfoData();
			BossInfoDataList.add(bossInfoData.getSelf(lastBoss));
		}
		
		
		LastWinHumanData[] lastWinHumanDataArr =new LastWinHumanData[1];
		lastWinHumanDataArr[0]=lastWinHumanData;
		
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
		if(boss == null && lastBoss == null){
			
			GCBossStartEndInfo gCBossStartEndInfo =  new GCBossStartEndInfo();
			if(boss != null){
				gCBossStartEndInfo.setBossType(boss.getType());
				gCBossStartEndInfo.setBeKilled(0);
			}
			gCBossStartEndInfo.setNextBossLeftTime(bossUtils.getNextStartLeftTime(bossTemplateList));
 			return gCBossStartEndInfo;
		}
		if(!bossStillAlive){
			return bossUtils.getGCBossStartEndInfo(boss,lastBoss,bossTemplate,bossPropertyTemplateList,bossTemplateList,1);
		}
		return bossUtils.getGCBossStartEndInfo(boss,lastBoss,bossTemplate,bossPropertyTemplateList,bossTemplateList,2);
		
	}
	/**
	 * 进入老虎机 调用
	 */
	public void  cgRefreshBoss(Player player){
		if(boss == null && lastBoss == null){
			return;
		}
		if(!bossStillAlive){
			return ;
		}
		bossBloodReturningUtils.refreshBoss(boss,player,null);
	}
	/**
	 * 进入老虎机 调用
	 */
	public void refreshTime(){
		if(boss == null && lastBoss == null){
			return;
		}
		if(!bossStillAlive){
			refreshTime(1);
			return;
		}
		refreshTime(2);
		
	}
	private void refreshTime(int status) {
		GCBossStartEndInfo gCBossStartEndInfo= bossUtils.getGCBossStartEndInfo(boss,lastBoss,bossTemplate,bossPropertyTemplateList,bossTemplateList,status);
		sendMessageToOpenPanl(gCBossStartEndInfo);
	}
	
	
	private void sendMessageToOpenPanl(GCMessage gCMessage){
			//0是打开
		GameUnitList<Player> players = Globals.getOnlinePlayerService().getOnlinePlayers();
		for(Player player:players){
			player.sendMessage(gCMessage);
		}
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
		if(leftTime >=60){
			leftTime=0;
		}
		return bossBloodReturningUtils.refreshBoss(boss, player,Long.valueOf(leftTime).intValue());
	}

	
	
	
	
	
	

	public List<AttackRankVO> getAttackRankVOList() {
		return attackRankVOList;
	}


	public void setAttackRankVOList(List<AttackRankVO> attackRankVOList) {
		this.attackRankVOList = attackRankVOList;
	}




	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
