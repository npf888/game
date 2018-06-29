package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 万圣节老虎机   wild的玩法 
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType38Wild extends GCMessage{
	
	/** 倍数 */
	private int multiple;

	public GCSlotType38Wild (){
	}
	
	public GCSlotType38Wild (
			int multiple ){
			this.multiple = multiple;
	}

	@Override
	protected boolean readImpl() {
		multiple = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(multiple);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE38_WILD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE38_WILD";
	}

	public int getMultiple(){
		return multiple;
	}
		
	public void setMultiple(int multiple){
		this.multiple = multiple;
	}
}