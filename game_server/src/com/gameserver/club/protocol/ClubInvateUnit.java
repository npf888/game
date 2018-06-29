package com.gameserver.club.protocol;

import com.gameserver.club.redis.ClubData;
import com.gameserver.common.Globals;
import com.gameserver.fw.ClubCache;

public class ClubInvateUnit {
	private String clubId;
	private int ico;
	private String name;
	private int type;
	private int level;
	private int limit;
	private int num;
	private int maxNum;
	private int huoyue;
	private long createTime;

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

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
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public static ClubInvateUnit toProtocol(ClubData cd, long inviteTs) {
		ClubInvateUnit protocol = new ClubInvateUnit();
		protocol.setHuoyue(cd.getHuoyue());
		protocol.setIco(cd.getIco());
		protocol.setLevel(cd.getLevel());
		protocol.setLimit(cd.getDuanweiLimit());
		protocol.setMaxNum(Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel()).getCluNum());
		protocol.setName(cd.getName());
		protocol.setNum(ClubCache.getClubMembersOfClub(cd.getId()).size());
		protocol.setType(cd.getClub_type());
		protocol.setCreateTime(inviteTs);
		protocol.setClubId(cd.getId());
		return protocol;
	}
}
