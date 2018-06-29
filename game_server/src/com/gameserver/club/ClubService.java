package com.gameserver.club;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.common.constants.LangConstants;
import com.core.async.AsyncIoOpeOnly;
import com.gameserver.club.msg.utils.ClubMessageUtils;
import com.gameserver.club.redis.ClubData;
import com.gameserver.club.redis.ClubMemberData;
import com.gameserver.club.redis.ClubSeasonData;
import com.gameserver.club.template.ClubListTemplate;
import com.gameserver.club.template.ClubTemplate;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.consts.ClubConsts;
import com.gameserver.fw.ClubCache;
import com.gameserver.human.Human;
import com.gameserver.mail.MailLogic;
import com.gameserver.redis.enums.RedisKey;

import redis.clients.jedis.Jedis;

/**
 * 
 */
public class ClubService implements InitializeRequired,AfterInitializeRequired {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ClubService.class);
	
	public static Lock clubOperationLock = new  ReentrantLock();
	@Override
	public void afterInit() {
//		startSeason();
	}

	@Override
	public void init() {
	}
	public void startSeason()
	{
		Jedis j = null;
		try {
			j = Globals.getRedisService().getJedisPool().getResource();
			String json = j.get(RedisKey.CLUB_SEASON_KEY);
			ClubSeasonData csd = Globals.gson.fromJson(json, ClubSeasonData.class);
			logger.info("csd.startTs: "+csd.startTs+", csd.endTs: "+csd.endTs);
			long interval = csd.endTs-csd.startTs;
			long now = System.currentTimeMillis();
			long x = (now-csd.endTs)%interval;
			if(now>=csd.endTs)
			{
				doSettlement();
			}
			
			
			long delay = 0;
			if(x>0)
			{
				delay = interval-x;
				csd.startTs = now-x;
				csd.endTs = csd.startTs+interval;
			}
			else
			{
				delay = -x;
				csd.endTs = now+delay;
				csd.startTs = csd.endTs-interval;
			}
			j.set(RedisKey.CLUB_SEASON_KEY, Globals.gson.toJson(csd));
			logger.info("csd.startTs: "+csd.startTs+", csd.endTs: "+csd.endTs);
			
			TimerTask tt = new TimerTask() {
				@Override
				public void run() {
					Jedis j = null;
					try {
						doSettlement();
						j = Globals.getRedisService().getJedisPool().getResource();
						String json = j.get(RedisKey.CLUB_SEASON_KEY);
						ClubSeasonData csd = Globals.gson.fromJson(json, ClubSeasonData.class);
						long interval = csd.endTs-csd.startTs;
						csd.startTs = csd.endTs;
						csd.endTs = csd.startTs+interval;
						j.set(RedisKey.CLUB_SEASON_KEY, Globals.gson.toJson(csd));
						
						logger.info("csd.startTs: "+csd.startTs+", csd.endTs: "+csd.endTs);
					} finally {
						if(j!=null)
						{
							j.close();
						}
					}
				}
			};
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(tt, delay, interval);
			logger.info("delay: "+delay+", interval: "+interval);
		} finally {
			if(j!=null)j.close();
		}
	}
	public void doSettlement()
	{
		clubOperationLock.lock();
		Jedis j = null;
		try {
			j = Globals.getRedisService().getJedisPool().getResource();
			for(ClubListTemplate clt : Globals.getClubTemplateService().HUO_YUE_LIST)
			{
				String title = String.valueOf(LangConstants.CLUB_SEASON_HUO_YUE_REWARD);
				List<RandRewardData> list = new ArrayList<RandRewardData>();
				RandRewardData data = new RandRewardData();
				data.setRewardCount(clt.getRewardNum1());
				data.setRewardId(clt.getReward1());
				list.add(data);
				Set<String> ss = j.zrange(RedisKey.CLUB_RANK_HUO_YUE_SORT_SET, clt.getList1()-1, clt.getList2()-1);
				int rank = clt.getList1();
				for(String s : ss)
				{
					String strContent = String.valueOf(LangConstants.CLUB_SEASON_HUO_YUE_REWARD_CONTENT)+","+String.valueOf(rank++);
					ClubData cd = ClubCache.getClubs(s);
					String icon = clt.getReward2();
					if(!icon.equals("0"))
					{
						if(cd!=null)
						{
							cd.getAdditionalIco().add(icon);
							ClubCache.putClub(s, cd);
						}
					}
					Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(s);
					List<Long> listId = new ArrayList<>();
					ClubTemplate ct = Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel());
					for(ClubMemberData cmd : map.values())
					{
						if(cmd.getHuoyue()>=ct.getActivelimit())
						{
							listId.add(cmd.getId());
						}
					}
					
					//发邮件
					MailLogic.getInstance().systemSendMail(null, title ,strContent, listId, list);
				}
			}
			
			for(ClubListTemplate clt : Globals.getClubTemplateService().GONG_XIAN_LIST)
			{
				String title = String.valueOf(LangConstants.CLUB_SEASON_GONG_XIAN_REWARD);
				List<RandRewardData> list = new ArrayList<RandRewardData>();
				RandRewardData data = new RandRewardData();
				data.setRewardCount(clt.getRewardNum1());
				data.setRewardId(clt.getReward1());
				list.add(data);
				Set<String> ss = j.zrange(RedisKey.CLUB_RANK_GONG_XIAN_SORT_SET, clt.getList1()-1, clt.getList2()-1);
				int rank = clt.getList1();
				for(String s : ss)
				{
					String strContent = String.valueOf(LangConstants.CLUB_SEASON_GONG_XIAN_REWARD_CONTENT)+","+String.valueOf(rank++);
					ClubData cd = ClubCache.getClubs(s);
					String icon = clt.getReward2();
					if(!icon.equals("0"))
					{
						if(cd!=null)
						{
							cd.getAdditionalIco().add(icon);
							ClubCache.putClub(s, cd);
						}
					}
					
					Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(s);
					List<Long> listId = new ArrayList<>();
					ClubTemplate ct = Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel());
					for(ClubMemberData cmd : map.values())
					{
						if(cmd.getHuoyue()>=ct.getActivelimit())
						{
							listId.add(cmd.getId());
						}
					}
					
					//发邮件
					MailLogic.getInstance().systemSendMail(null, title ,strContent, listId, list);
				}
			}
			
			Set<String> ids = j.hkeys(RedisKey.CLUB_INFO_HASH);
			for(String clubId : ids)
			{
				String json = j.hget(RedisKey.CLUB_INFO_HASH, clubId);
				ClubData cd = Globals.gson.fromJson(json, ClubData.class);
				cd.setHuoyue(0);
				cd.setMoney(0);
				j.hset(RedisKey.CLUB_INFO_HASH, clubId, Globals.gson.toJson(cd));
				ClubCache.removetClubFromMemory(clubId);
				Map<String, String> map = j.hgetAll(RedisKey.CLUB_MEMBER_INFO_HASH__+clubId);
				for(Entry<String, String> e : map.entrySet())
				{
					ClubMemberData cmd = Globals.gson.fromJson(e.getValue(), ClubMemberData.class);
					cmd.setHuoyue(0);
					cmd.setGongxian(0);
					j.hset(RedisKey.CLUB_MEMBER_INFO_HASH__+clubId, e.getKey(), Globals.gson.toJson(cmd));
				}
				ClubCache.removeClubMembersFromMemory(clubId);
				j.zadd(RedisKey.CLUB_RANK_HUO_YUE_SORT_SET, 0, clubId);
				j.zadd(RedisKey.CLUB_RANK_GONG_XIAN_SORT_SET, 0, clubId);
			}
		} finally {
			clubOperationLock.unlock();
			if(j!=null) {j.close();}
		}
		
	}
	
	public void addHuoYueForLaohujiWin(Human human, long reward)
	{
		clubOperationLock.lock();
		try
		{
			String clubId = human.getClubId();
			ClubData cd =  ClubCache.getClubs(clubId);
			if(cd==null)
			{
				return;
			}
			ClubMemberData cmd = ClubCache.retrieveMemberIfExist(clubId, human.getPassportId());
			if(cmd==null)
			{
				logger.error("passportId: "+human.getPassportId()+" has no clubMemberData in clubId: "+clubId);
				return;
			}
			ClubTemplate ct = Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel());
			int addHuoYue = (int)(reward/ct.getConvert());
			cmd.setHuoyue(cmd.getHuoyue()+addHuoYue);
			ClubCache.putClubMember(clubId, cmd);
			cd.setHuoyue(cd.getHuoyue()+addHuoYue);
			ClubCache.putClub(clubId, cd);
			ClubMessageUtils.pushClubInfoToClubMembers(cd);
			Jedis j = null;
			try {
				j = Globals.getRedisService().getJedisPool().getResource();
				j.zincrby(RedisKey.CLUB_RANK_HUO_YUE_SORT_SET, -addHuoYue * ClubConsts.integer_coefficient, cd.getId());
			} finally {
				if (j != null) {j.close();}
			}
		}
		finally {
			clubOperationLock.unlock();
		}
	}
	
	public void updateHumanInfoToClub(Human human)
	{
		Globals.getAsyncService().createOperationAndExecuteAtOnce(new AsyncIoOpeOnly() {
			@Override
			public int doIo() {
				clubOperationLock.lock();
				try {
					String clubId = human.getClubId();
					ClubMemberData cmd = ClubCache.retrieveMemberIfExist(clubId, human.getPassportId());
					if(cmd!=null)
					{
						cmd.setCountries(human.getCountries());
						cmd.setLevel((int)human.getLevel());
						cmd.setVipLevel(human.getHumanVipNewManager().getVipLv());
						cmd.setName(human.getName());
						cmd.setImg(human.getImg());
						cmd.setSex(human.getGirl());
						ClubCache.putClubMember(clubId, cmd);
					}
				} finally {
					clubOperationLock.unlock();
				}
				return STAGE_IO_DONE;
			}
		});
	}
}
