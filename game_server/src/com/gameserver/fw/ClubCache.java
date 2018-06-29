package com.gameserver.fw;

import java.lang.ref.SoftReference;
//import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.gameserver.club.redis.ClubData;
import com.gameserver.club.redis.ClubMemberData;
import com.gameserver.common.Globals;
import com.gameserver.redis.enums.RedisKey;

public class ClubCache {
	private static final Logger logger = LoggerFactory.getLogger(ClubCache.class);
	
	private static SoftReference<ConcurrentHashMap<String, ClubData>> clubs = new SoftReference<>(new ConcurrentHashMap<>());
	private static SoftReference<ConcurrentHashMap<String, Map<Long, ClubMemberData>>> clubMembers = new SoftReference<>(new ConcurrentHashMap<>());
	
	
	public static ClubData getClubs(String clubId) {
		if(StringUtils.isEmpty(clubId))
		{
			return null;
		}
		ConcurrentHashMap<String, ClubData> all = clubs.get();
		if(all==null)
		{
			all = new ConcurrentHashMap<>();
			clubs = new SoftReference<ConcurrentHashMap<String,ClubData>>(all);
		}
		ClubData cd = all.get(clubId);
		if(cd==null)
		{
			Jedis j = null;
			try {
				j = Globals.getRedisService().getJedisPool().getResource();
				String json = j.hget(RedisKey.CLUB_INFO_HASH, clubId);
				if(StringUtils.isNotEmpty(json))
				{
					cd = Globals.gson.fromJson(json, ClubData.class);
					all.put(cd.getId(), cd);
				}
			} finally {
				if(j!=null){j.close();}
			}
		}
		return cd;
	}
	public static void putClub(String clubId, ClubData cd) {
		ConcurrentHashMap<String, ClubData> all = clubs.get();
		if(all==null)
		{
			all = new ConcurrentHashMap<>();
			clubs = new SoftReference<ConcurrentHashMap<String,ClubData>>(all);
		}
		all.put(clubId, cd);
		Jedis j = null;
		try {
			j = Globals.getRedisService().getJedisPool().getResource();
			j.hset(RedisKey.CLUB_INFO_HASH, clubId, Globals.gson.toJson(cd));
		} finally {
			if(j!=null) {j.close();}
		}
	}
	public static void removeClub(String clubId)
	{
		ConcurrentHashMap<String, ClubData> all = clubs.get();
		if(all!=null)
		{
			all.remove(clubId);
		}
		Jedis j = null;
		try {
			j = Globals.getRedisService().getJedisPool().getResource();
			j.hdel(RedisKey.CLUB_INFO_HASH, clubId);
			
			ConcurrentHashMap<String, Map<Long, ClubMemberData>> members = clubMembers.get();
			if(all!=null)
			{
				members.remove(clubId);
			}
			j.del(RedisKey.CLUB_MEMBER_INFO_HASH__+clubId);
			j.zrem(RedisKey.CLUB_RANK_GONG_XIAN_SORT_SET, clubId);
			j.zrem(RedisKey.CLUB_RANK_HUO_YUE_SORT_SET, clubId);
			
		} finally {
			if(j!=null) {j.close();}
		}
	}
	public static void removetClubFromMemory(String clubId)
	{
		ConcurrentHashMap<String, ClubData> all = clubs.get();
		if(all!=null)
		{
			all.remove(clubId);
		}
	}
	public static Map<Long, ClubMemberData> getClubMembersOfClub(String clubId) {
		ConcurrentHashMap<String, Map<Long, ClubMemberData>> all = clubMembers.get();
		if(all==null)
		{
			all = new ConcurrentHashMap<>();
			clubMembers = new SoftReference<ConcurrentHashMap<String,Map<Long, ClubMemberData>>>(all);
		}
		Map<Long, ClubMemberData> membersOfClub = all.get(clubId);
		if(membersOfClub==null)
		{
			membersOfClub = new ConcurrentHashMap<>();
			all.put(clubId, membersOfClub);
			Jedis j = null;
			try {
				j = Globals.getRedisService().getJedisPool().getResource();
				Map<String, String> map = j.hgetAll(RedisKey.CLUB_MEMBER_INFO_HASH__+clubId);
				if(map!=null)
				{
					for(Entry<String, String> e : map.entrySet())
					{
						membersOfClub.put(Long.valueOf(e.getKey()), Globals.gson.fromJson(e.getValue(), ClubMemberData.class));
					}
				}
			} finally {
				if(j!=null) {j.close();}
			}
		}
		return membersOfClub;
	}
	public static void putClubMember(String clubId, ClubMemberData cmd) {
		Map<Long, ClubMemberData> map = getClubMembersOfClub(clubId);
		map.put(cmd.getId(), cmd);
		Jedis j = null;
		try {
			j = Globals.getRedisService().getJedisPool().getResource();
			j.hset(RedisKey.CLUB_MEMBER_INFO_HASH__+clubId, String.valueOf(cmd.getId()), Globals.gson.toJson(cmd));
		} finally {
			if(j!=null) {j.close();}
		}
	}
	public static void removeClubMember(String clubId, long passportId)
	{
		ConcurrentHashMap<String, Map<Long, ClubMemberData>> all = clubMembers.get();
		if(all!=null)
		{
			Map<Long, ClubMemberData> map = all.get(clubId);
			if(map!=null)
			{
				map.remove(passportId);
			}
		}
		Jedis j = null;
		try {
			j = Globals.getRedisService().getJedisPool().getResource();
			j.hdel(RedisKey.CLUB_MEMBER_INFO_HASH__+clubId, String.valueOf(passportId));
		} finally {
			if(j!=null) {j.close();}
		}
	}
	public static void removeClubMembersFromMemory(String clubId)
	{
		ConcurrentHashMap<String, Map<Long, ClubMemberData>> all = clubMembers.get();
		if(all!=null)
		{
			all.remove(clubId);
		}
	}
	public static ClubMemberData retrieveMemberIfExist(String clubId, Long passportid)
	{
		if(StringUtils.isEmpty(clubId))
		{
			return null;
		}
		Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(clubId);
		if(map!=null)
		{
			ClubMemberData cmd = map.get(passportid);
			if(cmd==null)
			{
				logger.error("clubId: "+clubId + "has no member: "+passportid);
			}
			return cmd;
		}
		else
		{
			logger.error("clubId: "+clubId+" has no member!");
		}
		return null;
	}
}
