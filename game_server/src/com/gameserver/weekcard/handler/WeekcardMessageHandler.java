package com.gameserver.weekcard.handler;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.gameserver.common.CommonLogic;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanWeekCardManager;
import com.gameserver.mail.MailLogic;
import com.gameserver.player.Player;
import com.gameserver.weekcard.msg.CGWeekCardGet;
import com.gameserver.weekcard.msg.GCWeekCardGet;

/**
 * 周卡
 * @author wayne
 *
 */
public class WeekcardMessageHandler {

	/** 日志 */
	private static final Logger logger = Loggers.weekCardLogger;


	public WeekcardMessageHandler() {
	
	}

	/**
	 * 领取周卡
	 * @param player
	 * @param cgWeekCardGet
	 */
	public void handleWeekCardGet(Player player, CGWeekCardGet cgWeekCardGet) {
		
		Human human = player.getHuman();
		if(human== null)
			return;
		
		//判断是否可以领取
		HumanWeekCardManager humanWeekCardManager= human.getHumanWeekCardManager();
		if(humanWeekCardManager.ifExpired())
		{
//			player.getHuman().sendSystemMessage(LangConstants.WEEK_CARD_EXPIRED);
			logger.warn("玩家[" +player.getPassportId() + "]领取过期的周卡");
			return;
		}
		
		if(humanWeekCardManager.ifGet())
		{
//			player.getHuman().sendSystemMessage(LangConstants.WEEK_CARD_ALREADY_GET);
			logger.warn("玩家[" +player.getPassportId() + "]今天已经领取过");
			return;
		}
		  int date = 0;
			try {
				long currTime = Globals.getTimeService().now();
				long beginTime = humanWeekCardManager.getHumanWeekCard().getBeginTime();
				long dateS = TimeUtils.daysBetweenFor(beginTime,currTime);
				long alldate = humanWeekCardManager.getHumanWeekCard().getDuration()/(1000*3600*24);
				date = (int)(alldate - dateS)-1;
			} catch (ParseException e) {
				Loggers.JACKPOT.error("", e);
			}
		List<RandRewardData> dailyRewardList = humanWeekCardManager.getWeekDailyRewardDataNew();
//		CommonLogic.getInstance().giveRandReward(human, dailyRewardList,LogReasons.GoldLogReason.WEEK_CARD_DAILY,LogReasons.DiamondLogReason.WEEK_CARD_DAILY,LogReasons.CharmLogReason.WEEK_CARD_DAILY,LogReasons.ItemLogReason.WEEK_CARD_DAILY, false);
		
		List<Long> listId = new ArrayList<Long>();
	    listId.add(player.getHuman().getPassportId());
	    
	    for(int i=0;i<dailyRewardList.size();i++){
	    	List<RandRewardData> newRandRewardDataList = new ArrayList<RandRewardData>();
	    	newRandRewardDataList.add(dailyRewardList.get(i));
			String content = String.valueOf(LangConstants.weekcardcontent)+","+dailyRewardList.get(i).getRewardCount()+","+date;
			MailLogic.getInstance().systemSendMail(null,String.valueOf(LangConstants.weekcardTitle) ,content, listId, newRandRewardDataList);
		}
		
		
		GCWeekCardGet gcWeekCardGet = new GCWeekCardGet();
		gcWeekCardGet.setRandRewardList(dailyRewardList.toArray(new RandRewardData[dailyRewardList.size()]));
		player.sendMessage(gcWeekCardGet);
		
		player.sendMessage(humanWeekCardManager.buildHumanWeekCardInfoData());
		
		logger.debug("玩家[" +player.getPassportId() + "]领取周卡奖励");
		//记录日志
		String detailReason = LogReasons.WeekcardLogReason.WEEK_CARD_GET.getReasonText();
		Globals.getLogService().sendWeekcardLog(human, LogReasons.WeekcardLogReason.WEEK_CARD_GET, detailReason, humanWeekCardManager.getHumanWeekCard().getDuration());
		
	}

}
