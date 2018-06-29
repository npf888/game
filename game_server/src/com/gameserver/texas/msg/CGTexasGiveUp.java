package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州allin
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTexasGiveUp extends CGMessage{
	
	
	public CGTexasGiveUp (){
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
		return MessageType.CG_TEXAS_GIVE_UP;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TEXAS_GIVE_UP";
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleTexasGiveUp(this.getSession().getPlayer(), this);
	}
}