package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 匹配到玩家
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRoomMatched extends GCMessage{
	
	/** 等待开始时间（秒） */
	private int waitTime;

	public GCRoomMatched (){
	}
	
	public GCRoomMatched (
			int waitTime ){
			this.waitTime = waitTime;
	}

	@Override
	protected boolean readImpl() {
		waitTime = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(waitTime);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ROOM_MATCHED;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROOM_MATCHED";
	}

	public int getWaitTime(){
		return waitTime;
	}
		
	public void setWaitTime(int waitTime){
		this.waitTime = waitTime;
	}
}