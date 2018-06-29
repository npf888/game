package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州快速开始
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTexasQuickStart extends CGMessage{
	
	
	public CGTexasQuickStart (){
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
		return MessageType.CG_TEXAS_QUICK_START;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TEXAS_QUICK_START";
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleTexasQuickStart(this.getSession().getPlayer(), this);
	}
}