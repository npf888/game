package com.gameserver.luckyspin.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.LogReasons.CharmLogReason;
import com.common.LogReasons.DiamondLogReason;
import com.common.LogReasons.GoldLogReason;
import com.common.LogReasons.ItemLogReason;
import com.common.LogReasons.LuckySpinLogReason;
import com.common.constants.Loggers;
import com.gameserver.common.CommonLogic;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanLuckySpinManager;
import com.gameserver.luckyspin.HumanLuckySpin;
import com.gameserver.luckyspin.msg.CGBigZhuanpan;
import com.gameserver.luckyspin.msg.CGLuckyMatch;
import com.gameserver.luckyspin.msg.CGLuckySpin;
import com.gameserver.luckyspin.msg.CGSpinZhuanpan;
import com.gameserver.luckyspin.msg.GCBigZhuanpan;
import com.gameserver.luckyspin.msg.GCLuckyMatch;
import com.gameserver.luckyspin.msg.GCLuckySpin;
import com.gameserver.luckyspin.msg.GCSpinZhuanpanFree;
import com.gameserver.luckyspin.template.LuckyMatchTemplate;
import com.gameserver.luckyspin.template.TurntableExtra;
import com.gameserver.luckyspin.template.TurntableTemplate;
import com.gameserver.player.Player;

public class LuckyspinMessageHandler {

	private Logger logger = Loggers.luckyspinLogger;
	
	public void handleLuckySpin(Player player, CGLuckySpin cgLuckySpin) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		HumanLuckySpinManager humanLuckSpinManager = human.getHumanLuckySpinManager();
		boolean  free = false;
		if(cgLuckySpin.getFree()==1){
			if(!humanLuckSpinManager.ifHasFreeTime()){
				logger.warn("玩家【"+player.getPassportId()+"】没有免费次数");
				return;
			}
			free = true;
			
		}else{
			//cost money 
			int tempMoney = Globals.getConfigTempl().getTurntableCost();
			if(!human.hasEnoughMoney(tempMoney, Currency.DIAMOND)){
				logger.warn("玩家【"+player.getPassportId()+"】没有足够的钱");
				return;
			}
			
			human.costMoney(tempMoney, Currency.DIAMOND, true, LogReasons.DiamondLogReason.LUCKY_SPIN_COST, LogReasons.DiamondLogReason.LUCKY_SPIN_COST.getReasonText(), -1, 1);
			free =false;
		}
		
		TurntableTemplate tempTurntableTemplate = null;
		
		if(free){
			 tempTurntableTemplate=Globals.getLuckySpinService().spinFree();
		}else{
			int currentPoolPos =humanLuckSpinManager.poolPos();
			tempTurntableTemplate=Globals.getLuckySpinService().spin(currentPoolPos);
		}
		
		humanLuckSpinManager.spin(free);
		if(!free){
			humanLuckSpinManager.checkIfRefresh();
		}
		//随机
		GCLuckySpin gcLuckySpin = new GCLuckySpin();
		gcLuckySpin.setPos(tempTurntableTemplate.getId());
		player.sendMessage(gcLuckySpin);
		
		
		CommonLogic.getInstance().giveRandReward(human, tempTurntableTemplate.getRewardList(), GoldLogReason.LUCKYSPIN, DiamondLogReason.LUCKY_SPIN_GET, CharmLogReason.LUCKYSPIN, ItemLogReason.LUCKY_SPIN, true);
	
		Globals.getLogService().sendLuckySpinLog(human, LuckySpinLogReason.SPIN, LuckySpinLogReason.SPIN.getReasonText(), cgLuckySpin.getFree(), tempTurntableTemplate.getId());
		human.sendMessage(humanLuckSpinManager.buildGCLuckySpinInfoData());
	}
	
	/**
	 * 幸运匹配
	 * @param player
	 * @param cgLuckyMatch
	 */
	public void handleLuckyMatch(Player player, CGLuckyMatch cgLuckyMatch) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		HumanLuckySpinManager humanLuckSpinManager = human.getHumanLuckySpinManager();
	
		//cost money 
		int tempMoney = Globals.getConfigTempl().getLuckyMatchCost();
		if(!human.hasEnoughMoney(tempMoney, Currency.GOLD)){
			logger.warn("玩家【"+player.getPassportId()+"】没有足够的钱");
			return;
		}
		
		human.costMoney(tempMoney, Currency.GOLD, true, LogReasons.GoldLogReason.LUCKY_MATCH_COST, LogReasons.GoldLogReason.LUCKY_MATCH_COST.getReasonText(), -1, 1);
	
		int currentPoolPos =humanLuckSpinManager.matchPoolPos();
		LuckyMatchTemplate tempLuckyMatchTemplate=Globals.getLuckySpinService().match(currentPoolPos);
	
		humanLuckSpinManager.match();
		humanLuckSpinManager.checkIfMatchRefresh();
			//随机
		GCLuckyMatch gcLuckyMatch = new GCLuckyMatch();
		gcLuckyMatch.setPos(tempLuckyMatchTemplate.getId());
		player.sendMessage(gcLuckyMatch);
		
		CommonLogic.getInstance().giveRandReward(human, tempLuckyMatchTemplate.getRewardList(), GoldLogReason.LUCKY_MATCH_GET, DiamondLogReason.LUCKY_MATCH_GET, CharmLogReason.LUCKY_MATCH_GET, ItemLogReason.LUCKY_MATCH_GET, true);
		
		Globals.getLogService().sendLuckySpinLog(human, LuckySpinLogReason.MATCH, LuckySpinLogReason.MATCH.getReasonText(), 0, tempLuckyMatchTemplate.getId());
		
	}

	public void handleBigZhuanpan(Player player, CGBigZhuanpan cgBigZhuanpan) {
		
		Human human = player.getHuman();
		
		GCBigZhuanpan message = new GCBigZhuanpan();
		message.setIsFree(human.getHumanLuckySpinManager().ifFree()?1:0);
		message.setLoginPoint(human.getHumanWeekSignInManager().getHumanWeekSignIn().getSignInList().size());
		
		player.sendMessage(message);
	}

	public void handleSpinZhuanpan(Player player, CGSpinZhuanpan cgSpinZhuanpan) {
		
		Human human = player.getHuman();
		
		HumanLuckySpinManager hls = human.getHumanLuckySpinManager();
		
		HumanLuckySpin humanLuckySpin = hls.getHumanLuckySpin();
		
		TurntableExtra te = Globals.getLuckySpinService().getTurntableExtra();
		
		List<RandRewardData> listData = new ArrayList<RandRewardData>();
		int index = 0;
		//1是免费次数
		int isFree = cgSpinZhuanpan.getIsFree();
		if(isFree ==1){
			if(!human.getHumanLuckySpinManager().ifFree()){
				return;
			}
		   //取哪一个概率
		   int poolPos = humanLuckySpin.getFreeTimes();
			
		   //免费转动
		   TurntableTemplate turntableTemplate = Globals.getLuckySpinService().spinFreeNew(poolPos);
		   index = turntableTemplate.getId();
		   //转动转盘获得
		   int rewardCount = turntableTemplate.getRewardList().get(0).getRewardCount();
		   //登陆奖励
		   int login = human.getHumanWeekSignInManager().signInNew().getRewardCount();
		   
		   int vipLv = human.getHumanVipNewManager().getVipLv();
		   //VIP奖励
		   int vipreward = Globals.getVipNewServer().getBigSpin(vipLv,login);
		   //朋友奖励
		   int friendReward = human.getHumanRelationManager().getFriendList().size()*te.getFriendreward();
		   //等级奖励
		   int levelReward = (int)human.getLevel()*te.getLevelreward();
		   
		   //重新设置时间
		   humanLuckySpin.setLastSpinTime(Globals.getTimeService().now());
		   //记录取概率点
		   humanLuckySpin.setFreeTimes(poolPos+1);
		   
		   GCSpinZhuanpanFree message = new GCSpinZhuanpanFree();
		   message.setPoint(index);
		   message.setLoginPoint(human.getHumanWeekSignInManager().getHumanWeekSignIn().getSignInList().size());
		   message.setDailyReward(rewardCount+login);
		   message.setVipReward(vipreward);
		   message.setFriendReward(friendReward);
		   message.setLevelReward(levelReward);
		   player.sendMessage(message);
		   RandRewardData data = new RandRewardData();
		   data.setRewardId(3);
		   data.setRewardCount(rewardCount+login+vipreward+friendReward+levelReward);
		   listData.add(data);
		   
		}else{
			//花钱转动消耗金币
			/*int tempMoney = Globals.getConfigTempl().getTurntableCost();
			if(!human.hasEnoughMoney(tempMoney, Currency.GOLD)){
				logger.warn("玩家【"+player.getPassportId()+"】没有足够的钱");
				return;
			}
			 int poolPos = humanLuckySpin.getTimes();
			
			 TurntableTemplate turntableTemplate =  Globals.getLuckySpinService().spinNew(poolPos);
			 index = turntableTemplate.getId();
			 
			 humanLuckySpin.setTimes(poolPos+1);
			 
			 GCSpinZhuanpanNofree message = new GCSpinZhuanpanNofree();
			 message.setIsFree(human.getHumanLuckySpinManager().ifFree()?1:0);
			 message.setPoint(index);
			 player.sendMessage(message);
			 
			 listData = turntableTemplate.getRewardList();*/
		}
		
		humanLuckySpin.setModified();
		
		CommonLogic.getInstance().giveRandReward(human, listData, GoldLogReason.LUCKYSPIN, DiamondLogReason.LUCKY_SPIN_GET, CharmLogReason.LUCKYSPIN, ItemLogReason.LUCKY_SPIN, false);
		
		Globals.getLogService().sendLuckySpinLog(human, LuckySpinLogReason.SPIN, LuckySpinLogReason.SPIN.getReasonText(), cgSpinZhuanpan.getIsFree(), index);
		
	}

}
