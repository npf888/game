package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 房间状态 变化
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRoomState extends GCMessage{
	
	/** 房间状态 */
	private int roomState;

	public GCRoomState (){
	}
	
	public GCRoomState (
			int roomState ){
			this.roomState = roomState;
	}

	@Override
	protected boolean readImpl() {
		roomState = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(roomState);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ROOM_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROOM_STATE";
	}

	public int getRoomState(){
		return roomState;
	}
		
	public void setRoomState(int roomState){
		this.roomState = roomState;
	}
}