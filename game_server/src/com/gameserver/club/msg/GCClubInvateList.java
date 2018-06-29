package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 获取俱乐留邀请
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubInvateList extends GCMessage{
	
	/** 邀请列表  */
	private com.gameserver.club.protocol.ClubInvateUnit[] list;

	public GCClubInvateList (){
	}
	
	public GCClubInvateList (
			com.gameserver.club.protocol.ClubInvateUnit[] list ){
			this.list = list;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		list = new com.gameserver.club.protocol.ClubInvateUnit[count];
		for(int i=0; i<count; i++){
			com.gameserver.club.protocol.ClubInvateUnit obj = new com.gameserver.club.protocol.ClubInvateUnit();
			obj.setClubId(readString());
			obj.setIco(readInteger());
			obj.setName(readString());
			obj.setType(readInteger());
			obj.setLevel(readInteger());
			obj.setLimit(readInteger());
			obj.setNum(readInteger());
			obj.setMaxNum(readInteger());
			obj.setHuoyue(readInteger());
			obj.setCreateTime(readLong());
			list[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(list.length);
		for(int i=0; i<list.length; i++){
			writeString(list[i].getClubId());
			writeInteger(list[i].getIco());
			writeString(list[i].getName());
			writeInteger(list[i].getType());
			writeInteger(list[i].getLevel());
			writeInteger(list[i].getLimit());
			writeInteger(list[i].getNum());
			writeInteger(list[i].getMaxNum());
			writeInteger(list[i].getHuoyue());
			writeLong(list[i].getCreateTime());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CLUB_INVATE_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_INVATE_LIST";
	}

	public com.gameserver.club.protocol.ClubInvateUnit[] getList(){
		return list;
	}

	public void setList(com.gameserver.club.protocol.ClubInvateUnit[] list){
		this.list = list;
	}	
}