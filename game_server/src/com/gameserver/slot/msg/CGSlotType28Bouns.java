package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 海底世界老虎机bouns小游戏
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType28Bouns extends CGMessage{
	
	
	public CGSlotType28Bouns (){
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
		return MessageType.CG_SLOT_TYPE28_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE28_BOUNS";
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType28Bouns(this.getSession().getPlayer(), this);
	}
}