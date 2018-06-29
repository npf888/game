package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州allin
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTexasAllIn extends CGMessage{
	
	
	public CGTexasAllIn (){
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
		return MessageType.CG_TEXAS_ALL_IN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TEXAS_ALL_IN";
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleTexasAllIn(this.getSession().getPlayer(), this);
	}
}