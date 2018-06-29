package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州跟注
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTexasFollow extends CGMessage{
	
	
	public CGTexasFollow (){
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
		return MessageType.CG_TEXAS_FOLLOW;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TEXAS_FOLLOW";
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleTexasFollow(this.getSession().getPlayer(), this);
	}
}