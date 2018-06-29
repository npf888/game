package com.gameserver.bazooachieve.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazooachieve.handler.BazooachieveHandlerFactory;

/**
 * 获取 成就(每个类型的第一个)
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooAchieveFirst extends CGMessage{
	
	
	public CGBazooAchieveFirst (){
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
		return MessageType.CG_BAZOO_ACHIEVE_FIRST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_ACHIEVE_FIRST";
	}
	


	@Override
	public void execute() {
		BazooachieveHandlerFactory.getHandler().handleBazooAchieveFirst(this.getSession().getPlayer(), this);
	}
}