package com.gameserver.newbie.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.newbie.handler.NewbieHandlerFactory;

/**
 * 新手存盘点
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotNewbie extends CGMessage{
	
	
	public CGSlotNewbie (){
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
		return MessageType.CG_SLOT_NEWBIE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_NEWBIE";
	}
	


	@Override
	public void execute() {
		NewbieHandlerFactory.getHandler().handleSlotNewbie(this.getSession().getPlayer(), this);
	}
}