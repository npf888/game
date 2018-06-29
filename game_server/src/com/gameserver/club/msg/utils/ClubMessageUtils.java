package com.gameserver.club.msg.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import com.gameserver.club.msg.GCClubDonateData;
import com.gameserver.club.msg.GCClubInfo;
import com.gameserver.club.msg.GCClubNoteList;
import com.gameserver.club.msg.GCClubSignData;
import com.gameserver.club.protocol.ClubNoteUnit;
import com.gameserver.club.redis.BoardMsgData;
import com.gameserver.club.redis.ClubData;
import com.gameserver.club.redis.ClubMemberData;
import com.gameserver.common.Globals;
import com.gameserver.fw.ClubCache;
import com.gameserver.player.OnlinePlayerService;
import com.gameserver.player.Player;
import com.gameserver.redis.enums.RedisKey;

public class ClubMessageUtils {
	/**
	 * 
	 * @return
	 */
	public static GCClubSignData getGCClubSignData(int level, long lastTs) {
		GCClubSignData data = new GCClubSignData();
		long now = System.currentTimeMillis();
		long interval = Globals.getClubTemplateService().getClubTemplateByLevel(level).getSignTime() * 60 * 60 * 1000L;
		if (now - lastTs >= interval) {
			data.setCanSign(1);
		} else {
			data.setCanSign(0);
			data.setNextSignTime(lastTs + interval - now);
		}

		return data;
	}

	/**
	 * 
	 * @return
	 */
	public static GCClubDonateData getGCClubDonateData(int level, long lastTs) {
		GCClubDonateData data = new GCClubDonateData();
		long now = System.currentTimeMillis();
		long interval = Globals.getClubTemplateService().getClubTemplateByLevel(level).getDonateTime() * 60 * 60 * 1000L;
		if (now - lastTs >= interval) {
			data.setCanDonate(1);
		} else {
			data.setCanDonate(0);
			data.setNextSignTime(lastTs + interval - now);
		}

		return data;
	}
	
	public static void pushClubInfoToClubMembers(ClubData cd)
	{
		String clubId = cd.getId();
		Map<Long, ClubMemberData> map  = ClubCache.getClubMembersOfClub(clubId);
		OnlinePlayerService os = Globals.getOnlinePlayerService();
		for(ClubMemberData cmd : map.values())
		{
			Player p = os.getPlayerByPassportId(cmd.getId());
			if(p!=null)
			{
				GCClubInfo clubInfo = cd.getGCClubInfo(cmd.getZhiwu());
				p.sendMessage(clubInfo);
			}
		}
	}
	
	public static GCClubNoteList buildGCClubNoteList(String clubId)
	{
		GCClubNoteList gCClubNoteList = new GCClubNoteList();
		Jedis j = null;
		try {
			j = Globals.getRedisService().getJedisPool().getResource();
	        Set<Tuple> l = j.zrevrangeWithScores(RedisKey.CLUB_BOARD_SORT_SET__+clubId, 0, 49);
	        gCClubNoteList.setOpType(1);
	        List<ClubNoteUnit> list = new ArrayList<>();
	        for(Tuple t : l)
	        {
	        	String msgId = t.getElement();
	        	String content = j.get(RedisKey.CLUB_BOARD_CONTENT_KEY__ + msgId);
	        	if(StringUtils.isNotEmpty(content))
	        	{
	        		BoardMsgData bm = Globals.gson.fromJson(content, BoardMsgData.class);
	        		list.add(ClubNoteUnit.toProtocol(bm, msgId, Math.round(t.getScore()), clubId, bm.getPassportId()));
	        	}
	        	else
	        	{
	        		j.zrem(RedisKey.CLUB_BOARD_SORT_SET__+clubId, msgId);
	        	}
	        }
	        ClubNoteUnit[] clubNoteUnitslubNoteUnit = new ClubNoteUnit[list.size()];
	        list.toArray(clubNoteUnitslubNoteUnit);
	        gCClubNoteList.setClubNote(clubNoteUnitslubNoteUnit);
		} finally {
			if(j!=null) {j.close();}
		}
        return gCClubNoteList;
	}
	
	
    public static void pushBoardList(String clubId)
    {
        GCClubNoteList res = ClubMessageUtils.buildGCClubNoteList(clubId);
        OnlinePlayerService os = Globals.getOnlinePlayerService();
        for(ClubMemberData x : ClubCache.getClubMembersOfClub(clubId).values())
        {
        	Player p = os.getPlayerByPassportId(x.getId());
        	if(p!=null)
        	{
        		p.sendMessage(res);
        	}
        }
    }
}
