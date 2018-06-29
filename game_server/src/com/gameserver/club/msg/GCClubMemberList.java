package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 获取俱乐部成员列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubMemberList extends GCMessage{
	
	/** 如果操作成功 成员变化后的信息  */
	private com.gameserver.club.protocol.ClubMemberListUnit[] list;

	public GCClubMemberList (){
	}
	
	public GCClubMemberList (
			com.gameserver.club.protocol.ClubMemberListUnit[] list ){
			this.list = list;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		list = new com.gameserver.club.protocol.ClubMemberListUnit[count];
		for(int i=0; i<count; i++){
			com.gameserver.club.protocol.ClubMemberListUnit obj = new com.gameserver.club.protocol.ClubMemberListUnit();
			obj.setPlayerId(readLong());
			obj.setName(readString());
			obj.setIco(readString());
			obj.setLevel(readInteger());
			obj.setCountry(readString());
			obj.setZhiwu(readInteger());
			obj.setGongxian(readInteger());
			obj.setHuoyue(readInteger());
			obj.setOnline(readInteger());
			obj.setInGame(readInteger());
			obj.setLogoutTime(readLong());
			obj.setTanheState(readInteger());
			obj.setAgree(readInteger());
			obj.setRefuse(readInteger());
			obj.setSelfState(readInteger());
			obj.setVipLevel(readInteger());
			obj.setGirlFlag(readInteger());
			list[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(list.length);
		for(int i=0; i<list.length; i++){
			writeLong(list[i].getPlayerId());
			writeString(list[i].getName());
			writeString(list[i].getIco());
			writeInteger(list[i].getLevel());
			writeString(list[i].getCountry());
			writeInteger(list[i].getZhiwu());
			writeInteger(list[i].getGongxian());
			writeInteger(list[i].getHuoyue());
			writeInteger(list[i].getOnline());
			writeInteger(list[i].getInGame());
			writeLong(list[i].getLogoutTime());
			writeInteger(list[i].getTanheState());
			writeInteger(list[i].getAgree());
			writeInteger(list[i].getRefuse());
			writeInteger(list[i].getSelfState());
			writeInteger(list[i].getVipLevel());
			writeInteger(list[i].getGirlFlag());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CLUB_MEMBER_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_MEMBER_LIST";
	}

	public com.gameserver.club.protocol.ClubMemberListUnit[] getList(){
		return list;
	}

	public void setList(com.gameserver.club.protocol.ClubMemberListUnit[] list){
		this.list = list;
	}	
}