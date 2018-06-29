package com.gameserver.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.gameserver.common.Globals;
import com.gameserver.redis.impl.ServerRedisMsgListener;

/**
 * redis 子线程
 * @author wayne
 *
 */
public class RedisSubThread extends Thread{

	private JedisPool jedisPool;
	
	public RedisSubThread()
	{
		jedisPool = Globals.getRedisService().getJedisPool();
	}
	
	@Override
	public void run()
	{
		ServerRedisMsgListener listener= new ServerRedisMsgListener();
		Jedis jedis = jedisPool.getResource();
		//jedis.psubscribe(listener, "server.100002|broadcast");接听多个频道
		jedis.subscribe(listener, Globals.getRedisService().getSelfChannel(),Globals.getRedisService().getBroadcastChannel());
		jedis.close();
//		this.jedisPool.returnResourceObject(jedis);
		
	}
	
}
