package com.gameserver.club.protocol;

import redis.clients.jedis.Jedis;

import com.gameserver.club.redis.ClubData;
import com.gameserver.common.Globals;
import com.gameserver.fw.ClubCache;
import com.gameserver.redis.enums.RedisKey;

public class ClubListUnit {
	private String clubId;//俱乐部id
	private int ico;//图标
	private String name;//名字
	private int type;//类型
	private int level;//等级
	private int limit;//段位
	private int num;//人数
	private int maxNum;//人数上限
	private int huoyue;//活跃度
	private int gongxian;//贡献
	private int applied;//是否申请过  申请过: 1
	public int getIco() {
		return ico;
	}
	public void setIco(int ico) {
		this.ico = ico;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	public int getHuoyue() {
		return huoyue;
	}
	public void setHuoyue(int huoyue) {
		this.huoyue = huoyue;
	}
	public String getClubId() {
		return clubId;
	}
	public void setClubId(String clubId) {
		this.clubId = clubId;
	}
	public int getGongxian() {
		return gongxian;
	}
	public void setGongxian(int gongxian) {
		this.gongxian = gongxian;
	}

//	public static ClubListUnit toProtocol(ClubModel clubModel) {
//		ClubListUnit protocol = new ClubListUnit();
//		protocol.setHuoyue(clubModel.getHuoyue());
//		protocol.setIco(clubModel.getIco());
//		protocol.setLevel(clubModel.getLevel());
//		protocol.setLimit(clubModel.getClub_limit());
//		protocol.setMaxNum(clubModel.getMax_num());
//		protocol.setName(clubModel.getName());
//		protocol.setNum(clubModel.getMax_num());
//		protocol.setType(clubModel.getClub_type());
//		protocol.setClubId(clubModel.getId());
//		protocol.setGongxian(clubModel.getMoney());
//		return protocol;
//	}
	
	public static ClubListUnit toProtocol(ClubData clubData) {
		ClubListUnit protocol = new ClubListUnit();
		protocol.setHuoyue(clubData.getHuoyue());
		protocol.setIco(clubData.getIco());
		protocol.setLevel(clubData.getLevel());
		protocol.setLimit(clubData.getDuanweiLimit());
		protocol.setMaxNum(Globals.getClubTemplateService().getClubTemplateByLevel(clubData.getLevel()).getCluNum());
		protocol.setName(clubData.getName());
		protocol.setNum(ClubCache.getClubMembersOfClub(clubData.getId()).size());
		protocol.setType(clubData.getClub_type());
		protocol.setClubId(clubData.getId());
		protocol.setGongxian(clubData.getMoney());
		Double d = null;
		Jedis j = null;
		try {
			j = Globals.getRedisService().getJedisPool().getResource();
			d = j.zscore(RedisKey.CLUB_APPLY_IN_HASH__ + clubData.getId(), "");
		} finally {
			if (j != null) {
				j.close();
			}
		}
		protocol.setApplied(d == null ? 0 : 1);
		return protocol;
	}
	public int getApplied() {
		return applied;
	}
	public void setApplied(int applied) {
		this.applied = applied;
	}
}
