package com.gameserver.player;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import com.alibaba.fastjson.JSON;
import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.util.StringUtils;
import com.db.model.HumanEntity;
import com.db.model.HumanVipNewEntity;
import com.gameserver.common.Globals;
import com.gameserver.player.cache.PlayerCacheInfo;

/**
 * 玩家缓存服务
 * @author wayne
 *
 */
public class PlayerCacheService implements InitializeRequired,AfterInitializeRequired{

	private Logger logger = Loggers.playerLogger;
	
	private final String PLAYER_CACHE_PREFIX = "player.";
	private final int EXPIRE_TIME = 3600*24;
	
	private JedisPool jedisPool;
	
	@Override
	public void init() {

	}
	
	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		this.jedisPool = Globals.getRedisService().getJedisPool();
	}
	
	//同步玩家基本信息
	public void syncPlayerCache(PlayerCacheInfo playerCacheInfo)
	{
		Jedis jedis = null;
		try{
			jedis = this.jedisPool.getResource();
			String playerCacheKey = playerCacheKey(playerCacheInfo.getPlayerId());
			jedis.set(playerCacheKey, JSON.toJSONString(playerCacheInfo));
			jedis.expire(playerCacheKey, EXPIRE_TIME);
		}finally{
			if(jedis!=null) {jedis.close();}
		}
		
		
	}

	
	/**
	 * 获得缓存
	 * @param playerId
	 * @return
	 */
	public PlayerCacheInfo getPlayerCacheById(long playerId){
		
		Jedis jedis = null;
		String cacheJsonStr = "";
		try{
			jedis = this.jedisPool.getResource();
			cacheJsonStr =  jedis.get(playerCacheKey(playerId));
		}finally{
			if(jedis!=null) {jedis.close();}
		}
		
		
		
		if(StringUtils.isEmpty(cacheJsonStr)){
			
			List<HumanEntity> humanEntityList = Globals.getDaoService().getHumanDao().loadHumans(playerId);
			
			if(humanEntityList.size()==0){  
				return null;
			}else{
				
				PlayerCacheInfo playerCacheInfo= PlayerCacheInfo.playerCacheInfoFromOfflineHuman(humanEntityList.get(0));
				
				HumanVipNewEntity entityVipNew = Globals.getDaoService().getVipNewDao().getVipHumanById(playerId);
				if(entityVipNew != null){
					playerCacheInfo.setVipLevel(entityVipNew.getVipLevel());
				}
				
				syncPlayerCache(playerCacheInfo);
				return playerCacheInfo;
			}
		}
		
		return JSON.parseObject(cacheJsonStr,PlayerCacheInfo.class );
	}
	/**
	 * 获得缓存
	 * @param passortId
	 * @return
	 */
	public List<PlayerCacheInfo> getPlayerCacheByName(String userName){
		
		List<PlayerCacheInfo> list = new ArrayList<PlayerCacheInfo>();
		
		List<HumanEntity> humanEntityList = Globals.getDaoService().getHumanDao().loadHumanAll(userName);
		if(humanEntityList != null){
			Jedis jedis = null;
			try {
				jedis = this.jedisPool.getResource();
				for(HumanEntity en : humanEntityList){
					long playerId = en.getPassportId();
					String cacheJsonStr = "";
					cacheJsonStr =  jedis.get(playerCacheKey(playerId));
					
					if(StringUtils.isEmpty(cacheJsonStr)){
						
						PlayerCacheInfo playerCacheInfo= PlayerCacheInfo.playerCacheInfoFromOfflineHuman(en);
						
						HumanVipNewEntity entityVipNew = Globals.getDaoService().getVipNewDao().getVipHumanById(playerId);
						if(entityVipNew != null){
							playerCacheInfo.setVipLevel(entityVipNew.getVipLevel());
						}
						syncPlayerCache(playerCacheInfo);
						
						list.add(playerCacheInfo);
					}else{
						list.add(JSON.parseObject(cacheJsonStr,PlayerCacheInfo.class));
					}
				}
			} finally {
				if(jedis!=null) {jedis.close();}
			}
		}
		return list;
	}
	
	
	
	/**
	 * 获得玩家列表
	 * @param playerIds
	 * @return
	 */
	public Map<Long,PlayerCacheInfo> getPlayerCacheMapByIds(List<Long> playerIds)
	{
		Map<Long,PlayerCacheInfo> tempMap = new HashMap<Long,PlayerCacheInfo>();
		Jedis jedis = this.jedisPool.getResource();
		
		if(jedis == null){
			logger.error("请求玩家列表， jedis instance failed");
			return null;
		}
		try{
			Pipeline p = jedis.pipelined();
			
			for(Long playerId : playerIds){
				p.get(playerCacheKey(playerId));
			}
			
			List<Object> playerCachePackList= p.syncAndReturnAll();
			
			for(int i=0;i< playerCachePackList.size();i++){
				
				Object obj = playerCachePackList.get(i);
				if(obj==null){
					List<HumanEntity> humanEntityList = Globals.getDaoService().getHumanDao().loadHumans(playerIds.get(i));
				
					if(humanEntityList.size() !=0 ){
						PlayerCacheInfo playerCacheInfo= PlayerCacheInfo.playerCacheInfoFromOfflineHuman(humanEntityList.get(0));
						HumanVipNewEntity entityVipNew = Globals.getDaoService().getVipNewDao().getVipHumanById(playerIds.get(i));
						if(entityVipNew != null){
							playerCacheInfo.setVipLevel(entityVipNew.getVipLevel());
						}
						syncPlayerCache(playerCacheInfo);
						tempMap.put(playerCacheInfo.getPlayerId(), playerCacheInfo);
					}
				
				}
				else{
					PlayerCacheInfo playerCacheInfo= JSON.parseObject((String)obj,PlayerCacheInfo.class );
					tempMap.put(playerCacheInfo.getPlayerId(), playerCacheInfo);
				}
			}
		}finally{
			jedis.close();
		}
		return tempMap;
	}
	
	/**
	 * 缓存中获取陌生人列表
	 * @return
	 */
	public List<PlayerCacheInfo> getPlayerCacheMap(Set<Long> setIds){
		
		List<PlayerCacheInfo> tempMap = new ArrayList<PlayerCacheInfo>();
		//随机取得14个
		List<PlayerCacheInfo> randomTempMap = new ArrayList<PlayerCacheInfo>();
		Jedis jedis = this.jedisPool.getResource();
		
		if(jedis == null){
			logger.error("请求玩家列表， jedis instance failed");
			return null;
		}
		try{
			//拿到所有缓存的key
			Set<String> setKey = jedis.keys("*");
			
	        Pipeline p = jedis.pipelined();
			
			for(String key : setKey){
				if(key.contains(PLAYER_CACHE_PREFIX)){
					p.get(key);
				}
			}
		
			List<Object> playerCachePackList= p.syncAndReturnAll();
			
			for(Object object : playerCachePackList){
				if(object != null){
					PlayerCacheInfo playerCacheInfo= JSON.parseObject((String)object,PlayerCacheInfo.class );
					long playerId = playerCacheInfo.getPlayerId();
					if(!setIds.contains(playerId)){
	                    /*if(tempMap.size() >= 14){//AAA
	                    	return tempMap;
						}*/
						tempMap.add(playerCacheInfo);
					}
					
				}
			}
			//随机获取14个
			Random random = new Random();
			List<Integer> indexs = new ArrayList<Integer>();
			List<Integer> beBackIndexs = new ArrayList<Integer>();
			//先把 tempMap的index存到indexs里
			for(int i = 0;i<tempMap.size();i++){
				indexs.add(i);
			}
			int totalPeople = indexs.size();
			if(totalPeople<14){
				for(int i=0;i<totalPeople;i++){
					int hasOutIndex = random.nextInt(indexs.size());
					beBackIndexs.add(indexs.get(hasOutIndex));
					indexs.remove(hasOutIndex);
				}
			}else{
				for(int i=0;i<14;i++){
					int hasOutIndex = random.nextInt(indexs.size());
					beBackIndexs.add(indexs.get(hasOutIndex));
					indexs.remove(hasOutIndex);
				}
			}
			for(Integer index:beBackIndexs){
				randomTempMap.add(tempMap.get(index));
			}
		}finally{
			jedis.close();
		}
		
		
		return randomTempMap;
	}
	
	private String playerCacheKey(long playerId){
		return PLAYER_CACHE_PREFIX+playerId;
	}
	

}
