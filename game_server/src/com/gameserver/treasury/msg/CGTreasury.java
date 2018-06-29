package com.gameserver.treasury.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.treasury.handler.TreasuryHandlerFactory;

/**
 * 返回当前存钱罐的 对象
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTreasury extends CGMessage{
	
	
	public CGTreasury (){
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
		return MessageType.CG_TREASURY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TREASURY";
	}
	


	@Override
	public void execute() {
		TreasuryHandlerFactory.getHandler().handleTreasury(this.getSession().getPlayer(), this);
	}
}