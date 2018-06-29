package com.gameserver.misc.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.misc.handler.MiscHandlerFactory;

/**
 * 新手引导
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGNewUser extends CGMessage{
	
	
	public CGNewUser (){
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
		return MessageType.CG_NEW_USER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_NEW_USER";
	}
	


	@Override
	public void execute() {
		MiscHandlerFactory.getHandler().handleNewUser(this.getSession().getPlayer(), this);
	}
}