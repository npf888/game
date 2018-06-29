package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 老虎机列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotList extends GCMessage{
	
	/** 老虎机列表 */
	private com.gameserver.slot.data.SlotList[] slotList;

	public GCSlotList (){
	}
	
	public GCSlotList (
			com.gameserver.slot.data.SlotList[] slotList ){
			this.slotList = slotList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		slotList = new com.gameserver.slot.data.SlotList[count];
		for(int i=0; i<count; i++){
			com.gameserver.slot.data.SlotList obj = new com.gameserver.slot.data.SlotList();
			obj.setSlotId(readInteger());
			obj.setHandsel(readLong());
			slotList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(slotList.length);
		for(int i=0; i<slotList.length; i++){
			writeInteger(slotList[i].getSlotId());
			writeLong(slotList[i].getHandsel());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_LIST";
	}

	public com.gameserver.slot.data.SlotList[] getSlotList(){
		return slotList;
	}

	public void setSlotList(com.gameserver.slot.data.SlotList[] slotList){
		this.slotList = slotList;
	}	
}