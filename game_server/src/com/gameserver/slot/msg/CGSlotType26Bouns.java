package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 女巫魔法老虎机bouns小游戏
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType26Bouns extends CGMessage{
	
	
	public CGSlotType26Bouns (){
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
		return MessageType.CG_SLOT_TYPE26_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE26_BOUNS";
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType26Bouns(this.getSession().getPlayer(), this);
	}
}