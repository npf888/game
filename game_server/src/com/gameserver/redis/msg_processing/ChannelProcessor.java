package com.gameserver.redis.msg_processing;

public interface ChannelProcessor {
	public String getChannel();
	public void doProcess(String data);
}
