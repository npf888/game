package com.gameserver.slot.handler;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.common.LogReasons;
import com.common.LogReasons.GoldLogReason;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.core.util.MathUtils;
import com.core.util.RandomUtil;
import com.gameserver.common.Globals;
import com.gameserver.common.i18n.LangService;
import com.gameserver.common.msg.GCMessage;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanSlotManager;
import com.gameserver.player.OnlinePlayerService;
import com.gameserver.player.Player;
import com.gameserver.redis.enums.RedisKey;
import com.gameserver.redis.enums.RedisSlotKey;
import com.gameserver.slot.BonusNinjaService;
import com.gameserver.slot.BounsPirateService;
import com.gameserver.slot.BounsTigerService;
import com.gameserver.slot.HumanSlot;
import com.gameserver.slot.ScatterCrystalService;
import com.gameserver.slot.Slot;
import com.gameserver.slot.SlotService;
import com.gameserver.slot.WildSphinxService;
import com.gameserver.slot.data.HumanSngInfo;
import com.gameserver.slot.data.SlotList;
import com.gameserver.slot.data.SlotRankData;
import com.gameserver.slot.data.SngTournamentData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.enums.SlotTypeEnum;
import com.gameserver.slot.handler.slot13.SmallGameType13;
import com.gameserver.slot.handler.slot24.SmallGameType24;
import com.gameserver.slot.msg.CGDemoType;
import com.gameserver.slot.msg.CGFreeSlotReward;
import com.gameserver.slot.msg.CGGetSlotCachemsg;
import com.gameserver.slot.msg.CGSlotEnter;
import com.gameserver.slot.msg.CGSlotEnterRoom;
import com.gameserver.slot.msg.CGSlotGetList;
import com.gameserver.slot.msg.CGSlotGetRank;
import com.gameserver.slot.msg.CGSlotOut;
import com.gameserver.slot.msg.CGSlotSlot;
import com.gameserver.slot.msg.CGSlotTournamentClose;
import com.gameserver.slot.msg.CGSlotTournamentStart;
import com.gameserver.slot.msg.CGSlotType12Choose;
import com.gameserver.slot.msg.CGSlotType12Free;
import com.gameserver.slot.msg.CGSlotType13BounsPrize;
import com.gameserver.slot.msg.CGSlotType15Bouns;
import com.gameserver.slot.msg.CGSlotType19Scatter;
import com.gameserver.slot.msg.CGSlotType21Bouns;
import com.gameserver.slot.msg.CGSlotType22;
import com.gameserver.slot.msg.CGSlotType23InitReward;
import com.gameserver.slot.msg.CGSlotType24BounsGameGo;
import com.gameserver.slot.msg.CGSlotType24BounsPrize;
import com.gameserver.slot.msg.CGSlotType25Bouns;
import com.gameserver.slot.msg.CGSlotType26Bouns;
import com.gameserver.slot.msg.CGSlotType28Bouns;
import com.gameserver.slot.msg.CGSlotType29Bouns;
import com.gameserver.slot.msg.CGSlotType30Bouns;
import com.gameserver.slot.msg.CGSlotType31Bouns;
import com.gameserver.slot.msg.CGSlotType38Bonus;
import com.gameserver.slot.msg.CGSngRankInfo;
import com.gameserver.slot.msg.CGTournamentGetList;
import com.gameserver.slot.msg.GCFreeSlotReward;
import com.gameserver.slot.msg.GCGetSlotCachemsg;
import com.gameserver.slot.msg.GCHumanJackpotReward;
import com.gameserver.slot.msg.GCSlotEnter;
import com.gameserver.slot.msg.GCSlotError;
import com.gameserver.slot.msg.GCSlotList;
import com.gameserver.slot.msg.GCSlotTournamentNotOpen;
import com.gameserver.slot.msg.GCSlotType12Choose;
import com.gameserver.slot.msg.GCSlotType12Free;
import com.gameserver.slot.msg.GCSlotType13Bouns;
import com.gameserver.slot.msg.GCSlotType15Bouns;
import com.gameserver.slot.msg.GCSlotType19;
import com.gameserver.slot.msg.GCSlotType21Bouns;
import com.gameserver.slot.msg.GCSlotType22;
import com.gameserver.slot.msg.GCSlotType23InitReward;
import com.gameserver.slot.msg.GCSlotType24Bouns;
import com.gameserver.slot.msg.GCSlotType24BounsBar;
import com.gameserver.slot.msg.GCSlotType24BounsSamba;
import com.gameserver.slot.msg.GCSlotType25Bouns;
import com.gameserver.slot.msg.GCSlotType26Bouns;
import com.gameserver.slot.msg.GCSlotType28Bouns;
import com.gameserver.slot.msg.GCSlotType29Bouns;
import com.gameserver.slot.msg.GCSlotType30Bouns;
import com.gameserver.slot.msg.GCSlotType31BonusOne;
import com.gameserver.slot.msg.GCSlotType31BonusThree;
import com.gameserver.slot.msg.GCSlotType31BonusTwo;
import com.gameserver.slot.msg.GCSlotType38Bonus;
import com.gameserver.slot.msg.GCSngRankInfo;
import com.gameserver.slot.pojo.HumanTemporaryData;
import com.gameserver.slot.pojo.SngInfo;
import com.gameserver.slot.redismsg.AddJackpot;
import com.gameserver.slot.redismsg.ChangJackpot;
import com.gameserver.slot.redismsg.ChangJackpotNew;
import com.gameserver.slot.redismsg.EnterSlotRoom;
import com.gameserver.slot.template.BonusBrazilBarGameTemplate;
import com.gameserver.slot.template.BonusBrazilSambaStageTemplate;
import com.gameserver.slot.template.BonusHolmesRewardTemplate;
import com.gameserver.slot.template.BonusNinjaRewardTemplate;
import com.gameserver.slot.template.BonusOceanRewardTemplate;
import com.gameserver.slot.template.BounsHallowmasRewardTemplate;
import com.gameserver.slot.template.BounsPirate1Step1Template;
import com.gameserver.slot.template.BounsPirate1Step2Template;
import com.gameserver.slot.template.BounsPirate2RewardTemplate;
import com.gameserver.slot.template.BounsPirate3RewardTemplate;
import com.gameserver.slot.template.BounsSphinxRewardTemplate;
import com.gameserver.slot.template.BounsTigerRewardTemplate;
import com.gameserver.slot.template.BounsZeusRewardTemplate;
import com.gameserver.slot.template.BoxTemplate;
import com.gameserver.slot.template.ScatterCrystalRewardTemplate;
import com.gameserver.slot.template.ScatterMultipleTemplate;
import com.gameserver.slot.template.SlotJackpotNewTemplate;
import com.gameserver.slot.template.SlotJackpotTemplate;
import com.gameserver.slot.template.SlotsListTemplate;
import com.gameserver.task.enums.ClientType;
import com.gameserver.task.enums.RefreshType;

/**
 *
 * @author wayne
 *
 */
public class SlotMessageHandler {
	
	private Logger logger = Loggers.slotLogger;
	
	
	public SlotMessageHandler(OnlinePlayerService onlinePlayerService,LangService langService) {
	}
	
	/**
	 * 进入老虎机
	 * @param player
	 * @param cgSlotEnter
	 */
	public void handleSlotEnter(Player player, CGSlotEnter cgSlotEnter) {
		
		
		//老虎机ID
		int slotId = cgSlotEnter.getSlotId();
		
		//获取老虎机
		Slot slot = Globals.getSlotService().getSlotByTemplateId(slotId);
		if(slot ==null){
			logger.warn("玩家["+player.getPassportId()+"]请求的老虎机不存在");
			return;
		}
		
		//判断是否在其它老虎机里
		//HumanSlotManager humanSlotManager = player.getHuman().getHumanSlotManager();
//		if(humanSlotManager.ifInSlot()){
//			logger.warn("玩家["+player.getPassportId()+"]已经在其它的老虎机内");
//			return;
//		}
		
		Human human = player.getHuman();
		
		int result = 0;
		//等级判断
		if(!Globals.getSlotService().isLevelById(slotId, human.getLevel())){
			result = 1;
			logger.warn("玩家["+player.getPassportId()+"]进入老虎机等级不足");
		}
		//筹码判断
		if(!Globals.getSlotService().isGoldById(slotId, human.getGold())){
			result = 2;
			logger.warn("玩家["+player.getPassportId()+"]进入老虎机筹码不足");
		}
		
		human.getHumanSlotManager().setBigWin(false);
		Globals.getSlotRoomService().enterSlot(player,human.getHumanSlotManager().getSlotId(),slot.getTempleId());
		
		//进入老虎机
		Globals.getSlotService().playerEnterSlot(player, slot);
		
		GCSlotEnter gcSlotEnter = new GCSlotEnter();
		gcSlotEnter.setSlotId(slotId);
		gcSlotEnter.setResultType(result);
		player.sendMessage(gcSlotEnter);
		
		human.getHumanSlotManager().getMsgCache().clear();
		
		try{
			player.sendMessage(Globals.getWorldBossNewService().cgRefreshTime());
			Globals.getWorldBossNewService().cgRefreshBoss(player);
			Globals.getWorldBossNewService().cgGCBeforeStart(player);
//			Globals.getWorldBossNewService().bossBeforeStart(player);
		}catch(Exception e){
			Loggers.slotLogger.error("", e);
		}
	}
	
	private void sendGCSlotError(Player player){
		GCSlotError message = new GCSlotError();
		player.sendMessage(message);
	}
	
	/**
	 * 
	 * @param player
	 * @param cgSlotSlot
	 */
	public void handleSlotSlot(Player player, CGSlotSlot cgSlotSlot) {
		long startTime = System.currentTimeMillis();
		Human human = player.getHuman();
		
		int bet = cgSlotSlot.getBet();
		
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		
		//判断是否在老虎机内
		if(!humanSlotManager.ifInSlot()){
			logger.warn("玩家["+player.getPassportId()+"]没有在老虎机内尝试押注");
			sendGCSlotError(player);
			return;
		}
		
		if(humanSlotManager.ifFreeTimesEnd()){
			logger.warn("玩家["+player.getPassportId()+"]免费次数结算中");
			sendGCSlotError(player);
			return;
		}
		
		SlotService slotService = Globals.getSlotService();
		
		Slot slot = slotService.getSlotById(humanSlotManager.getCurrentSlotId());
		
		if(slot == null){
			logger.warn("玩家["+player.getPassportId()+"]押注的老虎机不存在");
			sendGCSlotError(player);
			return;
		}
		
		int slotId = slot.getTempleId();
		
		//获取模板数据
		SlotsListTemplate tempSlotsListTemplate = slotService.getslotTemplateMap(slotId);
		
		//判断bet是不是在范围内
		if(!tempSlotsListTemplate.getBetList().contains(bet)){
			logger.warn("玩家["+player.getPassportId()+"]押注不在范围内");
			sendGCSlotError(player);
			return;
		}
		
		//押注金额等级判断
		if(!slotService.isBetLv((int)tempSlotsListTemplate.getId(), bet, human.getLevel())){
			logger.warn("玩家["+player.getPassportId()+"]押注超出自己的等级了");
			sendGCSlotError(player);
			return;
		}
		long slotStartBefore = System.currentTimeMillis();
		//总押注
		int tempAllBets = tempSlotsListTemplate.getPayLinesNum()*bet;
		
		int freeType = cgSlotSlot.getFree();
		
		boolean free = (freeType==1 || freeType==2);//1 2 免费
		//true 泰国象老虎机自由转动 中
		boolean elephantIsBigWild =  humanSlotManager.getHumanTemporaryData().isElephantIsBigWild();
		boolean isSpartaFreeTurn =  humanSlotManager.getHumanTemporaryData().isSpartaFreeTurn();
		//清空 winner 的数据
		humanSlotManager.getHumanTemporaryData().setWinner(false);
//		humanSlotManager.getHumanTemporaryData().setWinnerGold(0l);
//		boolean isDisTurn =  humanSlotManager.getHumanTemporaryData().isDisTurn();
		//免费
		if(free){
			if(!elephantIsBigWild && !isSpartaFreeTurn){// && !isDisTurn){
				if(!humanSlotManager.ifHasFreeTimes()){
					logger.warn("玩家["+player.getPassportId()+"]没有免费次数");
					sendGCSlotError(player);
					return;
				}
				
				if(tempAllBets != humanSlotManager.getCurrentBet()){
					logger.warn("玩家["+player.getPassportId()+"]下注量不对");
					sendGCSlotError(player);
					return;
				}
				humanSlotManager.freeSlot();
			}
		}else{
			//判断是否有足够的钱
			if(!human.hasEnoughMoney(tempAllBets, Currency.GOLD)){
				logger.warn("玩家["+player.getPassportId()+"]没有足够的钱");
				sendGCSlotError(player);
				return;
			}
			
			//扣除钱
			slotLog(human,slot.getTempleId(),true,false,false,tempAllBets,0l);
			
			/**
			 * 原先 里边包含 添加经验（现在去掉了）
			 */
			humanSlotManager.bet(slot, tempAllBets);
			
			if(tempAllBets > 0){//竞赛奖金池
				/*long bonus = (long)Math.floor(tempAllBets*(tempSlotsListTemplate.getRaceReward()/10000f));
				slotService.putBonuts(slot.getTempleId(), bonus);*/
				Globals.getTournamentService().putBonuts(slot.getType(), tempAllBets,human);
			}
		}
		/**
		 *        经验加成
		 * 
		 * 添加经验 不管是否付费 还是免费
		 */
		humanSlotManager.addHumanExp(tempAllBets);
		long slotStartBefore2 = System.currentTimeMillis();
		long slotStartBeforeTime = slotStartBefore2-slotStartBefore;
		
		
		long slotStart_BeforeEnterSlot = System.currentTimeMillis();
		
		SlotBase slotBase = SlotBase.getSlot(tempSlotsListTemplate.getType());
		
		if(slotBase == null){
			logger.info("玩家["+player.getPassportId()+"]找不到对应的老虎机类型");
			sendGCSlotError(player);
			return;
		}
		
		/**
		 * 
		 * 新的
		 * 
		 * 彩金补充 
		 * 
		 * 
		 * 先补 两个池子
		 */
		long beforeJackpot=0l;
		long beforeCumnJackpot=0l;
		if(tempSlotsListTemplate.getJackpotswitch() == 1){
			/**
			 * 转动之前的彩金 日志
			 */
			beforeJackpot = slotBase.getJackpot(slot, bet, tempSlotsListTemplate);
			beforeCumnJackpot = slotBase.getcumnJackpot(slot, bet, tempSlotsListTemplate);
			Globals.getLogService().sendJackpotLog(human, LogReasons.JackpotLogReason.JACK, "", 
					tempSlotsListTemplate.getLangDesc(),slot.getType(), slot.getTempleId(), bet, 1, "Jackpot-", "cumuJackpot-", 
					beforeJackpot, beforeCumnJackpot,    0, 0,    0);
			//具体的变化  彩金补充 
			slotBase.setSlot(slot);
			slotBase.supplemen(bet,tempAllBets, tempSlotsListTemplate);
			/**
			 * 彩金 发送消息
			 */
			slotBase.sendMessgae(slot,bet,tempSlotsListTemplate);
		}
		
		//转动之前的免费次数
		int beforeFreeTimes = humanSlotManager.getFreeTimes();
		
		slotBase.setHumanSlotManager(humanSlotManager);
		slotBase.setPlayer(player);
		slotBase.setSlot(slot);
		slotBase.setSlotService(slotService);
		slotBase.setTempSlotsListTemplate(tempSlotsListTemplate);
		slotBase.setSmh(this);
		slotBase.handleSlot(cgSlotSlot.getFree(), bet, tempAllBets);
		
		//转动之后的免费次数(主要用于活动：我是大赢家)
		int afterFreeTimes = humanSlotManager.getFreeTimes();
		slot.setModified();
		
		//赢得钱
        long tempReward = slotBase.getTempReward();
        //玩家获得总彩金
		long humanJackpot = slotBase.getHumanJackpot();
		
		
		
		/**
		 * 
		 * 新的
		 * 
		 * 彩金补充 
		 * 
		 * 
		 * 再用累计池 补充另一个池子
		 * 
		 * 
		 */
		if(tempSlotsListTemplate.getJackpotswitch() == 1){
			/**
			 * 变化
			 */
			slotBase.suppleAnotherOne(bet,tempAllBets, tempSlotsListTemplate,humanJackpot);
			
			/**
			 * 日志
			 */
			long afterJackpot = slotBase.getJackpot(slot, bet, tempSlotsListTemplate)-beforeJackpot;
			long afterCumnJackpot = slotBase.getcumnJackpot(slot, bet, tempSlotsListTemplate)-beforeCumnJackpot;
			Globals.getLogService().sendJackpotLog(human, LogReasons.JackpotLogReason.JACK, "", 
					tempSlotsListTemplate.getLangDesc(),slot.getType(), slot.getTempleId(), bet, 2, "Jackpot-", "cumuJackpot-", 
					0, 0,    afterJackpot, afterCumnJackpot,    humanJackpot);
		}
		
		
		//游戏类型
		int type = Globals.getJackpotServer().getSlotType(tempSlotsListTemplate.getType());
		
		
		
		long slotStart_afterOutSlot = System.currentTimeMillis();
		long slotStartBeforeAfter = (slotStart_afterOutSlot-slotStart_BeforeEnterSlot);
		/**
		 * 成就系统 和 竞赛 在转动老虎机时 的 间隔
		 */
		long achievement_tournament_start = System.currentTimeMillis();
		if(!free || elephantIsBigWild){
			
            slotLog(human,slot.getTempleId(),false,true,false,tempReward,(long)bet);
			
			humanSlotManager.refund(slot,tempReward,humanJackpot);
		
			Globals.getRankNewServer().win(human, tempReward);
			//成就赢取统计
			human.getHumanAchievementManager().updateSlotWin(slot.getType(), tempReward);
			
			//添加彩金
//			Globals.getJackpotServer().add(human.getPassportId(), human.getImg(), human.getName(), humanJackpot,type);
			//金币 + 彩金
			if((tempReward+humanJackpot)>0){
				//竞技中奖累计
				//slotService.putData(player,slotId, human.getPassportId(), tempReward);
				Globals.getTournamentService().putData(player, slot.getType(), human.getPassportId(), tempReward+humanJackpot);
				
			}
			if(tempReward > 0){
				/**
				 * 触发 winner触发的翻倍转盘
				 */
				human.getHumanSlotManager().handleWinner(tempReward, bet, tempSlotsListTemplate,false,0);
				/**
				 * 判断送礼物 是否免费
				 */
				human.getHumanSlotManager().isEpicOrSuperOrMega(tempReward, bet, tempSlotsListTemplate);
				
				//总产出
				Globals.getLogService().sendDataOverviewLog(human, LogReasons.DataOverviewLogReason.DataOverview_type11, 
						LogReasons.DataOverviewLogReason.DataOverview_type11.getReasonText(), tempReward,
						human.getAge(),human.getIpCountries(),human.getGirl());
			}
			
			//成就老虎机次数
			human.getHumanAchievementManager().updateSlotCumulativeNum(slot.getType());
			//成就押注
			human.getHumanAchievementManager().updateSlotCumulativeBet(slot.getType(), tempAllBets);
			
			
		}else{
			//添加累计奖励
			humanSlotManager.addReward(tempReward);
			//必须同时不等于维密 和 沙滩，维密和沙滩 特殊 ，它们需要单独处理
			if(humanSlotManager.ifFreeTimesEnd() && tempSlotsListTemplate.getType() != SlotTypeEnum.SlotType12.getIndex() 
					&& tempSlotsListTemplate.getType() != SlotTypeEnum.SlotType4.getIndex() ){
				CGFreeSlotReward message = new CGFreeSlotReward();
				if(isBox(tempSlotsListTemplate.getType())){
					message.setPos(-2);
				}else{
					//权重随机箱子
					List<BoxTemplate> boxTemplateList=Globals.getSlotService().getRandomBoxListByTypeLevel(slot.getType(), human.getLevel());
				    int position =RandomUtil.nextInt(0, boxTemplateList.size());
					message.setPos(position);
				}
				SlotHandlerFactory.getHandler().handleFreeSlotReward(player, message);
			}
		}
	
		if(tempReward > 0){
			theWin(human,bet,tempReward,tempSlotsListTemplate,slot.getType());
		}
		
		
		updateSlotTotal(human,tempReward,free);
		long achievement_tournament_end = System.currentTimeMillis();
		long achievementTournamentStartEnd = (achievement_tournament_end-achievement_tournament_start);
		
		
		long broadcastNoticeStart = System.currentTimeMillis();
		if(humanJackpot > 0){//全服广播
			//中了彩金  就 触发翻倍购买的转盘
			/**
			 * 触发 winner触发的翻倍转盘
			 */
			human.getHumanSlotManager().handleWinner(humanJackpot, bet, tempSlotsListTemplate,true,1);
			//用户前端 显示 加 彩金的金币
			GCHumanJackpotReward gCHumanJackpotReward = new GCHumanJackpotReward();
			gCHumanJackpotReward.setJackpotReward(humanJackpot);
			human.getPlayer().sendMessage(gCHumanJackpotReward);
			human.giveMoney(humanJackpot, Currency.GOLD, true, LogReasons.GoldLogReason.SLOTMOSAICGOLD, LogReasons.GoldLogReason.SLOTMOSAICGOLD.getReasonText(), -1, 1);
			Globals.getNoticeService().broadcastNoticeMulti(LangConstants.JACKPOT_BROADCAST, String.valueOf(type),human.getName(),human.getImg(),String.valueOf(humanJackpot));//humanJackpot
		}
		if(tempReward > 0){
			
			//中奖次数
			Globals.getLogService().sendDataOverviewLog(human, LogReasons.DataOverviewLogReason.DataOverview_type9, 
					LogReasons.DataOverviewLogReason.DataOverview_type9.getReasonText(), 1,
					human.getAge(),human.getIpCountries(),human.getGirl());
		}
		long broadcastNoticeEnd = System.currentTimeMillis();
		long humanJackpotBroadcastNoticeStartEnd = (broadcastNoticeEnd-broadcastNoticeStart);
		
		
		long activity_before = System.currentTimeMillis();
		//活动
		/**幸运老虎机**/
		Globals.getActivityService().luckySlot(human, slot.getType());
		if(!free){
			/**连续游戏送大礼**/
			Globals.getActivityService().continuousPlayForGift(human);
			/**情有独钟**/
			Globals.getActivityService().preferenceFavorForGift(human,slot.getType());
		}
		/**消耗返利**/
		Globals.getActivityService().ConsumeForGift(human);
		/**我是大赢家**/
		Globals.getActivityService().IAmWinnerForGift(human,afterFreeTimes-beforeFreeTimes,(int)tempReward);
		/**极限追求**/
		Globals.getActivityService().extremePursuitForGift(human,slot.getType(),new ArrayList<Integer>());
		
		
		//清空
		humanSlotManager.getRandomIntListDEMO().clear();
		//spin次数
		Globals.getLogService().sendDataOverviewLog(human, LogReasons.DataOverviewLogReason.DataOverview_type8, 
				LogReasons.DataOverviewLogReason.DataOverview_type8.getReasonText(), tempSlotsListTemplate.getId(),
				human.getAge(),human.getIpCountries(),human.getGirl());
		//总投入
		Globals.getLogService().sendDataOverviewLog(human, LogReasons.DataOverviewLogReason.DataOverview_type12, 
				LogReasons.DataOverviewLogReason.DataOverview_type12.getReasonText(), tempAllBets,
				human.getAge(),human.getIpCountries(),human.getGirl());
		//老虎机快照 后期在加上
		Globals.getLogService().sendSlotRoomLog(human, LogReasons.SlotRoomLogReason.SLOTROOMKUAIZHAO, "", slotId);
		//记录用户转动老虎机的时间
		HumanSlot humanSlot = humanSlotManager.getHumanSlotBySlotId(humanSlotManager.getCurrentSlotId());
		
		long activity_end = System.currentTimeMillis();
		long activityBeforeEnd=(activity_end-activity_before);
		
		
		long task_collect_before = System.currentTimeMillis();
		Globals.getTaskNewService().spinSlot(human, tempSlotsListTemplate.getType(), RefreshType.RefreshType5.getIndex());
		
		human.getHumanCollectManager().spinSlot(slotId,tempSlotsListTemplate.getTicketID(),tempSlotsListTemplate.getCount1(),tempSlotsListTemplate.getCount2());
		long task_collect_end = System.currentTimeMillis();
		long taskCollectBeforeEnd = (task_collect_end-task_collect_before);
		
		
		//区分老虎机 初级 中级 高级  精英
		int slotType = tempSlotsListTemplate.getList();
		Globals.getLogService().sendInOutTimeLog(human, LogReasons.InOutTimeLogReason.INOUTTIME, "", new Date().getTime(),slotType,0,0, humanSlot.getCharId(), slotId);
		
		/**
		 * 免费转动 金库里的金币不张
		 */
		long treasuryStartEnd=0l;
		if(!free){
			long treasury_start = System.currentTimeMillis();
			human.getHumanTreasuryManager().everyTurnChangeGold(tempAllBets, human);
			long treasury_end = System.currentTimeMillis();
			
			treasuryStartEnd=(treasury_end-treasury_start)/1000;
		}
		long HuoYueForLaohujiWinEnd=0l;
		if(tempReward>0)
		{
			long HuoYueForLaohujiWin_start = System.currentTimeMillis();
			Globals.getClubService().addHuoYueForLaohujiWin(human, tempReward);
			long HuoYueForLaohujiWin_end = System.currentTimeMillis();
			HuoYueForLaohujiWinEnd=(HuoYueForLaohujiWin_end-HuoYueForLaohujiWin_start);
		}

		/**
		 * 世界boss
		 */
		long WorldBossNewBeforeEnd=0l;
		long WorldBossNew_start = System.currentTimeMillis();
		try{
			Globals.getWorldBossNewService().attackBoss(human, tempReward, bet, slot,slotBase.getTempSlotConnectInfoList(),slotBase.getCurElementList(),slotService,slotType);
		}catch(Exception e){
			logger.error("世界boss的攻击异常：",e);
		}

		/**
		 * 经验加速卡 的提醒 ，中奖直接走经验加速卡
		 */
		if(tempReward > 0){
			
			human.getHumanPayguideManager().sendTreasuryAndExp(true);
		}else{
			/**
			 * 未中奖  则 小金猪 和 经验加速 同时出现，有个优先级
			 */
			human.getHumanPayguideManager().sendTreasuryAndExp(false);
		}
		long WorldBossNew_end = System.currentTimeMillis();
		WorldBossNewBeforeEnd=(WorldBossNew_end-WorldBossNew_start);
		long endTime = System.currentTimeMillis();
		long totalTime=endTime-startTime;
		try{
			
		
			Globals.getLogService().sendStatisticsTimeLog(human, LogReasons.StatisticsTimeLogReason.StatisticsTime, "",
					human.getPassportId(), Long.valueOf(tempSlotsListTemplate.getType()), Long.valueOf(tempSlotsListTemplate.getList()),
					Long.valueOf(bet), 
					slotStartBeforeTime,
					slotStartBeforeAfter, 
					achievementTournamentStartEnd, 
					humanJackpotBroadcastNoticeStartEnd,
					activityBeforeEnd, 
					taskCollectBeforeEnd, 
					treasuryStartEnd, 
					HuoYueForLaohujiWinEnd,
					WorldBossNewBeforeEnd,totalTime);
		}catch(Exception e){
			logger.error("时间统计日志 异常", e);
		}
	}
	
	
	/**
	 * 
	 * @param win
	 * @param free false 免费
	 */
	private void updateSlotTotal(Human human,long win,boolean free){
		human.setSlotRotate(human.getSlotRotate()+1);
		if(!free){
			if(human.getSlotSingleWin() < win){
				human.setSlotSingleWin(win);
			}
			human.setSlotWin(human.getSlotWin()+win);
			
			if(win > 0){
				human.setSlotWinNum(human.getSlotWinNum()+1);
			}
		}
		human.setModified();
	}
	
	/**
	 * 
	 * @param bet
	 * @param win
	 */
	private void theWin(Human human,long bet,long win,SlotsListTemplate template,int slotType){
		long big = template.getBigWinNum()*bet;
		long mega = template.getMegaWinNum()*bet;
		long supers = template.getSuperWinNum()*bet;
		long epic = template.getEpicWinNum()*bet;
		int type = 0;
		int taskType = 0;
		if(big<= win && win < mega){
			human.getHumanAchievementManager().updateSlotBigWinNum(slotType);
			type = 1;
			taskType = 4;
		}else if(mega<= win && win < supers){
			human.getHumanAchievementManager().updateSlotMegaWinNum(slotType);
			type = 2;
			taskType = 5;
		}else if(supers<= win && win < epic){
			human.getHumanAchievementManager().updateSlotSuperWinNum(slotType);
			type = 3;
			taskType = 6;
		}else if(epic<= win){
			human.getHumanAchievementManager().updateSlotEplcWinNum(slotType);
			type = 4;
			taskType = 7;
		}
		if(type == 0){
			human.getHumanSlotManager().setBigWin(false);
		}else{
			human.getHumanSlotManager().setBigWin(true);
		}
		
		if(type != 0){
			Globals.getSlotRoomService().change4(human.getPlayer(), type);
			Globals.getTaskNewService().slotBigWin(human, ClientType.TASK104.getIndex(), RefreshType.RefreshType9.getIndex(), taskType);
		}
		
	}
	
	/**
	 * 随机原始坐标 以后有可能有可能单独定制
	 * @param template
	 * @param manager
	 * @return
	 */
	public List<Integer> randomPoint(SlotsListTemplate template,HumanSlotManager manager,long level){
       List<Integer> randomIntList = new ArrayList<Integer>();
        if(manager.isFly() && level <= 4){//玩家第一次玩 设置固定移动步数
        	randomIntList.add(template.getFirstReel1()-1);
        	randomIntList.add(template.getFirstReel2()-1);
        	randomIntList.add(template.getFirstReel3()-1);
        	if(template.getColumns() != 3){//3列
        		randomIntList.add(template.getFirstReel4()-1);
            	randomIntList.add(template.getFirstReel5()-1);
        	}
        	manager.setFly(false);
        }else{
        	
        	List<Integer> listDemo = manager.getRandomIntListDEMO();
        	if(listDemo.isEmpty()){
        		for(int i=0;i<template.getColumns();i++){
        			//每个轴旋走的步数
        			int temp = MathUtils.random(0,template.getReelNumList().get(i));
        			randomIntList.add(temp);
        		}
        	}else{
        		return listDemo;
        	}
        	
        }
		return randomIntList;
	}
	
	/**
	 * 设置彩金  （修改 发广播给ID最小的服务器计算）
	 * @param slot
	 * @param slotService
	 * @param tempAllBets 总押注
	 */
	public void jackpotSlot(Slot slot,SlotService slotService,long tempAllBets) {
		
		if(Globals.getServerComm().isAuthority()){
			// 彩金池 彩金累计池增加
			int slotId = slot.getTempleId();
			// 彩金池参数
			double poolPer = slotService.getJackpotPoolPerById(slotId);
			// 彩金累计池参数
			double addpoolPer = slotService.getJackpotAddPoolPerById(slotId);
			
			slot.setJackpot(slot.getJackpot() + (long) Math.floor(tempAllBets * poolPer));
			slot.setCumuJackpot(slot.getCumuJackpot() + (long) Math.floor(tempAllBets * addpoolPer));
			slot.redisJackpot();
		}else{
			//广播给最小服务器操作
			AddJackpot addJackpot = new AddJackpot();
			addJackpot.setSlotTemplaId(slot.getTempleId());
			addJackpot.setTempAllBets(tempAllBets);
			String serverId = Globals.getServerComm().getMinServerId();
			Globals.getRedisService().sendRedisMsgToServer(serverId, addJackpot);
		}
	}
	
	/**
	 * 彩金计算 old
	 * @param slot
	 * @param sjt
	 * @param jackpotOriValue
	 * @param humanJackpot
	 * @return
	 */
	public long setJackpot(Slot slot,SlotJackpotTemplate sjt,long humanJackpot,boolean isJackpot){
		//玩家奖励参数
		double rewardPer = 0d;
		
		if(isJackpot){
			rewardPer = sjt.getJackpotPer()/10000d;
		}else{
			rewardPer = sjt.getRewardPer()/10000d;
		}
		
		long getJack = 0;
		if(Globals.getServerComm().isAuthority()){
			
			long jackpot = slot.getJackpot();
			
			//获取彩金
			getJack = (long)Math.floor(jackpot*rewardPer);
			
			changJackpot(slot,getJack);
			
		}else{
			//redis 中获取数据
			long jackpot = getJackpot(sjt.getId());

			//获取彩金
			getJack = (long)Math.floor(jackpot*rewardPer);
			
			ChangJackpot cj = new ChangJackpot();
			cj.setSlotTemplaId(sjt.getId());
			cj.setGetJack(getJack);
			String serverId = Globals.getServerComm().getMinServerId();
			Globals.getRedisService().sendRedisMsgToServer(serverId, cj);
		}
		return humanJackpot+getJack;
	}
	/**
	 * 彩金计算 new 
	 * @param slot
	 * @param sjt
	 * @param jackpotOriValue
	 * @param humanJackpot
	 * @return
	 */
	public long setJackpotNew(Human human,Slot slot,int bet,SlotJackpotNewTemplate sjt,long humanJackpot,boolean isJackpot){
		
		
		//玩家奖励参数
		long rewardPer = 0;
		ChangJackpotNew cj = new ChangJackpotNew();
		//某个老虎机 某个bet场的 某个单线下注 的   初始累计奖励（初始为0）
		SlotsListTemplate slotsListTemplate = Globals.getSlotService().getslotTemplateMap(slot.getTempleId());
		// 第一个 
		if(bet == slotsListTemplate.getBet1()){
			
			if(isJackpot){
				rewardPer = slot.getJackpot1()*sjt.getJackpotreward()/10000;
				slot.setJackpot1(slot.getJackpot1()-rewardPer);
			}
			
			cj.setSlotTemplaId(sjt.getId());
			cj.setWhich(1);
			cj.setJackPot(slot.getJackpot1());
			cj.setCumeJackPot(slot.getCumuJackpot1());
			// 第二个 
		}else if(bet == slotsListTemplate.getBet2()){
			
			if(isJackpot){
				rewardPer = slot.getJackpot2()*sjt.getJackpotreward()/10000;
				slot.setJackpot2(slot.getJackpot2()-rewardPer);
			}
			cj.setSlotTemplaId(sjt.getId());
			cj.setWhich(2);
			cj.setJackPot(slot.getJackpot2());
			cj.setCumeJackPot(slot.getCumuJackpot2());
			// 第三个 
		}else if(bet == slotsListTemplate.getBet3()){
			
			if(isJackpot){
				rewardPer = slot.getJackpot3()*sjt.getJackpotreward()/10000;
				slot.setJackpot3(slot.getJackpot3()-rewardPer);
			}
			
			cj.setSlotTemplaId(sjt.getId());
			cj.setWhich(3);
			cj.setJackPot(slot.getJackpot3());
			cj.setCumeJackPot(slot.getCumuJackpot3());
			// 第四个 
		}else if(bet == slotsListTemplate.getBet4()){
			
			if(isJackpot){
				rewardPer = slot.getJackpot4()*sjt.getJackpotreward()/10000;
				slot.setJackpot4(slot.getJackpot4()-rewardPer);
			}
			cj.setSlotTemplaId(sjt.getId());
			cj.setWhich(4);
			cj.setJackPot(slot.getJackpot4());
			cj.setCumeJackPot(slot.getCumuJackpot4());
			
		}else if(bet == slotsListTemplate.getBet5()){
			if(isJackpot){
				rewardPer = slot.getJackpot5()*sjt.getJackpotreward()/10000;
				slot.setJackpot5(slot.getJackpot5()-rewardPer);
			}
			
			cj.setSlotTemplaId(sjt.getId());
			cj.setWhich(5);
			cj.setJackPot(slot.getJackpot5());
			cj.setCumeJackPot(slot.getCumuJackpot5());
		}
		//累计奖池的变化
//		slot.setModified();
		//通过 redis 更新其他服务器 数据
		String serverId = Globals.getServerComm().getMinServerId();
//		Globals.getRedisService().sendRedisMsgToServer(serverId, cj);
		
		return rewardPer;
	}
	
	
	/**
	 * redis 获取彩金
	 * @param slotTempleId
	 * @return
	 */
	public long getJackpot(int slotTempleId){
		JedisPool pool = Globals.getRedisService().getJedisPool();
		Jedis jedis = pool.getResource();
		try{
			String key = RedisKey.SLOT.getKey()+slotTempleId;   
			String jack = jedis.hget(key, RedisSlotKey.jackpot.getKey());
			return jack == null? 0:Long.valueOf(jack);
		}finally{
			jedis.close();
//			pool.returnResourceObject(jedis);
		}
		
	}
	
	public void changJackpot(Slot slot,long getJack){
		/**以下计算发广播给服务器ID最小的服务器计算 **/
		slot.setJackpot(slot.getJackpot() - getJack);
		
		long jackPot = slot.getJackpot();
		long cumeJackPot = slot.getCumuJackpot();
		
		long jackpotOriValue = Globals.getSlotService().getJackpotOriValue(slot.getTempleId());
		
		if(jackPot < jackpotOriValue){
			long make  = jackpotOriValue - jackPot;//差多少
			if(cumeJackPot < make){
				slot.setJackpot(jackPot + cumeJackPot);
				slot.setCumuJackpot(0l);
			}else{
				slot.setJackpot(jackpotOriValue);
				slot.setCumuJackpot(cumeJackPot - make);
			}
		}
		slot.redisJackpot();
	}

	public void handleFreeSlotReward(Player player,CGFreeSlotReward cgFreeSlotReward) {
	
		Human human = player.getHuman();
		
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		
		//位置 -1 表示非宝箱
		int position = cgFreeSlotReward.getPos();
		
		Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
		
		if(slot ==null){
			logger.warn("玩家["+player.getPassportId()+"]请求的老虎机不存在");
			return;
		}
		
		SlotsListTemplate tempSlotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(),SlotsListTemplate.class);
		
		//当前单线押注额
 		int currBet = (int)humanSlotManager.getCurrentBet()/tempSlotsListTemplate.getPayLinesNum();
		logger.info("结算时的免费次数：：："+humanSlotManager.getFreeTimes()+"-----------------userTime::"+humanSlotManager.getUseTimes());
		if(!humanSlotManager.ifFreeTimesEnd()){
			logger.warn("玩家["+player.getPassportId()+"]免费次数还没结束");
			return;
		}
		if(position == -2 && isBox(tempSlotsListTemplate.getType())){
			long reward = (long) humanSlotManager.getCurrentRewardNum();
			slotFree(player,reward);
			
			/**
			 * 判断送礼物 是否免费
			 */
			human.getHumanSlotManager().isEpicOrSuperOrMega(reward, currBet, tempSlotsListTemplate);
		}else if(position != -2 && tempSlotsListTemplate.getType() == SlotTypeEnum.SlotType10.getIndex()){
			
			float rate = Globals.getSlotService().getScatter(position);
			
			long reward = (long) Math.floor(humanSlotManager.getCurrentRewardNum()*rate);
			GCFreeSlotReward gcFreeSlotReward = new GCFreeSlotReward();
			gcFreeSlotReward.setBoxList(new int[0]);
			gcFreeSlotReward.setRewardNum(reward);
			gcFreeSlotReward.setPos(position);
			player.sendMessage(gcFreeSlotReward);
			slotFree(player,reward);
			
			/**
			 * 判断送礼物 是否免费
			 */
			human.getHumanSlotManager().isEpicOrSuperOrMega(reward, currBet, tempSlotsListTemplate);
			
			
		}else if(position >= 0){
			//权重随机箱子
			List<BoxTemplate> boxTemplateList=Globals.getSlotService().getRandomBoxListByTypeLevel(slot.getType(), human.getLevel());
			
			int[] boxArr = new int[boxTemplateList.size()];
			for(int i=0;i<boxTemplateList.size();i++){
				boxArr[i] = boxTemplateList.get(i).getId();
			}
			
			//交换顺序
			if(position != 0){
				int temp = boxArr[0];
				boxArr[0] = boxArr[position];
				boxArr[position] = temp;
			}
			//这个值是给用户加钱 的，不用于前端
			long reward = (long) Math.floor(humanSlotManager.getCurrentRewardNum()*boxTemplateList.get(0).getRate());
			//这个值用于给前端显示-前端已经乘以了倍数
			long rewardView = (long) Math.floor(humanSlotManager.getCurrentRewardNum());
			
			//Globals.getSlotService().putData(player,slot.getSlotType(), human.getPassportId(), reward);
			
			GCFreeSlotReward gcFreeSlotReward = new GCFreeSlotReward();
			gcFreeSlotReward.setBoxList(boxArr);
			gcFreeSlotReward.setRewardNum(rewardView);
			gcFreeSlotReward.setPos(position);
			player.sendMessage(gcFreeSlotReward);
			
			slotFree(player,reward);
		    
			/**
			 * 判断送礼物 是否免费
			 */
			human.getHumanSlotManager().isEpicOrSuperOrMega(reward, currBet, tempSlotsListTemplate);
		    //中小箱子次数 
		    Globals.getLogService().sendDataOverviewLog(human, LogReasons.DataOverviewLogReason.DataOverview_type10, 
		    		LogReasons.DataOverviewLogReason.DataOverview_type10.getReasonText(), 1,
		    		human.getAge(),human.getIpCountries(),human.getGirl());
		}
		
		
	}
	
	private boolean isBox(int type){
		return  type != SlotTypeEnum.SlotType10.getIndex() && 
				type != SlotTypeEnum.SlotType1.getIndex() && 
				type != SlotTypeEnum.SlotType2.getIndex() && 
//				type != SlotTypeEnum.SlotType3.getIndex() && 
				type != SlotTypeEnum.SlotType4.getIndex() && 
				type != SlotTypeEnum.SlotType5.getIndex() && 
				type != SlotTypeEnum.SlotType6.getIndex() && 
				type != SlotTypeEnum.SlotType7.getIndex() && 
				type != SlotTypeEnum.SlotType9.getIndex() &&
				type != SlotTypeEnum.SlotType12.getIndex() ;
	}

	/**
	 * 获取老虎机列表
	 * @param player
	 * @param cgSlotGetList
	 */  
	public void handleSlotGetList(Player player, CGSlotGetList cgSlotGetList) {
		
		//老虎机类型
		int slotType = cgSlotGetList.getSoltType();
				
		logger.debug("玩家[" + player.getPassportId() + "]请求老虎机类型为+ "+slotType+" +的列表列表");
		
		
		GCSlotList slotList = new GCSlotList();
		
		List<SlotsListTemplate> listTemp = Globals.getSlotService().getListByType(slotType);
		
		SlotList[] list = new SlotList[listTemp.size()];
		
		for(int i = 0;i < listTemp.size();i++){
			SlotList sl = new SlotList();
			sl.setSlotId(listTemp.get(i).getId());
			sl.setHandsel(Globals.getSlotService().getSlotJack(listTemp.get(i).getId()));
			list[i] = sl;
		}
		
		slotList.setSlotList(list);
		
		player.sendMessage(slotList);
	}
	
	/**
	 * 老虎机详细日志统计
	 * @param human
	 * @param slotId
	 * @param cost
	 * @param get
	 * @param scatter
	 * @param totalGold
	 * @param bet
	 */
	public void slotLog(Human human, int slotId, boolean cost, boolean get, boolean scatter, long totalGold,Long bet) {

		String SLOT_COST = "SLOT_COST" + slotId;

		String SLOT_GET = "SLOT_GET" + slotId;

		String SLOT_SCATTER = "SLOT_SCATTER" + slotId;

		if (cost) {
			// 扣除钱
			GoldLogReason goldLogReason = LogReasons.GoldLogReason.valueOf(SLOT_COST);
			human.costMoney(totalGold, Currency.GOLD, true, goldLogReason,goldLogReason.getReasonText(), -1, 1);
		} else if (get) {
			GoldLogReason goldLogReason = LogReasons.GoldLogReason.valueOf(SLOT_GET);
			String detailReason = MessageFormat.format(goldLogReason.getReasonText(),slotId, bet.intValue());
			human.giveMoney(totalGold, Currency.GOLD, true, goldLogReason, detailReason,-1, 1);
			
			Globals.getTaskNewService().spinSlotWin(human, ClientType.TASK104.getIndex(), RefreshType.RefreshType8.getIndex(), (int)totalGold);
		} else if (scatter) {
			GoldLogReason goldLogReason = LogReasons.GoldLogReason.valueOf(SLOT_SCATTER);
			String detailReason = MessageFormat.format(goldLogReason.getReasonText(),slotId, bet);
			human.giveMoney(totalGold, Currency.GOLD, true,goldLogReason,detailReason, -1, 1);
		}
	}
	/**
	 * 只加钱 不发消息
	 * @param human
	 * @param slotId
	 * @param cost
	 * @param get
	 * @param scatter
	 * @param totalGold
	 * @param bet
	 */
	public void slotLogOther(Human human, int slotId, boolean cost, boolean get, boolean scatter, long totalGold,Long bet) {
		
		String SLOT_COST = "SLOT_COST" + slotId;
		
		String SLOT_GET = "SLOT_GET" + slotId;
		
		String SLOT_SCATTER = "SLOT_SCATTER" + slotId;
		
		if (cost) {
			// 扣除钱
			GoldLogReason goldLogReason = LogReasons.GoldLogReason.valueOf(SLOT_COST);
			human.costMoney(totalGold, Currency.GOLD, false, goldLogReason,goldLogReason.getReasonText(), -1, 1);
		} else if (get) {
			GoldLogReason goldLogReason = LogReasons.GoldLogReason.valueOf(SLOT_GET);
			String detailReason = MessageFormat.format(goldLogReason.getReasonText(),slotId, bet.intValue());
			human.giveMoney(totalGold, Currency.GOLD, false, goldLogReason, detailReason,-1, 1);
			
			Globals.getTaskNewService().spinSlotWin(human, ClientType.TASK104.getIndex(), RefreshType.RefreshType8.getIndex(), (int)totalGold);
		} else if (scatter) {
			GoldLogReason goldLogReason = LogReasons.GoldLogReason.valueOf(SLOT_SCATTER);
			String detailReason = MessageFormat.format(goldLogReason.getReasonText(),slotId, bet);
			human.giveMoney(totalGold, Currency.GOLD, false,goldLogReason,detailReason, -1, 1);
		}
	}

	public void handleSlotGetRank(Player player, CGSlotGetRank cgSlotGetRank) {
		//竞赛类型
		int tournamentType = cgSlotGetRank.getTournamentType();
		
		List<SlotRankData> list = Globals.getTournamentService().getSlotRankData(tournamentType,player.getHuman());
		
		if(list.isEmpty()){
			/*player.sendMessage(new GCSlotTournamentNotOpen());*/
			list = new ArrayList<SlotRankData>();
		}
		GCMessage message = Globals.getTournamentService().getGCSlotGetRank(list);
		player.sendMessage(message);
	}

    /**
     * 维密老虎机选择
     * 如果中了 scatter,就会出现 三个（10*2  20  5*4）卡片（卡片之间的区别是 ，10免费转动完之后 所获得的钱乘以2 , 20次免费转动完之后  所获得的钱乘以1,5次免费转动完之后  所获得的钱乘以4）
     * 然后用户选择 其中一个, 之后进行相应的免费转动，免费转动完了之后，才去调用 handleSlotType12Free 这个接口进行结算
     * @param player
     * @param cgSlotType12Choose
     */
	public void handleSlotType12Choose(Player player, CGSlotType12Choose cgSlotType12Choose) {
		
		 Human human = player.getHuman();
		   
		 HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		 
	     SlotService slotService = Globals.getSlotService();
			
	     Slot slot = slotService.getSlotById(humanSlotManager.getCurrentSlotId());
			
		 //玩家选择的ID
		 int id = cgSlotType12Choose.getId();
	
		 ScatterMultipleTemplate template = Globals.getScatterMultipleService().getTemplate(slot.getType(),id);
		 
		 GCSlotType12Choose message = new GCSlotType12Choose();
		 if(template != null){
			 humanSlotManager.addFreeSlot(template.getFreeSpinNum());
			 humanSlotManager.getHumanTemporaryData().setMultiple(template.getMultiple()/100);
			 message.setFreeNum(template.getFreeSpinNum());
		 }else{
			 logger.info("玩家["+player.getPassportId()+"]没有对应的模板数据 id = "+id);
		 }
		 
		 
		 player.sendMessage(message);
		 
	}

	/**
	 * 维密老虎机自由转完
	 * 
	 * 在自由转动之后才会调用的，进行最终的结算
	 * @param player
	 * @param cgSlotType12Free
	 */
	public void handleSlotType12Free(Player player, CGSlotType12Free cgSlotType12Free) {
		

		Human human = player.getHuman();
		
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();

		//倍数
	    int multiple = humanSlotManager.getHumanTemporaryData().getMultiple();
	    
        //免费时候赢得金币  
	    long reward = (long) humanSlotManager.getHumanTemporaryData().getSlot12FreeTimesWinRewards();
	    
	    reward = reward*multiple;
	    
	    slotFree(player,reward);
	    
	    GCSlotType12Free message = new GCSlotType12Free();
	    message.setRewardNum(reward);
	    message.setMultiple(multiple);
	    player.sendMessage(message);
	    
	   
	    
	}
	
	/**
	 * 免费完了后续处理
	 * @param player
	 * @param reward 免费总的累计奖励
	 */
	private void slotFree(Player player,long reward){
		
        Human human = player.getHuman();
		
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		
		SlotService slotService = Globals.getSlotService();
		
		Slot slot = slotService.getSlotById(humanSlotManager.getCurrentSlotId());
		
		//重新设置免费次数等
        humanSlotManager.resetFreeTimes();
        
        /**
		 * 小游戏获得 的钱
		 * 添加到竞赛里
		 **/
		Globals.getTournamentService().putData(human.getPlayer(), slot.getType(), human.getPassportId(), reward);
		
		//增加钱
		slotLog(human,slot.getTempleId(),false,true,false,reward,humanSlotManager.getCurrentBet());
		//返还记录
		humanSlotManager.refund(slot,reward,0l);
		
		
		//获取模板数据
		SlotsListTemplate tempSlotsListTemplate = slotService.getslotTemplateMap(slot.getTempleId());
		//成就系统
		int num = (int)(humanSlotManager.getCurrentBet()/tempSlotsListTemplate.getPayLinesNum());
		
		this.theWin(human,num , reward, tempSlotsListTemplate, tempSlotsListTemplate.getType());
		//排行榜系统
		Globals.getRankNewServer().win(human, reward);
		// 老虎机快照
		//Globals.getLogService().sendSlotRoomLog(human, LogReasons.SlotRoomLogReason.SLOTROOMKUAIZHAO, "", slot.getTempleId());	
		
		/**
		 * 触发 winner触发的翻倍转盘
		 */
		human.getHumanSlotManager().handleWinner(reward, num, tempSlotsListTemplate,false,1);
	}
	



	/**
	 * 宙斯老虎机抽奖
	 * @param player
	 * @param cgSlotType13BounsPrize
	 */
	public void handleSlotType13BounsPrize(Player player, CGSlotType13BounsPrize cgSlotType13BounsPrize) {
		
		 Human human = player.getHuman();
			
	     HumanSlotManager humanSlotManager = human.getHumanSlotManager();
	     
	     SlotService slotService = Globals.getSlotService();
			
		 Slot slot = slotService.getSlotById(humanSlotManager.getCurrentSlotId());
			
	     //当前可以抽次数
	     int currBouns = humanSlotManager.getHumanTemporaryData().getZsBouns();
	     //当前金额
	     long zsMoney = humanSlotManager.getHumanTemporaryData().getZsMoney();
	     
	     GCSlotType13Bouns message = new GCSlotType13Bouns();
		 message.setBounsNum(currBouns);
		 message.setMoney(zsMoney);
		 message.setIsSuccess(SmallGameType13.Type1.getIndex());//默认成功
		 BounsZeusRewardTemplate temp = Globals.getBounsZeusService().getBzReward(slot.getType());
	   
	     if(currBouns <= 0 || temp == null){
	    	 message.setIsSuccess(SmallGameType13.Type0.getIndex());//失败
	    	 player.sendMessage(message);
	    	 return;
	     }
	     
	     int type = temp.getType();
	     if(type == 1){//奖励单线押注
	    	SlotsListTemplate tempSlotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
	 		//当前单线押注额
	 		int currBet = (int)humanSlotManager.getCurrentBet()/tempSlotsListTemplate.getPayLinesNum();
	 		 
	 		int zsCurrBet = (int)(currBet*temp.getTimes()/100+(int)zsMoney);
	 		 
	 		humanSlotManager.getHumanTemporaryData().setZsBouns(currBouns - 1);
	 		humanSlotManager.getHumanTemporaryData().setZsMoney(zsCurrBet);
	 		
	 		message.setBounsNum(currBouns - 1);
	 		message.setMoney(zsCurrBet);
	 		message.setSingleMoney((int)(currBet*temp.getTimes()/100));
	 		message.setSingleBounsNum(0);
	 		
	 		//小游戏获得钱
	 		slotLog(human,slot.getTempleId(),false,false,true,(int)(currBet*temp.getTimes()/100),humanSlotManager.getCurrentBet());
	 		
	     }else{//赠送免费次数
	    	 int bounsNum = (int) (temp.getTimes()/100+currBouns-1);
	    	 humanSlotManager.getHumanTemporaryData().setZsBouns(bounsNum);
	    	 message.setBounsNum(bounsNum);
	    	 message.setSingleMoney(0);
		 	 message.setSingleBounsNum((int)(temp.getTimes()/100));
	     }
	    
	     player.sendMessage(message);
	     //Loggers.slotLogger.info(JSON.toJSONString(message));
	}

	/**
	 * 狮身人面老虎机 bouns 小游戏
	 * @param player
	 * @param cgSlotType15Bouns
	 */
	public void handleSlotType15Bouns(Player player,
			CGSlotType15Bouns cgSlotType15Bouns) {
		GCSlotType15Bouns gcSlotType15Bouns = new GCSlotType15Bouns();
		Human human = player.getHuman();
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
		
		SlotsListTemplate slotsListTemplate= Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		//获取当前老虎机的赔率
		int currBet = (int)humanSlotManager.getCurrentBet()/slotsListTemplate.getPayLinesNum();

		int rewardPool = cgSlotType15Bouns.getRewardPool();
		//如果rewardPool == 1 说明，新的一轮重新开始，排序重新设置
		if(rewardPool == 1){
			humanSlotManager.getHumanTemporaryData().setSequence(rewardPool);
			humanSlotManager.getHumanTemporaryData().setTotalGold(0l);
		}
		if(humanSlotManager.getHumanTemporaryData().getSequence() != rewardPool){
			logger.warn("[玩家顺序->"+rewardPool+"]-[正常顺序->"+humanSlotManager.getHumanTemporaryData().getSequence()+"]");
			return;
		}
		humanSlotManager.getHumanTemporaryData().setSequence(rewardPool+1);

		//获取相应奖池中的list
		//根据奖池的权值 随机取一个数据
		BounsSphinxRewardTemplate  bsr = Globals.getWildSphinxService().getRewardWeight(rewardPool, SlotTypeEnum.SlotType15.getIndex());
		
		if(bsr == null){
			logger.warn("玩家奖池["+rewardPool+"]为空");
			return;
		}
		//如果 type类型是1和3全部中奖 ，2为不中奖
		if(bsr.getType() == WildSphinxService.type_1 || bsr.getType() == WildSphinxService.type_3){
			//设置到返回值里
			double times = bsr.getTimes()/100;
			double gold = currBet*times;
			Long reward = Double.valueOf(gold).longValue();
			humanSlotManager.getHumanTemporaryData().setTotalGold(reward.longValue()+humanSlotManager.getHumanTemporaryData().getTotalGold());
			//增加钱

			slotLog(human,slot.getTempleId(),false,true,false,reward,humanSlotManager.getCurrentBet());

			gcSlotType15Bouns.setCurrentGold(reward);
			gcSlotType15Bouns.setTotalGold(humanSlotManager.getHumanTemporaryData().getTotalGold());
			gcSlotType15Bouns.setIsSuccess(1);//获取成功
			player.sendMessage(gcSlotType15Bouns);
		}else {
			gcSlotType15Bouns.setCurrentGold(0);
			gcSlotType15Bouns.setTotalGold(humanSlotManager.getHumanTemporaryData().getTotalGold());
			gcSlotType15Bouns.setIsSuccess(0);//失败退出
			player.sendMessage(gcSlotType15Bouns);
		}
		
	}

	public void handleSlotType19Scatter(Player player,
			CGSlotType19Scatter cgSlotType19Scatter) {
		Human human = player.getHuman();
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
		SlotsListTemplate slotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		int curMoney = (int)humanSlotManager.getCurrentBet()/slotsListTemplate.getPayLinesNum();
		ScatterCrystalRewardTemplate scatterCrystalReward  = Globals.getScatterCrystalService().getScatterByWeight();
		
		//判断 此用户的 等级是否足够玩此游戏
		long levelDown = scatterCrystalReward.getLevelDown();
		long levelUp = scatterCrystalReward.getLevelUp();
		long level = human.getLevel();
		GCSlotType19 gCSlotType19 = new GCSlotType19();
		if(level <= levelDown || level >= levelUp){
			gCSlotType19.setId(scatterCrystalReward.getId());
			gCSlotType19.setLevelEnough(ScatterCrystalService.level_enough_no);
			player.sendMessage(gCSlotType19);
			return;
		}else{
			gCSlotType19.setId(scatterCrystalReward.getId());
			gCSlotType19.setLevelEnough(ScatterCrystalService.level_enough_yes);
		}
		int mType = scatterCrystalReward.getType();// 获取的 1：金额  或者次数 2：次数
		if(mType == ScatterCrystalService.type_money){
			gCSlotType19.setMType(ScatterCrystalService.type_money);
			int addMoney = (curMoney*scatterCrystalReward.getRewardNum())/100;
			gCSlotType19.setMt(addMoney);
			//增加钱
			slotLog(human,slot.getTempleId(),false,false,true,addMoney,humanSlotManager.getCurrentBet());
			player.sendMessage(gCSlotType19);
			return;
		}else if(mType == ScatterCrystalService.type_times){
			gCSlotType19.setMType(ScatterCrystalService.type_times);
			int addTimes = scatterCrystalReward.getRewardNum()/100;
			gCSlotType19.setMt(addTimes);
			//增加次数
			humanSlotManager.addFreeSlot(addTimes);
			player.sendMessage(gCSlotType19);
		}
		
	}


	public void handleSlotType21Bouns(Player player,
			CGSlotType21Bouns cgSlotType21Bouns) {
		Human human = player.getHuman();
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
		SlotsListTemplate slotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		int curGold = (int)humanSlotManager.getCurrentBet()/slotsListTemplate.getPayLinesNum();
		//随机获取 卡片模板
		BounsTigerRewardTemplate bounsTigerRewardTemplate = Globals.getBounsTigerService().getRewardBYWeight();
		//临时数据
		HumanTemporaryData humanTemporaryData = humanSlotManager.getHumanTemporaryData();
		List<Integer> tigerPickUpRewards = humanTemporaryData.getTigerPickUpRewards();
		int theTigerTimes = humanTemporaryData.getTheTigerTimes();
		GCSlotType21Bouns gCSlotType21Bouns = new GCSlotType21Bouns();
		//随机获取的奖池
		int rewardPool = bounsTigerRewardTemplate.getRewardPool();
		gCSlotType21Bouns.setRewardPool(rewardPool);
		//只要 翻到相同卡片就结束了
		if(theTigerTimes > 1){//第二次、三次 、四次等等
			for(Integer reward:tigerPickUpRewards){
				if(reward == rewardPool){//如果有匹配的
					int addMoney = (curGold*bounsTigerRewardTemplate.getTimes())/100;
					gCSlotType21Bouns.setTotalGold(addMoney);
					gCSlotType21Bouns.setIsMatch(BounsTigerService.is_mathch_yes);
					//只要 翻到相同卡片就结束了  所以把第几次这个值 置为1
					humanTemporaryData.setTheTigerTimes(1);
					tigerPickUpRewards.clear();//如果匹配上就清空了
					//增加钱
					slotLog(human,slot.getTempleId(),false,false,true,addMoney,humanSlotManager.getCurrentBet());
					player.sendMessage(gCSlotType21Bouns);
					return;
				}
			}
		}
		//如果是第一次肯定不匹配  或者 后边几次 不匹配
		gCSlotType21Bouns.setTotalGold(0);
		gCSlotType21Bouns.setIsMatch(BounsTigerService.is_mathch_no);
		humanTemporaryData.setTheTigerTimes(++theTigerTimes);//第几次 递增
		tigerPickUpRewards.add(bounsTigerRewardTemplate.getRewardPool());
		player.sendMessage(gCSlotType21Bouns);
		return;
	}

	/**
	 * 挖金矿
	 * @param player
	 * @param cgSlotType22
	 */
	public void handleSlotType22(Player player, CGSlotType22 cgSlotType22) {
		
		Human human = player.getHuman();
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		
		int remaining = humanSlotManager.getHumanTemporaryData().getRemaining();
		long rewardNum = humanSlotManager.getHumanTemporaryData().getRewardNum();
		GCSlotType22 message = new GCSlotType22();
		if(remaining > 0){
			int newRemaining = remaining-1;
			message.setRemaining(newRemaining);
			humanSlotManager.getHumanTemporaryData().setRemaining(newRemaining);
			
			Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
			int bet = cgSlotType22.getBet();
			int times = Globals.getScatterCowboyService().getTimes(slot.getType());
			long newRewardNum = rewardNum+bet*times/100;
			
			humanSlotManager.getHumanTemporaryData().setRewardNum(newRewardNum);
//			message.setRewardNum(newRewardNum);
			//增加钱
			slotLog(human,slot.getTempleId(),false,false,true,bet*times/100,humanSlotManager.getCurrentBet());
			
		}else{
			message.setRemaining(0);
//			message.setRewardNum(rewardNum);
		}
		player.sendMessage(message);
		
	}

	/**
	 * 巴西风情老虎机
	 * @param player
	 * @param cgSlotType24BounsPrize
	 */
	public void handleSlotType24BounsPrize(Player player, CGSlotType24BounsPrize cgSlotType24BounsPrize) {
		 Human human = player.getHuman();
			
	     HumanSlotManager humanSlotManager = human.getHumanSlotManager();
	     
	     SlotService slotService = Globals.getSlotService();
			
		 Slot slot = slotService.getSlotById(humanSlotManager.getCurrentSlotId());
			
	     //当前可以抽次数
	     int currBouns = humanSlotManager.getHumanTemporaryData().getBxBouns();
	     //当前金额
	     long bxMoney = humanSlotManager.getHumanTemporaryData().getBxMoney();
	     
	     GCSlotType24Bouns message = new GCSlotType24Bouns();
		 message.setBounsNum(currBouns);
		 message.setMoney(bxMoney);
		 message.setIsSuccess(SmallGameType24.Type1.getIndex());//默认成功
		 BounsZeusRewardTemplate temp = Globals.getBounsZeusService().getBzReward(slot.getType());
	   
	     if(currBouns <= 0 || temp == null){
	    	 message.setIsSuccess(SmallGameType13.Type0.getIndex());//失败
	    	 player.sendMessage(message);
	    	 return;
	     }
	     
	     int type = temp.getType();
	     if(type == 1){//奖励单线押注
	    	SlotsListTemplate tempSlotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
	 		//当前单线押注额
	 		int currBet = (int)humanSlotManager.getCurrentBet()/tempSlotsListTemplate.getPayLinesNum();
	 		 
	 		int zsCurrBet = (int)(currBet*temp.getTimes()/100+(int)bxMoney);
	 		 
	 		humanSlotManager.getHumanTemporaryData().setBxBouns(currBouns - 1);
	 		humanSlotManager.getHumanTemporaryData().setBxMoney(zsCurrBet);
	 		
	 		message.setBounsNum(currBouns - 1);
	 		message.setMoney(zsCurrBet);
	 		message.setSingleMoney((int)(currBet*temp.getTimes()/100));
	 		message.setSingleBounsNum(0);
	 		
	 		//小游戏获得钱
	 		slotLog(human,slot.getTempleId(),false,false,true,(int)(currBet*temp.getTimes()/100),humanSlotManager.getCurrentBet());
	 		
	     }else{//赠送免费次数
	    	 int bounsNum = (int) (temp.getTimes()/100+currBouns-1);
	    	 humanSlotManager.getHumanTemporaryData().setBxBouns(bounsNum);
	    	 message.setBounsNum(bounsNum);
	    	 message.setSingleMoney(0);
		 	 message.setSingleBounsNum((int)(temp.getTimes()/100));
	     }
	    
	     player.sendMessage(message);
	     
	    //Loggers.slotLogger.info(JSON.toJSONString(message));
		
	}

	public void handleSlotType25Bouns(Player player,
			CGSlotType25Bouns cgSlotType25Bouns) {
		
		Human human = player.getHuman();
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		HumanTemporaryData humanTemporaryData = humanSlotManager.getHumanTemporaryData();
		Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
		SlotsListTemplate slotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		long curGold = humanSlotManager.getCurrentBet()/slotsListTemplate.getPayLinesNum();
		//用户 传过来 的选择的位置
		int position = cgSlotType25Bouns.getPosition();
		//随机获取 一个 中奖的对象
		long totalGold = 0;
		GCSlotType25Bouns gCSlotType25Bouns  = new GCSlotType25Bouns();
		List<BonusNinjaRewardTemplate> bonusNinjaRewardTemplateList = Globals.getBonusNinjaService().getBonusNinjaRewardTemplateMap().get(slotsListTemplate.getType());
		List<BonusNinjaRewardTemplate> typeYesList = new ArrayList<BonusNinjaRewardTemplate>();
		List<BonusNinjaRewardTemplate> typeNoList = new ArrayList<BonusNinjaRewardTemplate>();
		for(BonusNinjaRewardTemplate bt:bonusNinjaRewardTemplateList){
			if(bt.getType() == BonusNinjaService.type_yes){
				typeYesList.add(bt);
			}else{
				typeNoList.add(bt);
			}
		}
		//如果用户选择的位置 和 给定的位置一样，则 中奖 大金额，否则 中奖小金额
		if(position == humanTemporaryData.getPosition()){
			BonusNinjaRewardTemplate btYes = Globals.getBonusNinjaService().getBonusNinjaRewardTemplateByWeight(typeYesList,BonusNinjaService.type_yes);
			totalGold=(curGold*btYes.getTimes())/100;
			gCSlotType25Bouns.setIsMatch(1);
		}else{
			BonusNinjaRewardTemplate btNo = Globals.getBonusNinjaService().getBonusNinjaRewardTemplateByWeight(typeNoList,BonusNinjaService.type_no);
			totalGold=(curGold*btNo.getTimes())/100;
			gCSlotType25Bouns.setIsMatch(0);
		}
		gCSlotType25Bouns.setTotalGold(totalGold);
		/**
		 * 小游戏获得 的钱
		 * 添加到竞赛里
		 **/
		Globals.getTournamentService().putData(human.getPlayer(), slot.getType(), human.getPassportId(), totalGold);
		SlotBase slotBase = SlotBase.getSlot(slot.getType());
		slotBase.setSlot(slot);
		//小游戏获得钱
		slotBase.slotLog(human,slot.getTempleId(),false,true,false,totalGold,humanSlotManager.getCurrentBet());
		player.sendMessage(gCSlotType25Bouns);
	}

	public void handleSlotType26Bouns(Player player,
			CGSlotType26Bouns cgSlotType26Bouns) {
		Human human = player.getHuman();
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		HumanTemporaryData humanTemporaryData = humanSlotManager.getHumanTemporaryData();
		Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
		SlotsListTemplate slotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		int curGold = (int)humanSlotManager.getCurrentBet()/slotsListTemplate.getPayLinesNum();
		//随机获取 一个 中奖的对象
		GCSlotType26Bouns gCSlotType26Bouns  = new GCSlotType26Bouns();
		int totalGold = 0;
		int times  = Globals.getMagicSymbolService().getBonus(slotsListTemplate.getType());
		totalGold=(curGold*times)/100;
		gCSlotType26Bouns.setTotalGold(totalGold);
		//小游戏获得钱
 		slotLog(human,slot.getTempleId(),false,true,false,totalGold,humanSlotManager.getCurrentBet());
		player.sendMessage(gCSlotType26Bouns);
		
	}

	public void handleSlotType23InitReward(Player player,
			CGSlotType23InitReward cgSlotType23InitReward) {
		//龙头的逻辑
		
		Human human = player.getHuman();
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
		int curGold = cgSlotType23InitReward.getBet();
		String bets  = slot.getSlotType23Jackpot();
		if(!StringUtils.isBlank(bets)){
			String[] betArr = bets.split(";");
			GCSlotType23InitReward gCSlotType23InitReward= new GCSlotType23InitReward();
			for(int i=0;i<betArr.length;i++){
				String betStr = betArr[i];
				String[] b = betStr.split("=");
				int bb = Integer.valueOf(b[0]).intValue();
				String str = b[1];
				if(curGold == bb){
					gCSlotType23InitReward.setJackpotInfo(str);
					break;
				}
			}
			player.sendMessage(gCSlotType23InitReward);
		}
		
	}
/*	
 * 原先的  非共享 初始化奖池
 * public void handleSlotType23InitReward(Player player,
			CGSlotType23InitReward cgSlotType23InitReward) {
		//龙头的逻辑
		
		Human human = player.getHuman();
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		HumanSlot humanSlot = humanSlotManager.getHumanSlotBySlotId(humanSlotManager.getCurrentSlotId());
		Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
		SlotsListTemplate slotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		int curGold = cgSlotType23InitReward.getBet();
		int bet1 = slotsListTemplate.getBet1();
		int bet2 = slotsListTemplate.getBet2();
		int bet3 = slotsListTemplate.getBet3();
		int bet4 = slotsListTemplate.getBet4();
		int bet5 = slotsListTemplate.getBet5();
		//获取奖池的基数
		//先从临时数据里边 奖池的数据，如果是空的 ，再去数据库里取数据
		String	jackpot = humanSlot.getSlotType23Jackpot();
		GCSlotType23InitReward gCSlotType23InitReward= new GCSlotType23InitReward();
		JackpotDragonService jackpotDragonService = Globals.getJackpotDragonService();
		if(StringUtils.isNotBlank(jackpot)){
			String[] rewards = jackpot.split(";");
			for(int i = 0;i<rewards.length;i++){
				String[] bet_reward = rewards[i].split("=");
				String reward = bet_reward[1];
				int bet = Integer.valueOf(bet_reward[0]).intValue();
				if(curGold == 0 && bet == bet1){//如果当前下注是零，那么取最小的
					String reReward = jackpotDragonService.sortJackPort(reward);
					gCSlotType23InitReward.setJackpotInfo(reReward);
					break;
				}
				if(bet == curGold){
					String reReward = jackpotDragonService.sortJackPort(reward);
					gCSlotType23InitReward.setJackpotInfo(reReward);
					break;
				}
			}
			player.sendMessage(gCSlotType23InitReward);
			return;
		}
		//顺序取出所有的奖池
		List<JackpotDragonTemplate> jackpotDragons = jackpotDragonService.getJackpotDragonTemplateList();
		//bet1 的奖池
		String jackpotSingleList_bet1 = "";
		for(JackpotDragonTemplate jackpotDragonTemplate:jackpotDragons){
			jackpotSingleList_bet1+=(bet1*jackpotDragonTemplate.getInitial())/100+",";
		}
		jackpotSingleList_bet1 = bet1+"="+jackpotSingleList_bet1.substring(0,jackpotSingleList_bet1.length()-1);
		
		//bet2 的奖池
		String jackpotSingleList_bet2 = "";
		for(JackpotDragonTemplate jackpotDragonTemplate:jackpotDragons){
			jackpotSingleList_bet2+=(bet2*jackpotDragonTemplate.getInitial())/100+",";
		}
		jackpotSingleList_bet2 = bet2+"="+jackpotSingleList_bet2.substring(0,jackpotSingleList_bet2.length()-1);
		
		//bet3 的奖池
		String jackpotSingleList_bet3 = "";
		for(JackpotDragonTemplate jackpotDragonTemplate:jackpotDragons){
			jackpotSingleList_bet3+=(bet3*jackpotDragonTemplate.getInitial())/100+",";
		}
		jackpotSingleList_bet3 = bet3+"="+jackpotSingleList_bet3.substring(0,jackpotSingleList_bet3.length()-1);
		
		//bet4 的奖池
		String jackpotSingleList_bet4 = "";
		for(JackpotDragonTemplate jackpotDragonTemplate:jackpotDragons){
			jackpotSingleList_bet4+=(bet4*jackpotDragonTemplate.getInitial())/100+",";
		}
		jackpotSingleList_bet4 = bet4+"="+jackpotSingleList_bet4.substring(0,jackpotSingleList_bet4.length()-1);
		
		//bet5 的奖池
		String jackpotSingleList_bet5 = "";
		for(JackpotDragonTemplate jackpotDragonTemplate:jackpotDragons){
			jackpotSingleList_bet5+=(bet5*jackpotDragonTemplate.getInitial())/100+",";
		}
		jackpotSingleList_bet5 = bet5+"="+jackpotSingleList_bet5.substring(0,jackpotSingleList_bet5.length()-1);
		
		
		String finalFiveRewards = jackpotSingleList_bet1+";"+jackpotSingleList_bet2+";"+jackpotSingleList_bet3+";"+jackpotSingleList_bet4+";"+jackpotSingleList_bet5;
		//初始化奖池到数据库
		humanSlot.setSlotType23Jackpot(finalFiveRewards);
		humanSlot.setModified();//GCSlotType23BounsInfo
		
		if(curGold == bet1){
			String reReward = jackpotDragonService.sortJackPort(jackpotSingleList_bet1.replace(bet1+"=", ""));
			gCSlotType23InitReward.setJackpotInfo(reReward);
		}else if(curGold == bet2){
			String reReward = jackpotDragonService.sortJackPort(jackpotSingleList_bet2.replace(bet2+"=", ""));
			gCSlotType23InitReward.setJackpotInfo(reReward);
		}else if(curGold == bet3){
			String reReward = jackpotDragonService.sortJackPort(jackpotSingleList_bet3.replace(bet3+"=", ""));
			gCSlotType23InitReward.setJackpotInfo(reReward);
		}else if(curGold == bet4){
			String reReward = jackpotDragonService.sortJackPort(jackpotSingleList_bet4.replace(bet4+"=", ""));
			gCSlotType23InitReward.setJackpotInfo(reReward);
		}else if(curGold == bet5){
			String reReward = jackpotDragonService.sortJackPort(jackpotSingleList_bet5.replace(bet5+"=", ""));
			gCSlotType23InitReward.setJackpotInfo(reReward);
		}else{//当前的下注额是0 ,所以取最小的
			String reReward = jackpotDragonService.sortJackPort(jackpotSingleList_bet1.replace(bet1+"=", ""));
			gCSlotType23InitReward.setJackpotInfo(reReward);
		}
		player.sendMessage(gCSlotType23InitReward);
		
	}
*/
	public void handleSlotType28Bouns(Player player,
			CGSlotType28Bouns cgSlotType28Bouns) {
		Human human = player.getHuman();
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		HumanSlot humanSlot = humanSlotManager.getHumanSlotBySlotId(humanSlotManager.getCurrentSlotId());
		Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
		SlotsListTemplate slotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		int curGold = (int)humanSlotManager.getCurrentBet()/slotsListTemplate.getPayLinesNum();
		List<BonusOceanRewardTemplate> bonusOceanRewardTemplates = Globals.getBonusOceanService().getBonusOceanRewardTemplateMap().get(slotsListTemplate.getType());
		BonusOceanRewardTemplate bonusOceanRewardTemplate = Globals.getBonusOceanService().getRewardWeight(slotsListTemplate.getType());
		int totalGold = 0;
		int type = bonusOceanRewardTemplate.getType();
		int rewardNum = bonusOceanRewardTemplate.getRewardNum();
		GCSlotType28Bouns cGSlotType28Bouns = new GCSlotType28Bouns();
		cGSlotType28Bouns.setRewardId(bonusOceanRewardTemplate.getId());
//		cGSlotType28Bouns.setBackType(type);
		if(type ==1 || type == 3){//直接赠送奖金
			totalGold = (curGold*rewardNum)/100;
			slotLog(human,slot.getTempleId(),false,true,false,totalGold,humanSlotManager.getCurrentBet());
//			cGSlotType28Bouns.setGoldOrTimes(totalGold);
		}else if(type == 2){//赠送转动次数
			totalGold = rewardNum/100;
			humanSlotManager.addFreeSlot(totalGold);
//			cGSlotType28Bouns.setGoldOrTimes(totalGold);
		}
		player.sendMessage(cGSlotType28Bouns);
		
	}

	public void handleSlotType29Bouns(Player player,
			CGSlotType29Bouns cgSlotType29Bouns) {
		Human human = player.getHuman();
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		HumanSlot humanSlot = humanSlotManager.getHumanSlotBySlotId(humanSlotManager.getCurrentSlotId());
		HumanTemporaryData humanTemporaryData= humanSlotManager.getHumanTemporaryData();
		Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
		SlotsListTemplate slotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		int curGold = (int)humanSlotManager.getCurrentBet()/slotsListTemplate.getPayLinesNum();
		int totalGold = 0;
		//先获取权值LIST
		List<Integer> weightList= new ArrayList<Integer>();
		List<BounsZeusRewardTemplate> bounsZeusRewardTemplateList= Globals.getBounsZeusService().getZousReward(slotsListTemplate.getType());
		List<BounsZeusRewardTemplate> leftBounsZeusRewardTemplateList = new ArrayList<BounsZeusRewardTemplate>();
		
		for(BounsZeusRewardTemplate bz :bounsZeusRewardTemplateList){
			boolean exist = false;
			for(BounsZeusRewardTemplate BounsZeusRewardTemplate:humanTemporaryData.getExistBounsZeusRewardTemplateList()){
				if(bz.getId() == BounsZeusRewardTemplate.getId()){
					exist =true;
					continue;
				}
			}
			if(!exist){
				leftBounsZeusRewardTemplateList.add(bz);
			}
		}
		for(BounsZeusRewardTemplate bz :leftBounsZeusRewardTemplateList){
			weightList.add(bz.getWeight());
		}
		BounsZeusRewardTemplate  bounsZeusRewardTemplate= Globals.getBounsZeusService().getBzRewardDragon(leftBounsZeusRewardTemplateList,weightList);
		humanTemporaryData.getExistBounsZeusRewardTemplateList().add(bounsZeusRewardTemplate);
		humanTemporaryData.getExistWeightList().add(bounsZeusRewardTemplate.getWeight());
		//前段传过来的位置
		GCSlotType29Bouns gCSlotType29Bouns = new GCSlotType29Bouns();
		List<Integer> goldList = new ArrayList<Integer>();
		gCSlotType29Bouns.setIsSon(0);
		int type = bounsZeusRewardTemplate.getType();
		int id = bounsZeusRewardTemplate.getId();
		if(type == 2){//抽到龙子 
			Double timesAll = 0.0;
			for(BounsZeusRewardTemplate bt:leftBounsZeusRewardTemplateList){
				if(bt.getType() != type){
					Double dd = (curGold*bt.getTimes())/100;
					goldList.add(dd.intValue());
					timesAll +=bt.getTimes();
				}
			}
			totalGold = (curGold*timesAll.intValue())/100;
			gCSlotType29Bouns.setIsSon(1);
			humanTemporaryData.getExistBounsZeusRewardTemplateList().clear();//每次结束清空所有的IDS
			humanTemporaryData.setDragonTime(0);
		}else if(humanTemporaryData.getDragonTime()==3){//玩到第三次 
			for(BounsZeusRewardTemplate bt:leftBounsZeusRewardTemplateList){
				if(bt.getId() != id && bt.getType() != 2){
					Double dd = (curGold*bt.getTimes())/100;
					goldList.add(dd.intValue());
				}
			}
			Double timesAll =bounsZeusRewardTemplate.getTimes();
			totalGold = (curGold*timesAll.intValue())/100;
			goldList.add(0,totalGold);
			gCSlotType29Bouns.setIsSon(0);
			humanTemporaryData.getExistBounsZeusRewardTemplateList().clear();//每次结束清空所有的IDS
			humanTemporaryData.setDragonTime(0);
		}else{
			Double timesAll = bounsZeusRewardTemplate.getTimes();
			totalGold = (curGold*timesAll.intValue())/100;
			goldList.add(totalGold);
		}
		slotLog(human,slot.getTempleId(),false,true,false,totalGold,humanSlotManager.getCurrentBet());
		int[] goldArray = new int[goldList.size()];
		for(int i=0;i<goldList.size();i++){
			goldArray[i]=goldList.get(i).intValue();
		}
		humanTemporaryData.setDragonTime(humanTemporaryData.getDragonTime()+1);
		gCSlotType29Bouns.setGold(goldArray);
		player.sendMessage(gCSlotType29Bouns);
		
		
		
	}

	public void handleSlotType30Bouns(Player player,
			CGSlotType30Bouns cgSlotType30Bouns) {
		Human human = player.getHuman();
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		HumanSlot humanSlot = humanSlotManager.getHumanSlotBySlotId(humanSlotManager.getCurrentSlotId());
		HumanTemporaryData humanTemporaryData= humanSlotManager.getHumanTemporaryData();
		Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
		SlotsListTemplate slotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		int curGold = (int)humanSlotManager.getCurrentBet()/slotsListTemplate.getPayLinesNum();
		int totalGold = 0;
		
		GCSlotType30Bouns gCSlotType30Bouns = new GCSlotType30Bouns();
		int holmesTimes = humanTemporaryData.getHolmesTimes();
		if(holmesTimes == 1){
			humanTemporaryData.setHolmestotalGolds(0);
		}
		Map<Integer, List<BonusHolmesRewardTemplate>>  bhrtMap=Globals.getBonusHolmesService().getBonusHolmesRewardTemplateMap();
		List<BonusHolmesRewardTemplate> bonusHolmesRewardTemplateList=bhrtMap.get(slot.getType());
		for(BonusHolmesRewardTemplate bhr:bonusHolmesRewardTemplateList){
			int num = bhr.getNum();
			//如果是第一次 ，第二次，第三次.... 进行计算
			if(num == holmesTimes){
				int rewardNum = bhr.getRewardNum();
				int probability = bhr.getProbability();
				boolean isWin = false;
				if(probability >= 100){
					isWin = true;
				}else{
					Random random = new Random();
					int rand = random.nextInt(100);
					if(rand <= probability){
						isWin =true;
					}
				}
				if(isWin){
					totalGold+=(curGold*rewardNum)/100;
					gCSlotType30Bouns.setGold(totalGold);
					gCSlotType30Bouns.setIsSingleWin(1);
					slotLog(human,slot.getTempleId(),false,true,false,totalGold,humanSlotManager.getCurrentBet());
					humanTemporaryData.setHolmestotalGolds(humanTemporaryData.getHolmestotalGolds()+totalGold);
				}else{
					gCSlotType30Bouns.setIsSingleWin(0);
					humanTemporaryData.setHolmesAllWin(0);
				}
				break;
			}
		}
		gCSlotType30Bouns.setTotalGold(humanTemporaryData.getHolmestotalGolds());
		gCSlotType30Bouns.setIsAllWin(humanTemporaryData.getHolmesAllWin());
		
		if(holmesTimes == 4){
			holmesTimes = 1;
			humanTemporaryData.setHolmesTimes(holmesTimes);
			humanTemporaryData.setHolmestotalGolds(0);
		}else{
			holmesTimes++;
			humanTemporaryData.setHolmesTimes(holmesTimes);
		}
		player.sendMessage(gCSlotType30Bouns);
		
		
	}

	/**
	 * 通过房间进入老虎机
	 * @param player
	 * @param cgSlotEnterRoom
	 */
	public void handleSlotEnterRoom(Player player, CGSlotEnterRoom cgSlotEnterRoom) {
		
		//老虎机ID
		int newSlotId = cgSlotEnterRoom.getSlotId();
		
		//获取老虎机
		Slot slot = Globals.getSlotService().getSlotByTemplateId(newSlotId);
		if(slot ==null){
			logger.warn("玩家["+player.getPassportId()+"]请求的老虎机不存在");
			return;
		}
		
		Human human = player.getHuman();
		
		GCSlotEnter gcSlotEnter = new GCSlotEnter();
		gcSlotEnter.setSlotId(newSlotId);
		
		int result = 0;
		//等级判断
		if(!Globals.getSlotService().isLevelById(newSlotId, human.getLevel())){
			result = 1;
			logger.warn("玩家["+player.getPassportId()+"]进入老虎机等级不足");
			gcSlotEnter.setResultType(result);
			player.sendMessage(gcSlotEnter);
			return;
		}
		//筹码判断
		if(!Globals.getSlotService().isGoldById(newSlotId, human.getGold())){
			result = 2;
			logger.warn("玩家["+player.getPassportId()+"]进入老虎机筹码不足");
			gcSlotEnter.setResultType(result);
			player.sendMessage(gcSlotEnter);
			return;
		}
		
		int oldSlotId = human.getHumanSlotManager().getSlotId();
		String oldRoomid = human.getSlotRoomId();
		String newRoomid = cgSlotEnterRoom.getRoomId();
		
		if(Globals.getServerComm().isAuthority() && result == 0){
			String str = Globals.getSlotRoomService().enterSlotForRoomId(human.getPassportId(),newSlotId,oldSlotId,newRoomid,oldRoomid);
			 Globals.getSlotRoomService().enterSlotzz(player,str,slot,newRoomid,newSlotId);
			
		}else{
			EnterSlotRoom message = new EnterSlotRoom();
			message.setPassportId(human.getPassportId());
			message.setNewRoomId(newRoomid);
			message.setNewSlotId(newSlotId);
			message.setOldRoomId(oldRoomid);
			message.setOldSlotId(oldSlotId);
			message.setServerid(Globals.getServerConfig().getServerId());
			Globals.getRedisService().sendRedisMsgToServer(Globals.getServerComm().getMinServerId(), message);
		}
		
	
		
	}

	public void handleGetSlotCachemsg(Player player, CGGetSlotCachemsg cgGetSlotCachemsg) {
		
	
		Human human = player.getHuman();
		GCMessage message = human.getHumanSlotManager().getMsgCache().getMessage();
		if(message != null){
			player.sendMessage(message);
		}else{
			GCGetSlotCachemsg messagenot = new GCGetSlotCachemsg();
			player.sendMessage(messagenot);
			logger.error("玩家 id 【"+human.getPassportId()+"】  请求的消息 不存在!!!");
		}
		
	}

	public void handleDemoType(Player player, CGDemoType cgDemoType) {
		
		Human human = player.getHuman();
		
		HumanSlotManager manager = human.getHumanSlotManager();
		
		int slotId = manager.getSlotId();
		
		SlotsListTemplate template = Globals.getSlotService().getslotTemplateMap(slotId);
		
		int slotType = template.getType();
		
		int type = cgDemoType.getDemoType();
		
		if(type == 200){
			human.giveMoney(100000000, Currency.GOLD, true, LogReasons.GoldLogReason.BUY_COINS, LogReasons.GoldLogReason.BUY_COINS.getReasonText(), -1, 1);
			return;
		}
		
		List<Integer> list = new ArrayList<Integer>();
		if(type == 11){
			list = Globals.getSlotService().randomElementsRowsAny(slotType,(int)human.getLevel());
		}else {
			int returnType = Globals.getSlotService().slotElemByType(slotType,type);
			list = Globals.getSlotService().getListPost(slotType,(int)human.getLevel(),SlotElementType.indexOf(returnType),type);
		}

		if(list.isEmpty()){
			logger.error("玩家 id 【"+human.getPassportId()+"】  请求的demo 类型"+type+" 不存在");
			return;
		}
		manager.setRandomIntListDEMO(list);
		manager.getCurrentSlotPosList().clear();
	}


	/**
	 * 自己获奖记录
	 * @param player
	 * @param cgSngRankInfo
	 */
	public void handleSngRankInfo(Player player, CGSngRankInfo cgSngRankInfo) {
		
		Human human = player.getHuman();
		
		List<SngInfo> list = human.getSnginfos();
		
		HumanSngInfo[] info = new HumanSngInfo[list.size()];
		
		for(int i = 0;i < list.size();i++){
			
			SngInfo sngInfo = list.get(i);
			HumanSngInfo sh = new HumanSngInfo();
			sh.setRank(sngInfo.getRank());
			sh.setRewardNum(sngInfo.getRewardNum());
			sh.setRewardTime(sngInfo.getRewardTime());
			sh.setTournamentType(sngInfo.getTournamentType());
			info[i] = sh;
		}
		
		GCSngRankInfo message = new GCSngRankInfo();
		message.setHumanSngInfos(info);
		player.sendMessage(message);
		
	}

	/**
	 * 海盗老虎机
	 * @param player
	 * @param cgSlotType31Bouns
	 */
	public void handleSlotType31Bouns(Player player,
			CGSlotType31Bouns cgSlotType31Bouns) {
		Human human= player.getHuman();
		HumanSlotManager humanSlotManager =  human.getHumanSlotManager();
		int whichNum = cgSlotType31Bouns.getWhichNum();
		Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
		int slotId = slot.getType();
		SlotsListTemplate slotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		long curGold = humanSlotManager.getCurrentBet()/slotsListTemplate.getPayLinesNum();
		BounsPirateService bounsPirateService = Globals.getBounsPirateService();
		SlotBase slotBase = SlotBase.getSlot(slot.getType());
		//海盗交锋
		if(whichNum == 1){
			/**
			 * 1代表选船长（炮弹数量）
			 * 2代表选船（奖励倍数）
			 */
			//先选出炮弹的数量（选船长）
			int bulletType = 1;
			List<Integer> weightList = bounsPirateService.getPirate1Step1WeightList(slotId,bulletType);
			List<BounsPirate1Step1Template> bounsPirate1Step1List = bounsPirateService.getSlotNumBounsPirate1Step1ListBySlotNum(slotId,bulletType);
			BounsPirate1Step1Template bounsPirate1Step1Template = (BounsPirate1Step1Template)bounsPirateService.getPost(bounsPirate1Step1List, weightList);
			int few = bounsPirate1Step1Template.getNum();
			//再选出翻倍的 倍数（选战舰）
			int battleshipType = 2;
			List<Integer> weightList2 = bounsPirateService.getPirate1Step1WeightList(slotId,battleshipType);
			List<BounsPirate1Step1Template> bounsPirate1Step1List2 = bounsPirateService.getSlotNumBounsPirate1Step1ListBySlotNum(slotId,battleshipType);
			BounsPirate1Step1Template bounsPirate1Step1Template2 = (BounsPirate1Step1Template)bounsPirateService.getPost(bounsPirate1Step1List2, weightList2);
			int multiple = bounsPirate1Step1Template2.getNum();
			
			//根据few 来获取 奖励值
			List<Integer> rewardWeightList = bounsPirateService.getPirate1Step2WeightList(slotId);
			List<BounsPirate1Step2Template> bounsPirate1Step2List = bounsPirateService.getSlotNumBounsPirate1Step2ListBySlotNum(slotId);
			int totalFew =few;
			long totalGolds = 0l;
			List<Integer> typeList = new ArrayList<Integer>();
			List<Long> goldsOrNumList = new ArrayList<Long>();
			for(int i=0;i<totalFew;i++){
				BounsPirate1Step2Template bounsPirate1Step2Template = (BounsPirate1Step2Template)bounsPirateService.getPost(bounsPirate1Step2List, rewardWeightList);
				int type = bounsPirate1Step2Template.getRewardType();
				long rewardNum = bounsPirate1Step2Template.getRewardNum();
				typeList.add(type);
				// type 0 是金币    1是炮弹
				if(type == 0){
					totalGolds+=curGold*rewardNum*multiple/10000;
					goldsOrNumList.add(curGold*rewardNum/100);
				}else if(type == 1){
					goldsOrNumList.add(rewardNum);
					totalFew+=rewardNum;
				}
			}
			slotBase.setSlot(slot);
			slotBase.slotLog(human,slot.getTempleId(),false,true,false,totalGolds,humanSlotManager.getCurrentBet());
			
			GCSlotType31BonusOne gCSlotType31BonusOne = new GCSlotType31BonusOne();
			gCSlotType31BonusOne.setFirstFew(few);
			int[] typeArr = new int[typeList.size()];
			for(int i=0;i<typeList.size();i++){
				typeArr[i]=typeList.get(i);
			}
			long[] goldsOrNumArr = new long[goldsOrNumList.size()];
			for(int i=0;i<goldsOrNumList.size();i++){
				goldsOrNumArr[i]=goldsOrNumList.get(i);
			}
			gCSlotType31BonusOne.setTypeList(typeArr);
			gCSlotType31BonusOne.setMultiple(multiple/100);
			gCSlotType31BonusOne.setGoldsOrNumList(goldsOrNumArr);
			player.sendMessage(gCSlotType31BonusOne);
		//海岛钓鱼
		}else if(whichNum == 2){
			/**
			 * 三个集合 顺序保持一致
			 */
			//奖池 集合 根据表配置有几个 是几个
			List<Integer> rewardPoolList = new ArrayList<Integer>(); 
			//钓鱼的轨迹
			List<Integer> matchNumList = new ArrayList<Integer>();
			long finalGold = 0l;
			List<Integer> weightList = bounsPirateService.getBounsPirate2WeightList(slotId);
			List<BounsPirate2RewardTemplate> bounsPirate2RewardList = bounsPirateService.getSlotNumBounsPirate2RewardMap(slotId);
			//初始化
			for(int i=0;i<bounsPirate2RewardList.size();i++){
				rewardPoolList.add(bounsPirate2RewardList.get(i).getMultiplier()/100);
			}
			//需要几个相等的
			int sameNum = bounsPirateService.getSameNum(slotId);
			//此处 的100 就是足够大的意思 保证选取次数 在这个数量级里就可以了
			for(int i=0;i<100;i++){
				BounsPirate2RewardTemplate bounsPirate2RewardTemplate = (BounsPirate2RewardTemplate)bounsPirateService.getPost(bounsPirate2RewardList, weightList);
				int mul = bounsPirate2RewardTemplate.getMultiplier();
				int rewardPool = bounsPirate2RewardTemplate.getRewardPool()-1;
				matchNumList.add(rewardPool);
				//只要等于3就匹配了
				
				//判断这个元素有几个了 ，如果 有 这么多 sameNum，就结束
				int allExistNum = 0;
				for(int j=0;j<matchNumList.size();j++){
					if(rewardPool == matchNumList.get(j)){
						allExistNum++;
					}
				}
				if(allExistNum >= sameNum){
					finalGold=mul*curGold/100;
					slotBase.setSlot(slot);
					slotBase.slotLog(human,slot.getTempleId(),false,true,false,finalGold,humanSlotManager.getCurrentBet());
					break;
				}
			}
			
			int[] matchNumArr = new int[matchNumList.size()];
			for(int i=0;i<matchNumList.size();i++){
				matchNumArr[i]=matchNumList.get(i);
			}
			int[] rewardPoolArr = new int[rewardPoolList.size()];
			for(int i=0;i<rewardPoolList.size();i++){
				rewardPoolArr[i]=rewardPoolList.get(i);
			}
			GCSlotType31BonusTwo gCSlotType31BonusTwo = new GCSlotType31BonusTwo();
			gCSlotType31BonusTwo.setRewardPoolList(rewardPoolArr);
			gCSlotType31BonusTwo.setMatchNumList(matchNumArr);
			gCSlotType31BonusTwo.setGold(finalGold);
			player.sendMessage(gCSlotType31BonusTwo);
			
		//宝藏探秘
		}else if(whichNum == 3){
			int startNum = 5;
			List<Integer> rewardTypeList = new ArrayList<Integer>();
			List<Long> rewardNumList = new ArrayList<Long>();
			List<Long> totalGolds = new ArrayList<Long>();
			totalGolds.add(0l);
			List<Integer> times = new ArrayList<Integer>();
			times.add(0, startNum);
			List<Integer> levels = new ArrayList<Integer>();
			levels.add(0, 1);
			List<BounsPirate3RewardTemplate> existList = new ArrayList<BounsPirate3RewardTemplate>();
			while(true){
				int num = getTypeAndNum(bounsPirateService,slotId,levels,totalGolds,times,rewardTypeList,rewardNumList,existList);
				if(num == -1){
					break;
				}
				//每转动一次 -1
			}
			
			slotBase.setSlot(slot);
			slotBase.slotLog(human,slot.getTempleId(),false,true,false,totalGolds.get(0),humanSlotManager.getCurrentBet());
			GCSlotType31BonusThree gCSlotType31BonusThree = new GCSlotType31BonusThree();
			gCSlotType31BonusThree.setWhichNum(levels.get(0));
			
			int[] rewardTypeArr = new int[rewardTypeList.size()];
			for(int i=0;i<rewardTypeList.size();i++){
				rewardTypeArr[i]=rewardTypeList.get(i);
			}
			long[] rewardNumArr = new long[rewardNumList.size()];
			for(int i=0;i<rewardNumList.size();i++){
				rewardNumArr[i]=rewardNumList.get(i);
			}
			gCSlotType31BonusThree.setRewardTypeList(rewardTypeArr);
			gCSlotType31BonusThree.setRewardNumList(rewardNumArr);
			gCSlotType31BonusThree.setStartNum(startNum);
			player.sendMessage(gCSlotType31BonusThree);
		}
	}
	
	private int getTypeAndNum(BounsPirateService bounsPirateService,int slotType,List<Integer> levels,List<Long> totalGolds,List<Integer> times,
			List<Integer> rewardTypeList,List<Long> rewardNumList,List<BounsPirate3RewardTemplate> existList){
		int time = times.get(0);
		if(time==0){
			return -1;
		}
		--time;
		times.set(0, time);
		//每次取出来的数据（出现过的去掉了）
		List<Integer> theWeightList = new ArrayList<Integer>();
		List<BounsPirate3RewardTemplate> theBounsPirate1RewardTemplateList= new ArrayList<BounsPirate3RewardTemplate>();
		//去掉以前出现过的元素
		List<BounsPirate3RewardTemplate> bounsPirate1RewardTemplateList= bounsPirateService.getSlotNumBounsPirate3RewardMap().get(slotType);
		for(BounsPirate3RewardTemplate bounsPirate3RewardTemplate:bounsPirate1RewardTemplateList){
			int level = bounsPirate3RewardTemplate.getLevel();
			if(level == levels.get(0)){
				if(!existList.contains(bounsPirate3RewardTemplate)){
					theBounsPirate1RewardTemplateList.add(bounsPirate3RewardTemplate);
					theWeightList.add(bounsPirate3RewardTemplate.getWeight());
				}
			}
		}
		
		
		BounsPirate3RewardTemplate bounsPirate3RewardTemplate = (BounsPirate3RewardTemplate)bounsPirateService.getPost(theBounsPirate1RewardTemplateList, theWeightList);
		existList.add(bounsPirate3RewardTemplate);
		int rewardType = bounsPirate3RewardTemplate.getRewardType();
		long rewardNum = bounsPirate3RewardTemplate.getRewardNum();
		rewardTypeList.add(rewardType);
		rewardNumList.add(rewardNum);
		if(rewardType == 0){
			totalGolds.set(0, totalGolds.get(0)+rewardNum);
			//增加次数
		}else if(rewardType == 1){
			time+=rewardNum;
			times.set(0, time);
			//进入下一关时 也要给奖金
		}else if(rewardType == 2){
			int level = levels.get(0);
			level+=1;
			levels.set(0, level);
			totalGolds.set(0, totalGolds.get(0)+rewardNum);
			existList.clear();
			return getTypeAndNum(bounsPirateService,slotType,levels,totalGolds,times,rewardTypeList,rewardNumList,existList);
			
		}
		
		return 0;
		
		
		
	}

	public void handleSlotTournamentStart(Player player,
			CGSlotTournamentStart cgSlotTournamentStart) {
		//竞赛类型
		int tournamentType = cgSlotTournamentStart.getTournamentType();
		Globals.getTournamentService().openPanel(tournamentType, player.getPassportId());
		if(Globals.getTournamentService().isOPen(tournamentType)){
		  Globals.getTournamentService().enterTheContest(player,tournamentType);
		}else{
		  player.sendMessage(new GCSlotTournamentNotOpen());
		}
		
	}
	
	


	/**
	 * 关闭竞赛面板
	 * @param player
	 * @param cgCloseSng
	 */
	public void handleSlotTournamentClose(Player player,
			CGSlotTournamentClose cgSlotTournamentClose) {
		int tournamentType = cgSlotTournamentClose.getTournamentType();
		
		Globals.getTournamentService().closePanel(tournamentType, player.getPassportId());
		
	}

	public void handleTournamentGetList(Player player,
			CGTournamentGetList cgTournamentGetList) {
		Globals.getTournamentService().setUserIdWhenGetTourmentList(player.getPassportId());
		List<SngTournamentData> list = Globals.getTournamentService().getSngBriefData();
		GCMessage message = Globals.getTournamentService().getGCSngEnter(list);
		player.sendMessage(message);
		
	}

	public void handleSlotType24BounsGameGo(Player player,
			CGSlotType24BounsGameGo cgSlotType24BounsGameGo) {
		
		
		Human human= player.getHuman();
		HumanSlotManager humanSlotManager =  human.getHumanSlotManager();
		Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
		SlotsListTemplate slotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		long curGold = humanSlotManager.getCurrentBet()/slotsListTemplate.getPayLinesNum();
		SlotBase slotBase = SlotBase.getSlot(slot.getType());
		slotBase.setSlot(slot);
		/**
		 * 小游戏类型  1：喝酒小游戏 2：桑巴小游戏
		 */
		int gameType = cgSlotType24BounsGameGo.getGameType();
		/**
		 * 1：喝酒小游戏
		 */
		if(gameType == 1){
			//总共多少轮
			int round = Globals.getBonusBrazilService().getRoundBySlotType(slot.getType());
			int chance = Globals.getBonusBrazilService().getChanceBySlotType(slot.getType(),gameType);
			int hasChance = 0;
			long totalGold = 0l;
			/** 是否是金币  0：不是，1：是 */
			List<Integer> isRewards = new ArrayList<Integer>();
			/** 获得的金币数量集合 */
			List<Long> rewards = new ArrayList<Long>();
			//总共过了多少关
			int totalPassRound = 0;
			/**
			 * 一轮 摇一次骰子
			 */
			for(int i=0;i<round;i++){
				totalPassRound++;
				BonusBrazilBarGameTemplate bonusBrazilBarGameTemplate= Globals.getBonusBrazilService().getBrazilBarGameBySlotType(slot.getType(), i+1);
				/**
				 * 是否减生命值 （是否有酒） 0表是否，1表示是
				 */
				int alcohol = bonusBrazilBarGameTemplate.getAlcohol();
				long reward = bonusBrazilBarGameTemplate.getReward();
				if(alcohol >= 1){
					hasChance++;
					//摇到酒的次数 超了 就 玩完
					isRewards.add(0);
					if(hasChance>=chance){
						rewards.add(reward);
						totalGold+=reward;
						break;
					}
				}else{
					isRewards.add(1);
				}
				
				/**
				 * 如果没有摇到 酒 那就是摇到金币了
				 */
				rewards.add(reward);
				totalGold+=reward;
			}
			
			/**
			 * 如果 完满了 所有的 轮  奖励翻倍
			 */
			if(totalPassRound>=round){
				totalGold=totalGold*2;
			}
			
			/**
			 * 加钱
			 */
			slotBase.slotLog(human,slot.getTempleId(),false,true,false,totalGold*curGold/100,humanSlotManager.getCurrentBet());
			
			int[] isRewardArr = new int[isRewards.size()];
			for(int i=0;i<isRewards.size();i++){
				isRewardArr[i]=isRewards.get(i);
			}
			long[] rewardsArr = new long[rewards.size()];
			for(int i=0;i<rewards.size();i++){
				rewardsArr[i]=rewards.get(i);
			}
			
			GCSlotType24BounsBar gCSlotType24BounsBar = new GCSlotType24BounsBar();
			gCSlotType24BounsBar.setIsReward(isRewardArr);
			gCSlotType24BounsBar.setRewards(rewardsArr);
			gCSlotType24BounsBar.setTotalGold(totalGold*curGold/100);
			player.sendMessage(gCSlotType24BounsBar);
			
			
		/**
		 * 2：桑巴小游戏
		 */
		}else if(gameType == 2){
			int color =cgSlotType24BounsGameGo.getColor();
			BonusBrazilSambaStageTemplate bonusBrazilSambaStageTemplate = Globals.getBonusBrazilService().getBrazilSambaStageBySlotType(slot.getType(), color);
			/**
			 * 加钱
			 */
			slotBase.slotLog(human,slot.getTempleId(),false,true,false,Double.valueOf(bonusBrazilSambaStageTemplate.getReward()*curGold/100).longValue(),humanSlotManager.getCurrentBet());
			
			GCSlotType24BounsSamba gCSlotType24BounsSamba = new GCSlotType24BounsSamba();
			gCSlotType24BounsSamba.setReward(bonusBrazilSambaStageTemplate.getReward()*curGold/100);
			player.sendMessage(gCSlotType24BounsSamba);
			//小游戏获得钱
		}
		
	}

	/**
	 * 退出老虎机
	 * @param player
	 * @param cgSlotOut
	 */
	public void handleSlotOut(Player player, CGSlotOut cgSlotOut) {
			//老虎机ID
			int slotId = cgSlotOut.getSlotId();
			//获取老虎机
			Slot slot = Globals.getSlotService().getSlotByTemplateId(slotId);
			if(slot ==null){
				logger.warn("玩家["+player.getPassportId()+"]请求的老虎机不存在");
				return;
			}
			
			Human human = player.getHuman();
//			human.getHumanSlotManager().setBigWin(false);
			Globals.getSlotRoomService().outSlot(player.getPassportId(),human.getHumanSlotManager().getSlotId(),human.getSlotRoomId());
			
			Slot oldSlot = Globals.getSlotService().getSlotByTemplateId(human.getHumanSlotManager().getSlotId());
			if(oldSlot != null){
				Globals.getTournamentService().outSlot(slot.getType(),oldSlot.getType(),player.getPassportId());
			}
			
			/**
			 * 世界boss 专用；老虎机里  有哪个用户，然后就给他删除掉
			 */
			Globals.getSlotService().outSlot(player);
			
	}

	

	public void changJackpot(Slot slot, long jackPot, long cumeJackPot,
			long which) {
		/**以下计算发广播给服务器ID最小的服务器计算 **/
		if(which == 1){
			slot.setJackpot1(jackPot);
			slot.setCumuJackpot1(cumeJackPot);
			slot.redisJackpot();
		}else if(which == 2){
			slot.setJackpot2(jackPot);
			slot.setCumuJackpot2(cumeJackPot);
			slot.redisJackpot();
		}else if(which == 3){
			slot.setJackpot3(jackPot);
			slot.setCumuJackpot3(cumeJackPot);
			slot.redisJackpot();
		}else if(which == 4){
			slot.setJackpot4(jackPot);
			slot.setCumuJackpot4(cumeJackPot);
			slot.redisJackpot();
		}else if(which == 5){
			slot.setJackpot5(jackPot);
			slot.setCumuJackpot5(cumeJackPot);
			slot.redisJackpot();
		}
		
	}

	public void handleSlotType38Bonus(Player player,
			CGSlotType38Bonus cgSlotType38Bonus) {
		int isGhostArr = cgSlotType38Bonus.getIsGhost();
		Human human= player.getHuman();
		HumanSlotManager humanSlotManager =  human.getHumanSlotManager();
		Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
		
		SlotBase slotBase = SlotBase.getSlot(slot.getType());
		slotBase.setSlot(slot);
		
		SlotsListTemplate slotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		long bet = humanSlotManager.getCurrentBet()/slotsListTemplate.getPayLinesNum();
		
		long baseBlood = Globals.getBonusHallowmasService().getBaseBlood(slot.getType());
		long totalGold = baseBlood*bet/100;
		
		
		
		/**
		 * 每一关 对应一个奖励（有且只有这一个奖励），
		 * 
		 * 如果传过来 1 说明 过了2关；如果传过来 2，说明过了 3关； 如果传过来 3 说明全过了
		 */
		List<BounsHallowmasRewardTemplate> rewardList = Globals.getBonusHallowmasService().getRewardGhostByCheckpoint(slot.getType(), isGhostArr);
		
		for(BounsHallowmasRewardTemplate rewardTemplate :rewardList){
			totalGold+=rewardTemplate.getReward()*bet/100;
		}
		
		/**
		 * 加钱
		 */
		slotBase.slotLog(human,slot.getTempleId(),false,true,false,totalGold,humanSlotManager.getCurrentBet());
		
		
		
		GCSlotType38Bonus gCSlotType38Bonus = new GCSlotType38Bonus();
		gCSlotType38Bonus.setTotalGold(totalGold);
		
		player.sendMessage(gCSlotType38Bonus);
	}

	

	
	

	
	

	
	
	 
	
	
	

}
