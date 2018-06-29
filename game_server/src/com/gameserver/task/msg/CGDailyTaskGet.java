package com.gameserver.task.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.task.handler.TaskHandlerFactory;

/**
 * 领取奖励
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGDailyTaskGet extends CGMessage{
	
	/** 任务id */
	private int tId;
	
	public CGDailyTaskGet (){
	}
	
	public CGDailyTaskGet (
			int tId ){
			this.tId = tId;
	}
	
	@Override
	protected boolean readImpl() {
		tId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(tId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_DAILY_TASK_GET;
	}
	
	@Override
	public String getTypeName() {
		return "CG_DAILY_TASK_GET";
	}

	public int getTId(){
		return tId;
	}
		
	public void setTId(int tId){
		this.tId = tId;
	}
	


	@Override
	public void execute() {
		TaskHandlerFactory.getHandler().handleDailyTaskGet(this.getSession().getPlayer(), this);
	}
}