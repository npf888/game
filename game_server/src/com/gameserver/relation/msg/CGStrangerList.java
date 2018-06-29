package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.relation.handler.RelationHandlerFactory;

/**
 * 请求陌生人列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGStrangerList extends CGMessage{
	
	
	public CGStrangerList (){
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
		return MessageType.CG_STRANGER_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_STRANGER_LIST";
	}
	


	@Override
	public void execute() {
		RelationHandlerFactory.getHandler().handleStrangerList(this.getSession().getPlayer(), this);
	}
}