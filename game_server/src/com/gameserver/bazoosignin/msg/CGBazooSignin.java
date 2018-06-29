package com.gameserver.bazoosignin.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoosignin.handler.BazoosigninHandlerFactory;

/**
 * 签到
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooSignin extends CGMessage{
	
	
	public CGBazooSignin (){
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
		return MessageType.CG_BAZOO_SIGNIN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_SIGNIN";
	}
	


	@Override
	public void execute() {
		BazoosigninHandlerFactory.getHandler().handleBazooSignin(this.getSession().getPlayer(), this);
	}
}