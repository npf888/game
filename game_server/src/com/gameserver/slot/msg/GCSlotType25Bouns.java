package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 忍者老虎机bouns小游戏
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType25Bouns extends GCMessage{
	
	/** 是否匹配：1 是，0否 */
	private int isMatch;
	/** 中奖额度 */
	private long totalGold;

	public GCSlotType25Bouns (){
	}
	
	public GCSlotType25Bouns (
			int isMatch,
			long totalGold ){
			this.isMatch = isMatch;
			this.totalGold = totalGold;
	}

	@Override
	protected boolean readImpl() {
		isMatch = readInteger();
		totalGold = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(isMatch);
		writeLong(totalGold);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE25_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE25_BOUNS";
	}

	public int getIsMatch(){
		return isMatch;
	}
		
	public void setIsMatch(int isMatch){
		this.isMatch = isMatch;
	}

	public long getTotalGold(){
		return totalGold;
	}
		
	public void setTotalGold(long totalGold){
		this.totalGold = totalGold;
	}
}