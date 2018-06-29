package com.gameserver.club.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.common.constants.Loggers;
import com.gameserver.club.ClubZhiwu;
import com.gameserver.club.msg.GCClubInfo;
import com.gameserver.club.msg.GCClubInfoGet;
import com.gameserver.club.msg.GCClubMemberList;
import com.gameserver.club.protocol.ClubInfoGetUnit;
import com.gameserver.club.protocol.ClubInfoUnit;
import com.gameserver.club.protocol.ClubMemberListUnit;
import com.gameserver.common.Globals;
import com.gameserver.consts.ClubConsts;
import com.gameserver.fw.ClubCache;
import com.gameserver.player.Player;
import com.gameserver.redis.enums.RedisKey;

import redis.clients.jedis.Jedis;

public class ClubData {
    private String id;
    private int ico;
//    private int club_limit;
    private int level;
    private int club_type;
    private int money;
    private int exp;
    private int totalGongXian;
    private int huoyue;
    private String name;
    private Date create_time;
    private String notice;
    private int duanweiLimit;
    private long tanheSponsor;
    private long tanheStartTs;
    private Set<String> additionalIco = new HashSet<>();
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIco() {
		return ico;
	}

	public void setIco(int ico) {
		this.ico = ico;
	}

//	public int getClub_limit() {
//		return club_limit;
//	}
//
//	public void setClub_limit(int club_limit) {
//		this.club_limit = club_limit;
//	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getClub_type() {
		return club_type;
	}

	public void setClub_type(int club_type) {
		this.club_type = club_type;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public ClubProperties getClubProperties() {
		Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(id);
		int huoyue = 0;
		int gongxian = 0;
		for(ClubMemberData cmd : map.values())
		{
			huoyue+=cmd.getHuoyue();
			gongxian+=cmd.getGongxian();
		}
		return new ClubProperties(huoyue, gongxian);
	}
	
	public static class ClubProperties
	{
		public int huoyue;
		public int gongxian;
		public ClubProperties(int huoyue, int gongxian)
		{
			this.huoyue = huoyue;
			this.gongxian = gongxian;
		}
	}
	public void setHuoyue(int huoyue) {
		this.huoyue = huoyue;
	}
	public int getHuoyue() {
		return huoyue;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public GCClubInfo getGCClubInfo(int zhiwu) {
		GCClubInfo gCClubInfo = new GCClubInfo();
		ClubInfoUnit cu = new ClubInfoUnit();
		cu.setIco(getIco());
		cu.setHuoyue(getHuoyue());
		cu.setLevel(getLevel());
        countBboysAndGirlRate(cu);
        cu.setMoney(getMoney());
        cu.setName(getName());
        cu.setNotice(getNotice());
        cu.setProgress(getExp());
        Jedis j = null;
        try {
        	j = Globals.getRedisService().getJedisPool().getResource();
            int rankHuoYue = j.zrank(RedisKey.CLUB_RANK_HUO_YUE_SORT_SET, id).intValue()+1;
            cu.setRankHuoYue(rankHuoYue);
            int rankGongXian = j.zrank(RedisKey.CLUB_RANK_GONG_XIAN_SORT_SET, id).intValue()+1;
            cu.setRankGongXian(rankGongXian);
            cu.setLimit(getDuanweiLimit());
            cu.setClubType(getClub_type());
            String json = j.get(RedisKey.CLUB_SEASON_KEY);
            ClubSeasonData csd = Globals.gson.fromJson(json, ClubSeasonData.class);
            cu.setSeasonEndSecond((csd.endTs-System.currentTimeMillis())/1000L);
            cu.setZhiwu(zhiwu);
            String[] icons = new String[additionalIco.size()];
            this.additionalIco.toArray(icons);
            cu.setAdditionalIco(icons);
            gCClubInfo.setClubInfo(cu);
		} finally {
			if(j!=null) {j.close();}
		}
        return gCClubInfo;
    }

	public GCClubInfoGet getGCClubInfoGet() {
		GCClubInfoGet gcClubInfoGet = new GCClubInfoGet();
		ClubInfoGetUnit cu = new ClubInfoGetUnit();
		cu.setId(id);
		cu.setLimit(duanweiLimit);
		cu.setClubType(club_type);
		cu.setIco(getIco());
		countBoysAndGirlRate(cu);
		cu.setHuoyue(getHuoyue());
		cu.setLevel(getLevel());
		cu.setMoney(getMoney());
		cu.setName(getName());
		cu.setNotice(getNotice());
		cu.setProgress(getExp());
		Jedis j = null;
		try {
			j = Globals.getRedisService().getJedisPool().getResource();
			int rankHuoYue = j.zrank(RedisKey.CLUB_RANK_HUO_YUE_SORT_SET, id).intValue()+1;
	        cu.setRankHuoYue(rankHuoYue);
	        int rankGongXian = j.zrank(RedisKey.CLUB_RANK_GONG_XIAN_SORT_SET, id).intValue()+1;
	        cu.setRankGongXian(rankGongXian);
	        gcClubInfoGet.setClubInfo(cu);
		} finally {
			if(j!=null) {j.close();}
		}
		return gcClubInfoGet;
  }
    /**
     * 男女计数
     *
     * @return
     */
    public void countBboysAndGirlRate(ClubInfoUnit clubInfoUnit) {
        int boy = 0;
        int girl = 0;
        Map<Long, ClubMemberData> members = ClubCache.getClubMembersOfClub(this.getId());
        if(members!=null)
        {
        	 for (ClubMemberData model : members.values()) {
                 if (model.getSex() == 1) {
                     boy++;
                 } else {
                     girl++;
                 }
             }
        }
        clubInfoUnit.setMale(boy);
        clubInfoUnit.setFemale(girl);
    }
    
    public void countBoysAndGirlRate(ClubInfoGetUnit clubInfoGetUnit) {
        int boy = 0;
        int girl = 0;
        Map<Long, ClubMemberData> members = ClubCache.getClubMembersOfClub(this.getId());
        if(members!=null)
        {
        	 for (ClubMemberData model : members.values()) {
                 if (model.getSex() == 1) {
                     boy++;
                 } else {
                     girl++;
                 }
             }
        }
        clubInfoGetUnit.setMale(boy);
        clubInfoGetUnit.setFemale(girl);
    }
    /**
     * 成员列表
     * @return
     */
    public GCClubMemberList buildGCClubMemberList() {
        GCClubMemberList ret = new GCClubMemberList();
        Map<Long, ClubMemberData> members = ClubCache.getClubMembersOfClub(this.getId());
        List<ClubMemberData> needUpdate = new ArrayList<>();
        ClubMemberListUnit[] array = new ClubMemberListUnit[members.size()];
        int i = 0;
        for (ClubMemberData data : members.values()) {
        	if(tanheSponsor==-1)
        	{
        		int days = Globals.getClubTemplateService().getClubTemplateByLevel(this.level).getAccusetime();
        		long x = System.currentTimeMillis()-days*24*3600*1000L;
        		if(tanheStartTs<x)
        		{
        			tanheSponsor = 0;
        			tanheStartTs = 0;
        			ClubCache.putClub(id, this);
        		}
        		data.setTanheState(0);
        		data.setTanheVote(0);
        		needUpdate.add(data);
        	}
        	
        	else if(data.getZhiwu()==ClubZhiwu.ZHUXI.getLevel())
            {
            	switch(data.getTanheState())
            	{
            	case ClubConsts.STATE_TANHE_CANNOT:
            	{
            		long x = System.currentTimeMillis()-30*24*3600*1000L;
        			Player playerApplying = Globals.getOnlinePlayerService().getPlayerByPassportId(data.getId());
        			if(playerApplying==null && data.getOfflineTime()<x)
        			{
        				data.setTanheState(ClubConsts.STATE_TANHE_CAN);
        				needUpdate.add(data);
        			}
        			break;
            	}
            	case ClubConsts.STATE_TANHE_ING:
            	{
            		long x = System.currentTimeMillis()-24*3600*1000L;
            		if(tanheStartTs<x)
            		{
            			this.tanheSponsor = -1;
            			ClubCache.putClub(id, this);
            			data.setTanheState(ClubConsts.STATE_TANHE_FAIL);
            			needUpdate.add(data);
            		}
            		break;
            	}
            	}
            	
            		
            }
            array[i++] = ClubMemberListUnit.toProtocol(data, id);
        }
        ret.setList(array);
        for(ClubMemberData cmd : needUpdate)
        {
        	ClubCache.putClubMember(id, cmd);
        }
        return ret;
    }
    
    public static void main(String[] args) {
    	ClubData cd = new ClubData();
    	cd.id = "123123";
    	cd.ico = 2;
    	cd.setDuanweiLimit(20);
    	cd.level = 40;
    	cd.club_type = 20;
    	cd.create_time = new Date();
    	cd.notice = "123";
    	
		String s = Globals.gson.toJson(cd);
		
		ClubData xx = Globals.gson.fromJson(s, cd.getClass());
		Loggers.serverLogger.info(s);
		Loggers.serverLogger.info(xx.toString());
	}

	public int getDuanweiLimit() {
		return duanweiLimit;
	}

	public void setDuanweiLimit(int duanweiLimit) {
		this.duanweiLimit = duanweiLimit;
	}

	public long getTanheSponsor() {
		return tanheSponsor;
	}

	public void setTanheSponsor(long tanheSponsor) {
		this.tanheSponsor = tanheSponsor;
	}

	public Set<String> getAdditionalIco() {
		return additionalIco;
	}

	public void setAdditionalIco(Set<String> additionalIco) {
		this.additionalIco = additionalIco;
	}

	public long getTanheStartTs() {
		return tanheStartTs;
	}

	public void setTanheStartTs(long tanheStartTs) {
		this.tanheStartTs = tanheStartTs;
	}

	public int getTotalGongXian() {
		return totalGongXian;
	}

	public void setTotalGongXian(int totalGongXian) {
		this.totalGongXian = totalGongXian;
	}
	
	public double calculateScoreForHuoYue()
	{
		return -(huoyue*ClubConsts.integer_coefficient+level+money/ClubConsts.decimal_coefficient);
	}
	public double calculateScoreForGongXian()
	{
		return -(money*ClubConsts.integer_coefficient+level+huoyue/ClubConsts.decimal_coefficient);
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}
	

}
