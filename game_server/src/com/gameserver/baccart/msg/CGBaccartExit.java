package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.baccart.handler.BaccartHandlerFactory;

/**
 * 玩家退出
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBaccartExit extends CGMessage{
	
	
	public CGBaccartExit (){
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
		return MessageType.CG_BACCART_EXIT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BACCART_EXIT";
	}
	


	@Override
	public void execute() {
		BaccartHandlerFactory.getHandler().handleBaccartExit(this.getSession().getPlayer(), this);
	}
}