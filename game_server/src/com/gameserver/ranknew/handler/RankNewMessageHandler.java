package com.gameserver.ranknew.handler;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;
import com.gameserver.ranknew.data.RankListData;
import com.gameserver.ranknew.enums.RankKeyType;
import com.gameserver.ranknew.msg.CGHumanRank;
import com.gameserver.ranknew.msg.CGRequestRank;
import com.gameserver.ranknew.msg.GCHumanRank;
import com.gameserver.ranknew.msg.GCRankList;

/**
 * 
 * @author 郭君伟
 *
 */
public class RankNewMessageHandler {

	private Logger logger = Loggers.rankNewMessageLogger;
	
	public void handleRequestRank(Player player, CGRequestRank cgRequestRank) {
		
		
		int start = cgRequestRank.getStart();
		
		int end = cgRequestRank.getEnd();
		
		
		logger.info("玩家ID 【"+player.getPassportId()+"】 请求排行榜开始位置 "+start+"  结束位置    "+end);
		
		String key = RankKeyType.RankKeyType6.getKey();
	
		RankListData[] listData = Globals.getRankNewServer().getRankListData(key, start, end,player.getPassportId());
		
		GCRankList gc = new GCRankList();
		gc.setStart(start);
		gc.setEnd(end);
		gc.setRankListData(listData);
		player.sendMessage(gc);
		
		
	}

	public void handleHumanRank(Player player, CGHumanRank cgHumanRank) {
		
		long pass = player.getPassportId();
		
		long  now = Globals.getTimeService().now();
		long currMonday = TimeUtils.DAY*7 - (now - TimeUtils.getBeginOfWeek(now));
		
		GCHumanRank message = new GCHumanRank();
		message.setRank(Globals.getRankNewServer().getHumanRankbyId(pass));
		message.setWin(Globals.getRankNewServer().getHumanScorebyId(pass));
		message.setRefreshPoint(now+currMonday);
		
		
		player.sendMessage(message);
	}

}
