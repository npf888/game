package com.gameserver.bazootask.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazootask.handler.BazootaskHandlerFactory;

/**
 * 获取 任务
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooTask extends CGMessage{
	
	
	public CGBazooTask (){
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
		return MessageType.CG_BAZOO_TASK;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_TASK";
	}
	


	@Override
	public void execute() {
		BazootaskHandlerFactory.getHandler().handleBazooTask(this.getSession().getPlayer(), this);
	}
}