package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 石器时代老虎机有替换元素
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType14 extends GCMessage{
	
	/** 替换后的元素列表 */
	private int[] slotElementListCope;

	public GCSlotType14 (){
	}
	
	public GCSlotType14 (
			int[] slotElementListCope ){
			this.slotElementListCope = slotElementListCope;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		slotElementListCope = new int[count];
		for(int i=0; i<count; i++){
			slotElementListCope[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(slotElementListCope.length);
		for(int i=0; i<slotElementListCope.length; i++){
			writeInteger(slotElementListCope[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE14;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE14";
	}

	public int[] getSlotElementListCope(){
		return slotElementListCope;
	}

	public void setSlotElementListCope(int[] slotElementListCope){
		this.slotElementListCope = slotElementListCope;
	}	
}