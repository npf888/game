package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 宙斯老虎机抽奖
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType13BounsPrize extends CGMessage{
	
	
	public CGSlotType13BounsPrize (){
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
		return MessageType.CG_SLOT_TYPE13_BOUNS_PRIZE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE13_BOUNS_PRIZE";
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType13BounsPrize(this.getSession().getPlayer(), this);
	}
}