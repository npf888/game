package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 根据状态返回消息 5
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStateRoomCallDice extends GCMessage{
	
	/** 已经叫号（和上边 的 5 循环切换 状态） */
	private int status;

	public GCStateRoomCallDice (){
	}
	
	public GCStateRoomCallDice (
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
		return MessageType.GC_STATE_ROOM_CALL_DICE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STATE_ROOM_CALL_DICE";
	}

	public int getStatus(){
		return status;
	}
		
	public void setStatus(int status){
		this.status = status;
	}
}