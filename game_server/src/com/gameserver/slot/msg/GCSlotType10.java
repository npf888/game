package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 马来网红倍数
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType10 extends GCMessage{
	
	/** 马来网红倍数ID */
	private int winMulId;

	public GCSlotType10 (){
	}
	
	public GCSlotType10 (
			int winMulId ){
			this.winMulId = winMulId;
	}

	@Override
	protected boolean readImpl() {
		winMulId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(winMulId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE10;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE10";
	}

	public int getWinMulId(){
		return winMulId;
	}
		
	public void setWinMulId(int winMulId){
		this.winMulId = winMulId;
	}
}