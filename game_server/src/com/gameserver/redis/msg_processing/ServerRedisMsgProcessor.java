package com.gameserver.redis.msg_processing;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;

import redis.clients.jedis.JedisPubSub;

import com.common.constants.Loggers;
import com.gameserver.redis.msg_processing.processor.ClubDataChangedProcessor;
import com.gameserver.redis.msg_processing.processor.ClubMemberDataChangedProcessor;
import com.gameserver.redis.msg_processing.processor.JpushNotifyProcessor;

public class ServerRedisMsgProcessor extends JedisPubSub {
	private static final Logger logger = Loggers.redisLogger;
	private Map<String, ChannelProcessor> channelProcessors = new HashMap<>();
	private ExecutorService siglePool = Executors.newSingleThreadExecutor();
	public void init() {
//		ClubDataChangedProcessor cdp = new ClubDataChangedProcessor();
//		channelProcessors.put(cdp.getChannel(), cdp);
//		ClubMemberDataChangedProcessor cdcp = new ClubMemberDataChangedProcessor();
//		channelProcessors.put(cdcp.getChannel(), cdcp);
		JpushNotifyProcessor jp = new JpushNotifyProcessor();
		channelProcessors.put(jp.getChannel(), jp);
	}
	
	public String[] retrievedSubscribedChannels()
	{
		String[] channels = new String[channelProcessors.size()];
		int i = 0;
		for(ChannelProcessor cp : channelProcessors.values())
		{
			channels[i++] = cp.getChannel();
		}
		return channels;
	}
	
	@Override
	/*redis订阅消息处理
	 * (non-Javadoc)
	 * @see redis.clients.jedis.JedisPubSub#onMessage(java.lang.String, java.lang.String)
	 */
	public void onMessage(String channel, String message) {
		logger.info("received: " + channel + "=>" + message);
		
		siglePool.submit(new Runnable() {
			@Override
			public void run() {
				ChannelProcessor cp = channelProcessors.get(channel);
				if(cp!=null)
				{
					logger.info("will process: " + channel + "=>" + message);
					try
					{
						cp.doProcess(message);
					}
					catch(Throwable t)
					{
						logger.error("", t);
					}
				}
				else
				{
					logger.error("no processor for: " + channel + "=>" + message);
				}
			}
		});
	}
}
