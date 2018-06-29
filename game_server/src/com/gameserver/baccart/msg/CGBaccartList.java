package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.baccart.handler.BaccartHandlerFactory;

/**
 * 百家乐列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBaccartList extends CGMessage{
	
	
	public CGBaccartList (){
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
		return MessageType.CG_BACCART_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BACCART_LIST";
	}
	


	@Override
	public void execute() {
		BaccartHandlerFactory.getHandler().handleBaccartList(this.getSession().getPlayer(), this);
	}
}