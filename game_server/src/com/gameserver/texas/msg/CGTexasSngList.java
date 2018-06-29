package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州sng列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTexasSngList extends CGMessage{
	
	
	public CGTexasSngList (){
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
		return MessageType.CG_TEXAS_SNG_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TEXAS_SNG_LIST";
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleTexasSngList(this.getSession().getPlayer(), this);
	}
}