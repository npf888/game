package com.gameserver.bazootask.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazootask.handler.BazootaskHandlerFactory;

/**
 * 获取 所有的成就任务
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooAchieveTask extends CGMessage{
	
	
	public CGBazooAchieveTask (){
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
		return MessageType.CG_BAZOO_ACHIEVE_TASK;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_ACHIEVE_TASK";
	}
	


	@Override
	public void execute() {
		BazootaskHandlerFactory.getHandler().handleBazooAchieveTask(this.getSession().getPlayer(), this);
	}
}