package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 水晶魔法老虎机的scatter玩法
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType19Scatter extends CGMessage{
	
	
	public CGSlotType19Scatter (){
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
		return MessageType.CG_SLOT_TYPE19_SCATTER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE19_SCATTER";
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType19Scatter(this.getSession().getPlayer(), this);
	}
}