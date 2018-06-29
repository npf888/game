package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 马来网红scatter倍数
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType10Scatter extends GCMessage{
	
	/** 马来网红scatter倍数 */
	private int pos;

	public GCSlotType10Scatter (){
	}
	
	public GCSlotType10Scatter (
			int pos ){
			this.pos = pos;
	}

	@Override
	protected boolean readImpl() {
		pos = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pos);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE10_SCATTER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE10_SCATTER";
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}
}