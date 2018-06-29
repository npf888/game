package com.gameserver.bazootask.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazootask.handler.BazootaskHandlerFactory;

/**
 * 获取 金币
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooGetReward extends CGMessage{
	
	/** 相应的ID */
	private int taskId;
	
	public CGBazooGetReward (){
	}
	
	public CGBazooGetReward (
			int taskId ){
			this.taskId = taskId;
	}
	
	@Override
	protected boolean readImpl() {
		taskId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(taskId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BAZOO_GET_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_GET_REWARD";
	}

	public int getTaskId(){
		return taskId;
	}
		
	public void setTaskId(int taskId){
		this.taskId = taskId;
	}
	


	@Override
	public void execute() {
		BazootaskHandlerFactory.getHandler().handleBazooGetReward(this.getSession().getPlayer(), this);
	}
}