package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州房间类型列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasList extends GCMessage{
	
	/** 房间类型列表 */
	private com.gameserver.texas.data.TexasRoomTypeInfoData[] roomTypeList;

	public GCTexasList (){
	}
	
	public GCTexasList (
			com.gameserver.texas.data.TexasRoomTypeInfoData[] roomTypeList ){
			this.roomTypeList = roomTypeList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		roomTypeList = new com.gameserver.texas.data.TexasRoomTypeInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.texas.data.TexasRoomTypeInfoData obj = new com.gameserver.texas.data.TexasRoomTypeInfoData();
			obj.setTypeId(readInteger());
			obj.setRoomTag(readInteger());
			obj.setOpenUp(readInteger());
			obj.setSmallBlind(readInteger());
			obj.setMinCarry(readInteger());
			obj.setMaxCarry(readInteger());
			obj.setRoomNum(readInteger());
			obj.setTotalNum(readInteger());
			obj.setOpenLv(readInteger());
			obj.setList(readInteger());
			obj.setHandsel(readLong());
			roomTypeList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(roomTypeList.length);
		for(int i=0; i<roomTypeList.length; i++){
			writeInteger(roomTypeList[i].getTypeId());
			writeInteger(roomTypeList[i].getRoomTag());
			writeInteger(roomTypeList[i].getOpenUp());
			writeInteger(roomTypeList[i].getSmallBlind());
			writeInteger(roomTypeList[i].getMinCarry());
			writeInteger(roomTypeList[i].getMaxCarry());
			writeInteger(roomTypeList[i].getRoomNum());
			writeInteger(roomTypeList[i].getTotalNum());
			writeInteger(roomTypeList[i].getOpenLv());
			writeInteger(roomTypeList[i].getList());
			writeLong(roomTypeList[i].getHandsel());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_LIST";
	}

	public com.gameserver.texas.data.TexasRoomTypeInfoData[] getRoomTypeList(){
		return roomTypeList;
	}

	public void setRoomTypeList(com.gameserver.texas.data.TexasRoomTypeInfoData[] roomTypeList){
		this.roomTypeList = roomTypeList;
	}	
}