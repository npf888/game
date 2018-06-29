package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州打赏
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTexasTips extends CGMessage{
	
	
	public CGTexasTips (){
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
		return MessageType.CG_TEXAS_TIPS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TEXAS_TIPS";
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleTexasTips(this.getSession().getPlayer(), this);
	}
}