package com.gameserver.redis.msg_processing.processor;

import redis.clients.jedis.Jedis;

import com.gameserver.club.redis.ClubMemberData;
import com.gameserver.common.Globals;
import com.gameserver.fw.ClubCache;
import com.gameserver.redis.enums.RedisKey;
import com.gameserver.redis.msg_processing.ChannelProcessor;

public class ClubMemberDataChangedProcessor implements ChannelProcessor {
	
	public static final String channel = "cmd_chgd";
	@Override
	public String getChannel() {
		return channel;
	}
	
	
	@Override
	public void doProcess(String data) {
		Jedis j = null;
		try {
			j = Globals.getRedisService().getJedisPool().getResource();
			String[] ss = data.split("\\|");
			String clubId = ss[0];
			String memberId = ss[1];
			String x = j.hget(RedisKey.CLUB_MEMBER_INFO_HASH__+clubId, memberId);
			ClubCache.putClubMember(clubId, Globals.gson.fromJson(x, ClubMemberData.class));
		} finally {
			if(j!=null)
			{
				j.close();
			}
		}
	}
}
