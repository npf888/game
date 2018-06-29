package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 老虎老虎机bouns游戏
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType21Bouns extends CGMessage{
	
	
	public CGSlotType21Bouns (){
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
		return MessageType.CG_SLOT_TYPE21_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE21_BOUNS";
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType21Bouns(this.getSession().getPlayer(), this);
	}
}