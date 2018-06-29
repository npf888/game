package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.baccart.handler.BaccartHandlerFactory;

/**
 * 百家乐快速开始
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBaccaratQuickStart extends CGMessage{
	
	
	public CGBaccaratQuickStart (){
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
		return MessageType.CG_BACCARAT_QUICK_START;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BACCARAT_QUICK_START";
	}
	


	@Override
	public void execute() {
		BaccartHandlerFactory.getHandler().handleBaccaratQuickStart(this.getSession().getPlayer(), this);
	}
}