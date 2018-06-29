package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 维密老虎机玩家选择ID
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType12Choose extends GCMessage{
	
	/** 免费次数 */
	private int freeNum;

	public GCSlotType12Choose (){
	}
	
	public GCSlotType12Choose (
			int freeNum ){
			this.freeNum = freeNum;
	}

	@Override
	protected boolean readImpl() {
		freeNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(freeNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE12_CHOOSE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE12_CHOOSE";
	}

	public int getFreeNum(){
		return freeNum;
	}
		
	public void setFreeNum(int freeNum){
		this.freeNum = freeNum;
	}
}