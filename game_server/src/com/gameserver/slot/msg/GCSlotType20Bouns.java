package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 泰国大象老虎机Bouns小游戏 
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType20Bouns extends GCMessage{
	
	/** 宝箱倍数客户端需要缩小100倍 */
	private int[] multiples;

	public GCSlotType20Bouns (){
	}
	
	public GCSlotType20Bouns (
			int[] multiples ){
			this.multiples = multiples;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		multiples = new int[count];
		for(int i=0; i<count; i++){
			multiples[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(multiples.length);
		for(int i=0; i<multiples.length; i++){
			writeInteger(multiples[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE20_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE20_BOUNS";
	}

	public int[] getMultiples(){
		return multiples;
	}

	public void setMultiples(int[] multiples){
		this.multiples = multiples;
	}	
}