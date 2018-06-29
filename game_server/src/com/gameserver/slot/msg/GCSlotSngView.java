package com.gameserver.slot.msg;

import com.gameserver.common.msg.GCMessage;
import com.gameserver.common.msg.MessageType;

/**
 * 推送大厅显示老虎机竞赛显示 此消息作废
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotSngView extends GCMessage{
	
	/** 大厅竞赛结果展示 */
	private com.gameserver.slot.data.SlotSngView[] slotSngView;

	public GCSlotSngView (){
	}
	
	public GCSlotSngView (
			com.gameserver.slot.data.SlotSngView[] slotSngView ){
			this.slotSngView = slotSngView;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		slotSngView = new com.gameserver.slot.data.SlotSngView[count];
		for(int i=0; i<count; i++){
			com.gameserver.slot.data.SlotSngView obj = new com.gameserver.slot.data.SlotSngView();
			obj.setSlotType(readInteger());
			obj.setImg(readString());
			obj.setBonus(readLong());
			slotSngView[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(slotSngView.length);
		for(int i=0; i<slotSngView.length; i++){
			writeInteger(slotSngView[i].getSlotType());
			writeString(slotSngView[i].getImg());
			writeLong(slotSngView[i].getBonus());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_SNG_VIEW;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_SNG_VIEW";
	}

	public com.gameserver.slot.data.SlotSngView[] getSlotSngView(){
		return slotSngView;
	}

	public void setSlotSngView(com.gameserver.slot.data.SlotSngView[] slotSngView){
		this.slotSngView = slotSngView;
	}	
}