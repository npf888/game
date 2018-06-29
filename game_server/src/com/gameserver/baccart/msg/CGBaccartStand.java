package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.baccart.handler.BaccartHandlerFactory;

/**
 * 玩家站起
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBaccartStand extends CGMessage{
	
	
	public CGBaccartStand (){
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
		return MessageType.CG_BACCART_STAND;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BACCART_STAND";
	}
	


	@Override
	public void execute() {
		BaccartHandlerFactory.getHandler().handleBaccartStand(this.getSession().getPlayer(), this);
	}
}