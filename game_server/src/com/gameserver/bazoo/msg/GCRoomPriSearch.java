package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 搜索私人房间（根据房间号,返回）
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRoomPriSearch extends GCMessage{
	
	/** 房间号 */
	private String roomNumber;

	public GCRoomPriSearch (){
	}
	
	public GCRoomPriSearch (
			String roomNumber ){
			this.roomNumber = roomNumber;
	}

	@Override
	protected boolean readImpl() {
		roomNumber = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(roomNumber);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ROOM_PRI_SEARCH;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROOM_PRI_SEARCH";
	}

	public String getRoomNumber(){
		return roomNumber;
	}
		
	public void setRoomNumber(String roomNumber){
		this.roomNumber = roomNumber;
	}
}