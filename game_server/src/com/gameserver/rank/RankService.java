package com.gameserver.rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.gameserver.common.Globals;
import com.gameserver.player.cache.PlayerCacheInfo;
import com.gameserver.rank.data.RankData;
import com.gameserver.redis.RedisService;

/**
 * 排行版服务
 * @author wayne
 *
 */
public class RankService implements InitializeRequired,AfterInitializeRequired {

	private Logger logger = Loggers.redisLogger;
	private JedisPool jedisPool;
	private RedisService redisService;

	
	@Override
	public void init() {
		// TODO Auto-generated method stub
	
	}
	
	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		/*redisService = Globals.getRedisService();
		jedisPool = redisService.getJedisPool();*/
	}


	/**
	 * 查询排行数据
	 * 
	 * @param key
	 * @param page
	 * @param numPerPage
	 * @return
	 */
	public List<RankData> queryRank(String key,int page,int numPerPage)
	{
		Jedis jedis = jedisPool.getResource();
		if(jedis == null)
		{
			logger.error("请求排行版失败， jedis instance failed");
			return null;
		}
		long start = (page-1)* numPerPage;
		long end =page*numPerPage-1;
		Set<Tuple> strSet=jedis.zrevrangeWithScores(key, start, end);
		List<RankData> tempList = new ArrayList<RankData>();
		List<Long> playerIds = new ArrayList<Long>();
		for(Tuple tuple : strSet)
		{
			RankData rankData = new RankData();
			long uid = Long.parseLong(tuple.getElement());
			playerIds.add(uid);
			rankData.setUId(uid);
			rankData.setScore((long)(tuple.getScore()));
			tempList.add(rankData);
		}
		
		Map<Long,PlayerCacheInfo> playerCacheInfoMap= Globals.getPlayerCacheService().getPlayerCacheMapByIds(playerIds);
		if(playerCacheInfoMap == null)
		{
			logger.error("请求排行版缓存数据失败， jedis instance failed");
			return null;
		}
		
		for(RankData rankData :tempList)
		{
			PlayerCacheInfo playerCacheInfo = playerCacheInfoMap.get(rankData.getUId());
			if(playerCacheInfo!=null){
				rankData.setImg(playerCacheInfo.getImg());
				rankData.setName(playerCacheInfo.getName());
			}
		}
		jedis.close();
//		jedisPool.returnResourceObject(jedis);
		return tempList;
	}
	
	/**
	 * 查询自己排行版
	 * @param key
	 * @return
	 */
	public long  querySelfRank(String uid,String key) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		if(jedis == null)
		{
			logger.error("请求排行版失败， jedis instance failed");
			return -1;
		}
		long rank = jedis.zrevrank(key, uid);
		jedis.close();
//		jedisPool.returnResourceObject(jedis);
		return rank;
	}
	
	/**
	 * 更新排行版
	 * @param key
	 * @param uId
	 * @param score
	 */
	public void updateRank(String key, long uId, long score) {
		Jedis jedis = jedisPool.getResource();
		if(jedis == null)
		{
			logger.error("更新排行版失败， jedis instance failed");
			return ;
		}
		jedis.zadd(key, score, String.valueOf(uId));
		jedis.close();
//		jedisPool.returnResourceObject(jedis);
		
	}
	
	/**
	 * 更新周排行版
	 */
	public void updateWeekRank(String key,long uId,long score){
		Jedis jedis = jedisPool.getResource();
		if(jedis == null)
		{
			logger.error("更新排行版失败， jedis instance failed");
			return ;
		}
		jedis.zadd(key, score, String.valueOf(uId));
		jedis.expire(key, (int)(7* TimeUtils.DAY/TimeUtils.SECOND));
		jedis.close();
//		jedisPool.returnResourceObject(jedis);
	}

	
}
