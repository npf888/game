package com.gameserver.bazooachieve.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazooachieve.handler.BazooachieveHandlerFactory;

/**
 * 获取 成就
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooAchieve extends CGMessage{
	
	
	public CGBazooAchieve (){
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
		return MessageType.CG_BAZOO_ACHIEVE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_ACHIEVE";
	}
	


	@Override
	public void execute() {
		BazooachieveHandlerFactory.getHandler().handleBazooAchieve(this.getSession().getPlayer(), this);
	}
}