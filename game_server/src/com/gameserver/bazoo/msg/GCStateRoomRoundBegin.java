package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 根据状态返回消息 3
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStateRoomRoundBegin extends GCMessage{
	
	/** 收到统一摇色子的消息 之后的状态 */
	private int status;

	public GCStateRoomRoundBegin (){
	}
	
	public GCStateRoomRoundBegin (
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
		return MessageType.GC_STATE_ROOM_ROUND_BEGIN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STATE_ROOM_ROUND_BEGIN";
	}

	public int getStatus(){
		return status;
	}
		
	public void setStatus(int status){
		this.status = status;
	}
}