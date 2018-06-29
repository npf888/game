package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 退出老虎机，删除用户所在当前老虎机的ID
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotOut extends CGMessage{
	
	/** 老虎机Id */
	private int slotId;
	
	public CGSlotOut (){
	}
	
	public CGSlotOut (
			int slotId ){
			this.slotId = slotId;
	}
	
	@Override
	protected boolean readImpl() {
		slotId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(slotId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLOT_OUT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_OUT";
	}

	public int getSlotId(){
		return slotId;
	}
		
	public void setSlotId(int slotId){
		this.slotId = slotId;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotOut(this.getSession().getPlayer(), this);
	}
}