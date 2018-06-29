package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州看牌
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTexasLook extends CGMessage{
	
	
	public CGTexasLook (){
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
		return MessageType.CG_TEXAS_LOOK;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TEXAS_LOOK";
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleTexasLook(this.getSession().getPlayer(), this);
	}
}