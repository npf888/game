package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 万圣节老虎机   用户玩 bonus小游戏 
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType38Bonus extends GCMessage{
	
	/** 总共中了多少钱 */
	private long totalGold;

	public GCSlotType38Bonus (){
	}
	
	public GCSlotType38Bonus (
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
		return MessageType.GC_SLOT_TYPE38_BONUS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE38_BONUS";
	}

	public long getTotalGold(){
		return totalGold;
	}
		
	public void setTotalGold(long totalGold){
		this.totalGold = totalGold;
	}
}