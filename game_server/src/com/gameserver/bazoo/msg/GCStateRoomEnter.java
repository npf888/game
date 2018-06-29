package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 根据状态返回消息 0
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStateRoomEnter extends GCMessage{
	
	/** 新一轮 的状态  */
	private int status;

	public GCStateRoomEnter (){
	}
	
	public GCStateRoomEnter (
			int status ){
			this.status = status;
	}

	@Override
	protected boolean readImpl() {
		status = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(status);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STATE_ROOM_ENTER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STATE_ROOM_ENTER";
	}

	public int getStatus(){
		return status;
	}
		
	public void setStatus(int status){
		this.status = status;
	}
}