package com.gameserver.monthcard.handler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanMonthCardManager;
import com.gameserver.mail.MailLogic;
import com.gameserver.monthcard.msg.CGMonthCardGet;
import com.gameserver.player.Player;
/**
 * 月卡
 * @author wayne
 *
 */
public class MonthcardMessageHandler {

	
	/** 日志 */
	private static final Logger logger = Loggers.monthCardLogger;


	public MonthcardMessageHandler() {
	
	}

	public void handleMonthCardGet(Player player, CGMonthCardGet cgMonthCardGet) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		if(human== null)
			return;
		//判断是否可以领取
		HumanMonthCardManager humanMonthCardManager= human.getHumanMonthCardManager();
		if(humanMonthCardManager.ifExpired())
		{
			//player.getHuman().sendSystemMessage(LangConstants.MONTH_CARD_EXPIRED);
			//logger.warn("玩家[" +player.getPassportId() + "]领取过期的月卡");
			return;
		}
		
		if(humanMonthCardManager.ifGet())
		{
			//player.getHuman().sendSystemMessage(LangConstants.MONTH_CARD_ALREADY_GET);
			//logger.warn("玩家[" +player.getPassportId() + "]已经领过");
			return;
		}
		
		  List<RandRewardData> dailyRewardList = humanMonthCardManager.getMonthDailyRewardDataNew();
		
		  List<Long> listId = new ArrayList<Long>();
		  listId.add(player.getHuman().getPassportId());
		  
		  int date = 0;
		try {
			long currTime = Globals.getTimeService().now();
			long beginTime = humanMonthCardManager.getHumanMonthCard().getBeginTime();
			long dateS = TimeUtils.daysBetweenFor(beginTime,currTime);
			long alldate = humanMonthCardManager.getHumanMonthCard().getDuration()/(1000*3600*24);
			date = (int)(alldate - dateS)-1;
		} catch (ParseException e) {
			Loggers.JACKPOT.error("", e);
		}
		  //发邮件
		/*String content = String.valueOf(LangConstants.cardcontent)+","+dailyRewardList.get(0).getRewardCount()+","+date;
		MailLogic.getInstance().systemSendMail(null,String.valueOf(LangConstants.cardTitle) ,content, listId, dailyRewardList); */
		
		for(int i=0;i<dailyRewardList.size();i++){
			List<RandRewardData> newRandRewardDataList = new ArrayList<RandRewardData>();
	    	newRandRewardDataList.add(dailyRewardList.get(i));
			String content = String.valueOf(LangConstants.cardcontent)+","+dailyRewardList.get(i).getRewardCount()+","+date;
			MailLogic.getInstance().systemSendMail(null,String.valueOf(LangConstants.cardTitle) ,content, listId, newRandRewardDataList); 
		}
		//CommonLogic.getInstance().giveRandReward(human, dailyRewardList,LogReasons.GoldLogReason.MONTH_CARD_DAILY,LogReasons.DiamondLogReason.MONTH_CARD_DAILY,LogReasons.CharmLogReason.MONTH_CARD_DAILY,LogReasons.ItemLogReason.MONTH_CARD_DAILY, false);
		player.sendMessage(humanMonthCardManager.buildHumanMonthCardInfoData());
		
		logger.debug("玩家[" +player.getPassportId() + "]领取月卡每日奖励");
		//记录日志
		Globals.getLogService().sendMonthcardLog(human, LogReasons.MonthcardLogReason.MONTH_CARD_GET, LogReasons.MonthcardLogReason.MONTH_CARD_GET.getReasonText(), humanMonthCardManager.getHumanMonthCard().getDuration());
/*		Human human = player.getHuman();
		if(human== null)
			return;
		//判断是否可以领取
		HumanMonthCardManager humanMonthCardManager= human.getHumanMonthCardManager();
		if(humanMonthCardManager.ifExpired())
		{
			player.getHuman().sendSystemMessage(LangConstants.MONTH_CARD_EXPIRED);
			logger.warn("玩家[" +player.getPassportId() + "]领取过期的月卡");
			return;
		}
		
		if(humanMonthCardManager.ifGet())
		{
			player.getHuman().sendSystemMessage(LangConstants.MONTH_CARD_ALREADY_GET);
			logger.warn("玩家[" +player.getPassportId() + "]已经领过");
			return;
		}
		
		List<RandRewardData> dailyRewardList = humanMonthCardManager.getMonthDailyRewardData();
		CommonLogic.getInstance().giveRandReward(human, dailyRewardList,LogReasons.GoldLogReason.MONTH_CARD_DAILY,LogReasons.DiamondLogReason.MONTH_CARD_DAILY,LogReasons.CharmLogReason.MONTH_CARD_DAILY,LogReasons.ItemLogReason.MONTH_CARD_DAILY, false);
		
		GCMonthCardGet gcMonthCardGet = new GCMonthCardGet();
		gcMonthCardGet.setRandRewardList(dailyRewardList.toArray(new RandRewardData[dailyRewardList.size()]));
		player.sendMessage(gcMonthCardGet);
		player.sendMessage(humanMonthCardManager.buildHumanMonthCardInfoData());
		
		logger.debug("玩家[" +player.getPassportId() + "]领取月卡每日奖励");
		//记录日志
		Globals.getLogService().sendMonthcardLog(human, LogReasons.MonthcardLogReason.MONTH_CARD_GET, LogReasons.MonthcardLogReason.MONTH_CARD_GET.getReasonText(), humanMonthCardManager.getHumanMonthCard().getDuration());
*/		
	}

	

}
