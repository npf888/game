package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 巴西风情老虎机点击卡牌
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType24BounsPrize extends CGMessage{
	
	
	public CGSlotType24BounsPrize (){
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
		return MessageType.CG_SLOT_TYPE24_BOUNS_PRIZE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE24_BOUNS_PRIZE";
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType24BounsPrize(this.getSession().getPlayer(), this);
	}
}