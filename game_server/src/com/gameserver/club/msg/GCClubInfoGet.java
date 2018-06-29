package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 获取俱乐部信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubInfoGet extends GCMessage{
	
	/** 俱乐部信息get */
	private com.gameserver.club.protocol.ClubInfoGetUnit clubInfo;

	public GCClubInfoGet (){
	}
	
	public GCClubInfoGet (
			com.gameserver.club.protocol.ClubInfoGetUnit clubInfo ){
			this.clubInfo = clubInfo;
	}

	@Override
	protected boolean readImpl() {
		clubInfo = new com.gameserver.club.protocol.ClubInfoGetUnit();
					clubInfo.setId(readString());
							clubInfo.setName(readString());
							clubInfo.setIco(readInteger());
							clubInfo.setLevel(readInteger());
							clubInfo.setProgress(readInteger());
							clubInfo.setNotice(readString());
							clubInfo.setHuoyue(readInteger());
							clubInfo.setMoney(readInteger());
							clubInfo.setMale(readInteger());
							clubInfo.setFemale(readInteger());
							clubInfo.setRankHuoYue(readInteger());
							clubInfo.setRankGongXian(readInteger());
							clubInfo.setClubType(readInteger());
							clubInfo.setLimit(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(clubInfo.getId());
		writeString(clubInfo.getName());
		writeInteger(clubInfo.getIco());
		writeInteger(clubInfo.getLevel());
		writeInteger(clubInfo.getProgress());
		writeString(clubInfo.getNotice());
		writeInteger(clubInfo.getHuoyue());
		writeInteger(clubInfo.getMoney());
		writeInteger(clubInfo.getMale());
		writeInteger(clubInfo.getFemale());
		writeInteger(clubInfo.getRankHuoYue());
		writeInteger(clubInfo.getRankGongXian());
		writeInteger(clubInfo.getClubType());
		writeInteger(clubInfo.getLimit());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CLUB_INFO_GET;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_INFO_GET";
	}

	public com.gameserver.club.protocol.ClubInfoGetUnit getClubInfo(){
		return clubInfo;
	}
		
	public void setClubInfo(com.gameserver.club.protocol.ClubInfoGetUnit clubInfo){
		this.clubInfo = clubInfo;
	}
}