package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 女巫魔法老虎机bouns小游戏
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType26Bouns extends GCMessage{
	
	/** 中奖额度 */
	private int totalGold;

	public GCSlotType26Bouns (){
	}
	
	public GCSlotType26Bouns (
			int totalGold ){
			this.totalGold = totalGold;
	}

	@Override
	protected boolean readImpl() {
		totalGold = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(totalGold);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE26_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE26_BOUNS";
	}

	public int getTotalGold(){
		return totalGold;
	}
		
	public void setTotalGold(int totalGold){
		this.totalGold = totalGold;
	}
}