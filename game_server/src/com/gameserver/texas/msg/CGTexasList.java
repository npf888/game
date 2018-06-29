package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州房间类型列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTexasList extends CGMessage{
	
	
	public CGTexasList (){
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
		return MessageType.CG_TEXAS_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TEXAS_LIST";
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleTexasList(this.getSession().getPlayer(), this);
	}
}