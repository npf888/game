package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州vip列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTexasVipList extends CGMessage{
	
	
	public CGTexasVipList (){
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
		return MessageType.CG_TEXAS_VIP_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TEXAS_VIP_LIST";
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleTexasVipList(this.getSession().getPlayer(), this);
	}
}