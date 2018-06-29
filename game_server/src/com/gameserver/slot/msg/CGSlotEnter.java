package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 进入老虎机
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotEnter extends CGMessage{
	
	/** 老虎机Id */
	private int slotId;
	
	public CGSlotEnter (){
	}
	
	public CGSlotEnter (
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
		return MessageType.CG_SLOT_ENTER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_ENTER";
	}

	public int getSlotId(){
		return slotId;
	}
		
	public void setSlotId(int slotId){
		this.slotId = slotId;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotEnter(this.getSession().getPlayer(), this);
	}
}