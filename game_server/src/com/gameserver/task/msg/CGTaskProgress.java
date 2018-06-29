package com.gameserver.task.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.task.handler.TaskHandlerFactory;

/**
 * 领取任务进度奖励
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTaskProgress extends CGMessage{
	
	/** 宝箱ID */
	private int boxId;
	
	public CGTaskProgress (){
	}
	
	public CGTaskProgress (
			int boxId ){
			this.boxId = boxId;
	}
	
	@Override
	protected boolean readImpl() {
		boxId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(boxId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_TASK_PROGRESS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TASK_PROGRESS";
	}

	public int getBoxId(){
		return boxId;
	}
		
	public void setBoxId(int boxId){
		this.boxId = boxId;
	}
	


	@Override
	public void execute() {
		TaskHandlerFactory.getHandler().handleTaskProgress(this.getSession().getPlayer(), this);
	}
}