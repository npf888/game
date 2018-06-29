package com.gameserver.worldboss.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.worldboss.handler.WorldbossHandlerFactory;

/**
 * 自己攻击的血量 
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGReturnBlood extends CGMessage{
	
	
	public CGReturnBlood (){
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
		return MessageType.CG_RETURN_BLOOD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_RETURN_BLOOD";
	}
	


	@Override
	public void execute() {
		WorldbossHandlerFactory.getHandler().handleReturnBlood(this.getSession().getPlayer(), this);
	}
}