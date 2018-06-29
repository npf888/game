package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 老虎机错误
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotError extends GCMessage{
	

	public GCSlotError (){
	}
	

	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_ERROR;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_ERROR";
	}
}