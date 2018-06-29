package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 获取俱乐部信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubInfo extends GCMessage{
	
	/** 俱乐部信息 */
	private com.gameserver.club.protocol.ClubInfoUnit clubInfo;

	public GCClubInfo (){
	}
	
	public GCClubInfo (
			com.gameserver.club.protocol.ClubInfoUnit clubInfo ){
			this.clubInfo = clubInfo;
	}

	@Override
	protected boolean readImpl() {
		clubInfo = new com.gameserver.club.protocol.ClubInfoUnit();
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
							clubInfo.setZhiwu(readInteger());
							clubInfo.setSeasonEndSecond(readLong());
						{
			int subCount = readShort();
							String[] subList = new String[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readString();
									}
			clubInfo.setAdditionalIco(subList);
		}
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
		writeInteger(clubInfo.getZhiwu());
		writeLong(clubInfo.getSeasonEndSecond());
		String[] additionalIco=clubInfo.getAdditionalIco();
		writeShort(additionalIco.length);
		for(int i=0; i<additionalIco.length; i++){	
				writeString(additionalIco[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CLUB_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_INFO";
	}

	public com.gameserver.club.protocol.ClubInfoUnit getClubInfo(){
		return clubInfo;
	}
		
	public void setClubInfo(com.gameserver.club.protocol.ClubInfoUnit clubInfo){
		this.clubInfo = clubInfo;
	}
}