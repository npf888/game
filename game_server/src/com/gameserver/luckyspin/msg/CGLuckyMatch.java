package com.gameserver.luckyspin.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.luckyspin.handler.LuckyspinHandlerFactory;

/**
 * 匹配
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGLuckyMatch extends CGMessage{
	
	
	public CGLuckyMatch (){
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
		return MessageType.CG_LUCKY_MATCH;
	}
	
	@Override
	public String getTypeName() {
		return "CG_LUCKY_MATCH";
	}
	


	@Override
	public void execute() {
		LuckyspinHandlerFactory.getHandler().handleLuckyMatch(this.getSession().getPlayer(), this);
	}
}