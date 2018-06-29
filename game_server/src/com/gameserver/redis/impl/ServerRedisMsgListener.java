package com.gameserver.redis.impl;


import org.slf4j.Logger;

import redis.clients.jedis.JedisPubSub;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.common.constants.Loggers;
import com.core.msg.MessageParseException;
import com.gameserver.common.Globals;
import com.gameserver.redis.IRedisMessage;
import com.gameserver.redis.RedisMsgRecognizer;
import com.gameserver.redis.RedisPayloadMessage;

/**
 * 
 * @author wayne
 *
 */
public class ServerRedisMsgListener extends JedisPubSub{
	
	private Logger logger = Loggers.redisLogger;
	
	private RedisMsgRecognizer  redisMsgRecognizer;
	
	public ServerRedisMsgListener()
	{
		redisMsgRecognizer = RedisMsgRecognizer.getInstance();
	}
	
	 // 取得订阅的消息后的处理  
	@Override
    public void onMessage(String channel, String message) {  
    	logger.info(channel+ "=" + message);
    	RedisPayloadMessage redisPayloadMessage= null;
    	try{
    		redisPayloadMessage = JSON.parseObject(message, RedisPayloadMessage.class);
    	}
    	catch(JSONException jsonEx)
    	{
    		logger.error("payload message decode error"+jsonEx.toString());
    		return;
    	}
    	
    	
    	IRedisMessage redisMessage= null;
    	try{
    		redisMessage = redisMsgRecognizer.createMessageImpl(redisPayloadMessage.getCmd(), redisPayloadMessage.getPayload());
    		
    		Globals.getGlobalLogicRunner().putRedisMsg(redisMessage);
    		
    		
    	}
    	catch(MessageParseException ex)
    	{
    		logger.error("payload message decode error "+ex.toString());
    		return;
    	}
    }  
  
    // 初始化订阅时候的处理  
	@Override
    public void onSubscribe(String channel, int subscribedChannels) {  
		logger.info("subscribe ["+channel+"],"+channel+ "=" + subscribedChannels);
    }  
  
    // 取消订阅时候的处理  
	@Override
    public void onUnsubscribe(String channel, int subscribedChannels) {  
        
		logger.info("unsubscribe ["+channel+"],"+channel+ "=" + subscribedChannels);
    }  
  
    // 初始化按表达式的方式订阅时候的处理  
	@Override
    public void onPSubscribe(String pattern, int subscribedChannels) { 
		logger.info("psubscribe ["+pattern+"],"+pattern+ "=" + subscribedChannels);
        
    }  
  
    // 取消按表达式的方式订阅时候的处理  
	@Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) { 
		logger.info("punsubscribe ["+pattern+"],"+pattern+ "=" + subscribedChannels);
      
    }  
  
    // 取得按表达式的方式订阅的消息后的处理  
	@Override
    public void onPMessage(String pattern, String channel, String message) {  
		logger.info("PMessage pattern["+pattern+"],"+"channel["+channel+"],"+"message["+message+"]");
    }  
}
