package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 维密老虎机自由转动结束后发送这个消息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType12Free extends CGMessage{
	
	
	public CGSlotType12Free (){
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
		return MessageType.CG_SLOT_TYPE12_FREE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE12_FREE";
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType12Free(this.getSession().getPlayer(), this);
	}
}