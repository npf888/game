package com.gameserver.redis;

import org.slf4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.alibaba.fastjson.JSON;
import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.gameserver.common.Globals;
import com.gameserver.common.config.GameServerConfig;
import com.gameserver.redis.msg_processing.ServerRedisMsgProcessor;

/**
 * 
 * @author wayne
 *
 */
public class RedisService implements InitializeRequired,AfterInitializeRequired{
	
	/**日志*/
	private Logger logger =Loggers.redisLogger;
	/**jedis pool*/
	private JedisPool jedisPool;
	/**jedis监听线程*/
	private RedisSubThread redisSubThread;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		logger.info("redis service init");
		GameServerConfig gameServerConfig = Globals.getServerConfig();
		JedisPoolConfig jedisPoolCfg = new JedisPoolConfig();
		jedisPoolCfg.setMaxTotal(gameServerConfig.getRedisMaxActive());
		jedisPoolCfg.setMaxIdle(gameServerConfig.getRedisMaxIdle());
		jedisPoolCfg.setMaxWaitMillis(gameServerConfig.getRedisMaxWait() * TimeUtils.SECOND);
		jedisPoolCfg.setTestOnBorrow(false);
		jedisPoolCfg.setTestOnReturn(false);
//		jedisPool = new JedisPool(jedisPoolCfg,gameServerConfig.getRedisHost(),gameServerConfig.getRedisPort(),2000,gameServerConfig.getPassword());
		jedisPool = new JedisPool(jedisPoolCfg,gameServerConfig.getRedisHost(),gameServerConfig.getRedisPort());
		
		redisSubThread = new RedisSubThread();
		logger.info("redis["+gameServerConfig.getRedisHost()+":"+gameServerConfig.getRedisPort()+" service init successfully");
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				ServerRedisMsgProcessor sp = new ServerRedisMsgProcessor();
				sp.init();
				Jedis jedis = null;
				try {
					jedis = jedisPool.getResource();
					jedis.subscribe(sp, sp.retrievedSubscribedChannels());
				} finally {
					jedis.close();
				}
			}
		}).start();
		logger.info("ServerRedisMsgProcessor initialized successfully!");
	}
	
	public void start()
	{
		logger.info("begin start redis sub thread");
		redisSubThread.start();
		logger.info("end start redis sub thread");
	}
	
	public void stop(){
		logger.info("begin stop redis sub thread");
		redisSubThread.interrupt();
		logger.info("end stop redis sub thread");
	}
	
	
	
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		logger.info("redis service after init");
	}
	
	
	
	public String getSelfChannel()
	{
		return "server."+Globals.getServerConfig().getServerId();
	}
	
	public String getBroadcastChannel()
	{
		return "broadcast";
	}
	
	//发送数据到某个服务器频道
	public void sendRedisMsgToServer(String serverId,IRedisMessage redisMsg)
	{
		RedisPayloadMessage redisPayloadMessage = new RedisPayloadMessage();
		redisPayloadMessage.setCmd(redisMsg.getClass().getSimpleName());
		redisPayloadMessage.setPayload(JSON.toJSONString(redisMsg));
		String channel = "server."+serverId;
		Jedis jedis = jedisPool.getResource();
		if(jedis == null)
		{
			logger.error("发送到服务器频道,jedis instance failed");
			return;
		}
		
		jedis.publish(channel,JSON.toJSONString(redisPayloadMessage));
		jedis.close();
//		jedisPool.returnResourceObject(jedis);
	}
	
	//广播
	public void broadcastRedisMsg(IRedisMessage redisMsg)
	{
		RedisPayloadMessage redisPayloadMessage = new RedisPayloadMessage();
		redisPayloadMessage.setCmd(redisMsg.getClass().getSimpleName());
		redisPayloadMessage.setPayload(JSON.toJSONString(redisMsg));
		String channel = getBroadcastChannel();
		Jedis jedis = jedisPool.getResource();
		if(jedis == null)
		{
			logger.error("发送到服务器频道,jedis instance failed");
			return;
		}
		
		jedis.publish(channel,JSON.toJSONString(redisPayloadMessage));
		jedis.close();
//		jedisPool.returnResourceObject(jedis);
		
	}
	
	public void push(String channel,String message){
		Jedis jedis = jedisPool.getResource();
		if(jedis == null)
		{
			logger.error("发送到服务器频道,jedis instance failed");
			return;
		}
		
		try {
			jedis.publish(channel,message);
		} finally {
			jedis.close();
		}
	}
	
}
