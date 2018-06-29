package com.gameserver.rank;

import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.gameserver.common.Globals;
import com.gameserver.rank.data.RankData;
import com.gameserver.rank.enums.RankTypeEnum;

/**
 * 排行逻辑 
 * @author wayne
 *
 */
public class RankLogic {
	
	private Logger logger = Loggers.rankLogger;
	
	private final int NUM_PER_PAGE =100;
	
	private static RankLogic instance = new RankLogic();
	public static RankLogic getInstance()
	{
		return instance;
	}
	
	/**
	 * 查询排行版
	 * @param rankTypeEnum
	 * @param page
	 * @return
	 */
	public List<RankData> queryRank(RankTypeEnum rankTypeEnum,int page)
	{
	
		return Globals.getRankService().queryRank(getRankKey(rankTypeEnum), page, NUM_PER_PAGE);
	}
	
	/**
	 * 查询自己排行
	 * @param rankTypeEnum
	 * @param page
	 * @return
	 */
	public long querySelfRank(long uid,RankTypeEnum rankTypeEnum)
	{
		return Globals.getRankService().querySelfRank(String.valueOf(uid),getRankKey(rankTypeEnum));
	}
	
	/**
	 * 
	 * @param rankTypeEnum
	 * @param uId
	 * @param score
	 */
	public void updateRank(RankTypeEnum rankTypeEnum,long uId,long score)
	{
		/*if(rankTypeEnum.getRefreshDay()==-1)
			Globals.getRankService().updateRank(getRankKey(rankTypeEnum), uId, score);
		else if(rankTypeEnum.getRefreshDay()==7){
			Globals.getRankService().updateWeekRank(getRankKey(rankTypeEnum), uId, score);
		}
		else{
			logger.error("current rank not support this rank type");
		}*/
	}
	
	/**
	 * 获得周key
	 */
	private String getRankKey(RankTypeEnum rankTypeEnum){
		if(rankTypeEnum.getRefreshDay()==-1)
			return rankTypeEnum.getKey();
		else if(rankTypeEnum.getRefreshDay()==7){
			return rankTypeEnum.getKey()+"."+String.valueOf(TimeUtils.getBeginOfWeek(Globals.getTimeService().now()));
		}
		else{
			return rankTypeEnum.getKey();
		}
	}
}
