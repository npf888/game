package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 老虎机玩家广播4
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotRoom4 extends GCMessage{
	
	/** 玩家id */
	private long playerId;
	/** 大奖类型 0：没有中大奖 1 2 3 4 */
	private int bigAward;

	public GCSlotRoom4 (){
	}
	
	public GCSlotRoom4 (
			long playerId,
			int bigAward ){
			this.playerId = playerId;
			this.bigAward = bigAward;
	}

	@Override
	protected boolean readImpl() {
		playerId = readLong();
		bigAward = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerId);
		writeInteger(bigAward);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_ROOM4;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_ROOM4";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}

	public int getBigAward(){
		return bigAward;
	}
		
	public void setBigAward(int bigAward){
		this.bigAward = bigAward;
	}
}