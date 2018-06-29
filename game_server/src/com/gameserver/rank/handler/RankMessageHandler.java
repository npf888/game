package com.gameserver.rank.handler;

import java.util.List;

import org.slf4j.Logger;

import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.gameserver.common.i18n.LangService;
import com.gameserver.player.OnlinePlayerService;
import com.gameserver.player.Player;
import com.gameserver.rank.RankLogic;
import com.gameserver.rank.data.RankData;
import com.gameserver.rank.enums.RankTypeEnum;
import com.gameserver.rank.msg.CGCommonRank;
import com.gameserver.rank.msg.GCCommonRank;

/**
 * 排行版处理器
 * @author wayne
 *
 */
public class RankMessageHandler {
	
	private Logger logger = Loggers.rankLogger;
	
	public RankMessageHandler(OnlinePlayerService onlinePlayerService,
			LangService langService) {
		// TODO Auto-generated constructor stub
	}

	public void handleCommonRank(Player player, CGCommonRank cgCommonRank) {
		// TODO Auto-generated method stub
		RankTypeEnum rankTypeEnum = RankTypeEnum.valueOf(cgCommonRank.getRankType());
		if(rankTypeEnum==null)
		{
			player.sendSystemMessage(LangConstants.RANK_TYPE_ERROR);
			return;
		}
		if(cgCommonRank.getPage()!=1){
			logger.warn("玩家["+player.getPassportId()+"]请求排行版页数错误,参数["+cgCommonRank.getPage()+"]");
			return;
		}
		List<RankData> rankDataList=RankLogic.getInstance().queryRank(rankTypeEnum, cgCommonRank.getPage());
		long rank = RankLogic.getInstance().querySelfRank(player.getPassportId(),rankTypeEnum);
		GCCommonRank gcCommonRank = new GCCommonRank();
		gcCommonRank.setSelfRank(rank);
		gcCommonRank.setRankType(cgCommonRank.getRankType());
		gcCommonRank.setPage(cgCommonRank.getPage());
		gcCommonRank.setRankDataList(rankDataList.toArray(new RankData[rankDataList.size()]));
		player.sendMessage(gcCommonRank);
	}

}
