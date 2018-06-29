package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 根据状态返回消息 1
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStateRoomMatching extends GCMessage{
	
	/** 匹配状态  */
	private int status;

	public GCStateRoomMatching (){
	}
	
	public GCStateRoomMatching (
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
		return MessageType.GC_STATE_ROOM_MATCHING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STATE_ROOM_MATCHING";
	}

	public int getStatus(){
		return status;
	}
		
	public void setStatus(int status){
		this.status = status;
	}
}