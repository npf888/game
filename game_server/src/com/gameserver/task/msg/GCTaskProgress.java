package com.gameserver.task.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 领取任务进度奖励返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTaskProgress extends GCMessage{
	
	/** 宝箱ID */
	private int boxId;
	/** 已完成任务个数 */
	private int taskNum;
	/** 已经领取奖励ID逗号隔开 */
	private String taskProcges;

	public GCTaskProgress (){
	}
	
	public GCTaskProgress (
			int boxId,
			int taskNum,
			String taskProcges ){
			this.boxId = boxId;
			this.taskNum = taskNum;
			this.taskProcges = taskProcges;
	}

	@Override
	protected boolean readImpl() {
		boxId = readInteger();
		taskNum = readInteger();
		taskProcges = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(boxId);
		writeInteger(taskNum);
		writeString(taskProcges);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TASK_PROGRESS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TASK_PROGRESS";
	}

	public int getBoxId(){
		return boxId;
	}
		
	public void setBoxId(int boxId){
		this.boxId = boxId;
	}

	public int getTaskNum(){
		return taskNum;
	}
		
	public void setTaskNum(int taskNum){
		this.taskNum = taskNum;
	}

	public String getTaskProcges(){
		return taskProcges;
	}
		
	public void setTaskProcges(String taskProcges){
		this.taskProcges = taskProcges;
	}
}