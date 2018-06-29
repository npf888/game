package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 授权
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubPromate extends GCMessage{
	
	/** 类型 0 成功 1 失败 */
	private int ret;
	/** 如果操作成功 成员变化后的信息  */
	private com.gameserver.club.protocol.ClubMemberListUnit info;

	public GCClubPromate (){
	}
	
	public GCClubPromate (
			int ret,
			com.gameserver.club.protocol.ClubMemberListUnit info ){
			this.ret = ret;
			this.info = info;
	}

	@Override
	protected boolean readImpl() {
		ret = readInteger();
		info = new com.gameserver.club.protocol.ClubMemberListUnit();
					info.setPlayerId(readLong());
							info.setName(readString());
							info.setIco(readString());
							info.setLevel(readInteger());
							info.setCountry(readString());
							info.setZhiwu(readInteger());
							info.setGongxian(readInteger());
							info.setHuoyue(readInteger());
							info.setOnline(readInteger());
							info.setInGame(readInteger());
							info.setLogoutTime(readLong());
							info.setTanheState(readInteger());
							info.setAgree(readInteger());
							info.setRefuse(readInteger());
							info.setSelfState(readInteger());
							info.setVipLevel(readInteger());
							info.setGirlFlag(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(ret);
		writeLong(info.getPlayerId());
		writeString(info.getName());
		writeString(info.getIco());
		writeInteger(info.getLevel());
		writeString(info.getCountry());
		writeInteger(info.getZhiwu());
		writeInteger(info.getGongxian());
		writeInteger(info.getHuoyue());
		writeInteger(info.getOnline());
		writeInteger(info.getInGame());
		writeLong(info.getLogoutTime());
		writeInteger(info.getTanheState());
		writeInteger(info.getAgree());
		writeInteger(info.getRefuse());
		writeInteger(info.getSelfState());
		writeInteger(info.getVipLevel());
		writeInteger(info.getGirlFlag());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CLUB_PROMATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_PROMATE";
	}

	public int getRet(){
		return ret;
	}
		
	public void setRet(int ret){
		this.ret = ret;
	}

	public com.gameserver.club.protocol.ClubMemberListUnit getInfo(){
		return info;
	}
		
	public void setInfo(com.gameserver.club.protocol.ClubMemberListUnit info){
		this.info = info;
	}
}