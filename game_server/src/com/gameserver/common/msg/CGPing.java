package com.gameserver.common.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.common.handler.CommonHandlerFactory;

/**
 * 客户端用于时间同步的消息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPing extends CGMessage{
	
	
	public CGPing (){
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
		return MessageType.CG_PING;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PING";
	}
	


	@Override
	public void execute() {
		CommonHandlerFactory.getHandler().handlePing(this.getSession().getPlayer(), this);
	}
}