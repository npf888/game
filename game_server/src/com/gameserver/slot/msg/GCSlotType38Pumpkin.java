package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 万圣节老虎机   南瓜头给奖励 
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType38Pumpkin extends GCMessage{
	
	/** 总共中了多少钱 */
	private long totalGold;

	public GCSlotType38Pumpkin (){
	}
	
	public GCSlotType38Pumpkin (
			long totalGold ){
			this.totalGold = totalGold;
	}

	@Override
	protected boolean readImpl() {
		totalGold = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(totalGold);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE38_PUMPKIN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE38_PUMPKIN";
	}

	public long getTotalGold(){
		return totalGold;
	}
		
	public void setTotalGold(long totalGold){
		this.totalGold = totalGold;
	}
}