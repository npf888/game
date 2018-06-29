package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 维密老虎机scatter返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType12 extends GCMessage{
	
	/** 维密老虎机要选择的ID */
	private int[] slotsScatterData;

	public GCSlotType12 (){
	}
	
	public GCSlotType12 (
			int[] slotsScatterData ){
			this.slotsScatterData = slotsScatterData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		slotsScatterData = new int[count];
		for(int i=0; i<count; i++){
			slotsScatterData[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(slotsScatterData.length);
		for(int i=0; i<slotsScatterData.length; i++){
			writeInteger(slotsScatterData[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE12;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE12";
	}

	public int[] getSlotsScatterData(){
		return slotsScatterData;
	}

	public void setSlotsScatterData(int[] slotsScatterData){
		this.slotsScatterData = slotsScatterData;
	}	
}