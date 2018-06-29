package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 抢开 
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRobOpen extends CGMessage{
	
	
	public CGRobOpen (){
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
		return MessageType.CG_ROB_OPEN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ROB_OPEN";
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleRobOpen(this.getSession().getPlayer(), this);
	}
}