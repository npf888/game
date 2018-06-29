package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 俱乐部搜索结果
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubSearchResult extends GCMessage{
	
	/** 俱乐部搜索结果 */
	private com.gameserver.club.protocol.ClubListUnit[] list;

	public GCClubSearchResult (){
	}
	
	public GCClubSearchResult (
			com.gameserver.club.protocol.ClubListUnit[] list ){
			this.list = list;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		list = new com.gameserver.club.protocol.ClubListUnit[count];
		for(int i=0; i<count; i++){
			com.gameserver.club.protocol.ClubListUnit obj = new com.gameserver.club.protocol.ClubListUnit();
			obj.setClubId(readString());
			obj.setIco(readInteger());
			obj.setName(readString());
			obj.setType(readInteger());
			obj.setLevel(readInteger());
			obj.setLimit(readInteger());
			obj.setNum(readInteger());
			obj.setMaxNum(readInteger());
			obj.setHuoyue(readInteger());
			obj.setGongxian(readInteger());
			obj.setApplied(readInteger());
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
			writeInteger(list[i].getGongxian());
			writeInteger(list[i].getApplied());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CLUB_SEARCH_RESULT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_SEARCH_RESULT";
	}

	public com.gameserver.club.protocol.ClubListUnit[] getList(){
		return list;
	}

	public void setList(com.gameserver.club.protocol.ClubListUnit[] list){
		this.list = list;
	}	
}