package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 离开德州房间
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGLeaveTexas extends CGMessage{
	
	
	public CGLeaveTexas (){
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
		return MessageType.CG_LEAVE_TEXAS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_LEAVE_TEXAS";
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleLeaveTexas(this.getSession().getPlayer(), this);
	}
}