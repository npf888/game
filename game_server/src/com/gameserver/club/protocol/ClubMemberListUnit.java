package com.gameserver.club.protocol;

import java.util.Map;

import com.gameserver.club.ClubZhiwu;
import com.gameserver.club.redis.ClubMemberData;
import com.gameserver.common.Globals;
import com.gameserver.consts.ClubConsts;
import com.gameserver.fw.ClubCache;
import com.gameserver.player.Player;

public class ClubMemberListUnit {
	private String name;
	private String ico;
	private int level;
	private String country; 
	private int zhiwu;
	private int gongxian;
	private int huoyue;
	private int online;
	private int inGame;
	private long logoutTime;
	protected long playerId;
	private int tanheState;
	private int agree;
	private int refuse;
	private int selfState;
	private int vipLevel;
	private int girlFlag;
	
	
	public int getInGame() {
		return inGame;
	}


	public void setInGame(int inGame) {
		this.inGame = inGame;
	}


	public int getTanheState() {
		return tanheState;
	}


	public void setTanheState(int tanheState) {
		this.tanheState = tanheState;
	}


	public int getAgree() {
		return agree;
	}


	public void setAgree(int agree) {
		this.agree = agree;
	}


	public int getRefuse() {
		return refuse;
	}


	public void setRefuse(int refuse) {
		this.refuse = refuse;
	}


	public int getSelfState() {
		return selfState;
	}


	public void setSelfState(int selfState) {
		this.selfState = selfState;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getIco() {
		return ico;
	}


	public void setIco(String ico) {
		this.ico = ico;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public int getZhiwu() {
		return zhiwu;
	}


	public void setZhiwu(int zhiwu) {
		this.zhiwu = zhiwu;
	}


	public int getGongxian() {
		return gongxian;
	}


	public void setGongxian(int gongxian) {
		this.gongxian = gongxian;
	}


	public int getHuoyue() {
		return huoyue;
	}


	public void setHuoyue(int huoyue) {
		this.huoyue = huoyue;
	}


	public int getOnline() {
		return online;
	}


	public void setOnline(int online) {
		this.online = online;
	}


	public long getLogoutTime() {
		return logoutTime;
	}


	public void setLogoutTime(long logoutTime) {
		this.logoutTime = logoutTime;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public static ClubMemberListUnit toProtocol(ClubMemberData clubMemberData, String clubId) {
		Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(clubId);
		ClubMemberListUnit protocol = new ClubMemberListUnit();
		protocol.setCountry(clubMemberData.getCountries());
		protocol.setGongxian(clubMemberData.getGongxian());
		protocol.setHuoyue(clubMemberData.getHuoyue());
		Player playerApplying = Globals.getOnlinePlayerService().getPlayerByPassportId(clubMemberData.getId());
		if(playerApplying==null)
		{
			protocol.setOnline(0);
		}
		else
		{
			protocol.setOnline(1);
		}
		protocol.setLevel(clubMemberData.getLevel());
		protocol.setName(clubMemberData.getName());
		protocol.setZhiwu(clubMemberData.getZhiwu());
		protocol.setLogoutTime(clubMemberData.getOfflineTime());
		protocol.setIco(clubMemberData.getImg());
		protocol.setPlayerId(clubMemberData.getId());
		protocol.setSelfState(clubMemberData.getTanheVote());
		if(clubMemberData.getZhiwu()==ClubZhiwu.ZHUXI.getLevel())
		{
			int agree = 0;
			int disagree = 0;
//			List<Long> passportIds = new ArrayList<>();
			for(ClubMemberData cmd : map.values())
			{
//				passportIds.add(cmd.getId());
				switch(cmd.getTanheVote())
				{
				case ClubConsts.OPERATION_TANHE_AGREE:
				{
					agree++;
					break;
				}
				case ClubConsts.OPERATION_TANHE_DISAGREE:
				{
					disagree++;
					break;
				}
				}
			}
			protocol.setAgree(agree);
			protocol.setRefuse(disagree);
			protocol.setTanheState(clubMemberData.getTanheState());
		}
		return protocol;
	}


	public int getVipLevel() {
		return vipLevel;
	}


	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}


	public int getGirlFlag() {
		return girlFlag;
	}


	public void setGirlFlag(int girlFlag) {
		this.girlFlag = girlFlag;
	}
}
