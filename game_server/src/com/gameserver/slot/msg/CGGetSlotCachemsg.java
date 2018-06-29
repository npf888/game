package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 请求老虎机缓存消息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGetSlotCachemsg extends CGMessage{
	
	
	public CGGetSlotCachemsg (){
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
		return MessageType.CG_GET_SLOT_CACHEMSG;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_SLOT_CACHEMSG";
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleGetSlotCachemsg(this.getSession().getPlayer(), this);
	}
}