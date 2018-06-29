package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 大厅通知前端, 状态 是否有没做的事，主动请求
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooHallStatus extends CGMessage{
	
	
	public CGBazooHallStatus (){
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
		return MessageType.CG_BAZOO_HALL_STATUS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_HALL_STATUS";
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleBazooHallStatus(this.getSession().getPlayer(), this);
	}
}