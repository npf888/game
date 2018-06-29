package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.activity.handler.ActivityHandlerFactory;

/**
 * 功能
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGFunction extends CGMessage{
	
	
	public CGFunction (){
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
		return MessageType.CG_FUNCTION;
	}
	
	@Override
	public String getTypeName() {
		return "CG_FUNCTION";
	}
	


	@Override
	public void execute() {
		ActivityHandlerFactory.getHandler().handleFunction(this.getSession().getPlayer(), this);
	}
}