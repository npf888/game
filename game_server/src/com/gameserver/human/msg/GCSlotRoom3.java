package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 老虎机玩家广播3
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotRoom3 extends GCMessage{
	
	/** 玩家id */
	private long playerId;
	/** 玩家筹码 */
	private long gold;

	public GCSlotRoom3 (){
	}
	
	public GCSlotRoom3 (
			long playerId,
			long gold ){
			this.playerId = playerId;
			this.gold = gold;
	}

	@Override
	protected boolean readImpl() {
		playerId = readLong();
		gold = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerId);
		writeLong(gold);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_ROOM3;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_ROOM3";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}

	public long getGold(){
		return gold;
	}
		
	public void setGold(long gold){
		this.gold = gold;
	}
}