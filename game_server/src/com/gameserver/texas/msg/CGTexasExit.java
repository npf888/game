package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州玩家退出
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTexasExit extends CGMessage{
	
	
	public CGTexasExit (){
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
		return MessageType.CG_TEXAS_EXIT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TEXAS_EXIT";
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleTexasExit(this.getSession().getPlayer(), this);
	}
}