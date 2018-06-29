package com.gameserver.common;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.common.InitializeRequired;
import com.gameserver.redis.enums.RedisKey;

/**
 * @author 郭君伟
 *
 */
public class ServerComm implements InitializeRequired {

	private JedisPool jedisPool;
	
	/**当前服务器ID **/
	private String serverid;
	
	@Override
	public void init() {
		
		
		
		serverid = Globals.getServerConfig().getServerId();
		
		jedisPool = Globals.getRedisService().getJedisPool();
		flush();
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.zadd(RedisKey.SERVERID.getKey(), Integer.valueOf(serverid), serverid);
		}finally{
			jedis.close();
//			jedisPool.returnResourceObject(jedis);
		}
	}
	
	public void flush(){
        Jedis jedis = jedisPool.getResource();
		
		try{
			Set<String> keys = jedis.keys("*");
			
			for(String key : keys){
				if(key.contains("slot")){
					jedis.del(key);
				}
			}
			
//			jedis.flushDB();
//			jedis.flushAll();
			
		}finally{
			jedis.close();
//			jedisPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 是否有操作数据权限
	 * @return true : 有持久化的权限
	 */
	public boolean isAuthority(){
		Jedis jedis = jedisPool.getResource();
		try{
			Long rank = jedis.zrank(RedisKey.SERVERID.getKey(), serverid);
			return rank != null && rank == 0;
		}finally{
			jedis.close();
//			jedisPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 获取服务器最小ID
	 * @return
	 */
	public String getMinServerId(){
        Jedis jedis = jedisPool.getResource();
		try{
			Set<String> ids = jedis.zrange(RedisKey.SERVERID.getKey(), 0, 0);
			return ids.iterator().next();
		}finally{
			jedis.close();
//			jedisPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 服务器关闭的时候调用
	 */
	public void removeId(){
	     Jedis jedis = jedisPool.getResource();
	     try{
	    	 jedis.zrem(RedisKey.SERVERID.getKey(), serverid);
	     }finally{
	    	 jedis.close();
//	    	 jedisPool.returnResourceObject(jedis);
	     }
	    
	}

}
