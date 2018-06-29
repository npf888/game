package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 玩家退出老虎机玩家广播5
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotRoom5 extends GCMessage{
	
	/** 玩家id */
	private long playerId;

	public GCSlotRoom5 (){
	}
	
	public GCSlotRoom5 (
			long playerId ){
			this.playerId = playerId;
	}

	@Override
	protected boolean readImpl() {
		playerId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_ROOM5;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_ROOM5";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
}