package com.gameserver.task.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 任务改变
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTaskInfoDataChange extends GCMessage{
	
	/** 任务信息 */
	private com.gameserver.task.data.TaskData dailyTaskData;

	public GCTaskInfoDataChange (){
	}
	
	public GCTaskInfoDataChange (
			com.gameserver.task.data.TaskData dailyTaskData ){
			this.dailyTaskData = dailyTaskData;
	}

	@Override
	protected boolean readImpl() {
		dailyTaskData = new com.gameserver.task.data.TaskData();
					dailyTaskData.setTaskId(readInteger());
							dailyTaskData.setFinished(readInteger());
							dailyTaskData.setGetNums(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(dailyTaskData.getTaskId());
		writeInteger(dailyTaskData.getFinished());
		writeInteger(dailyTaskData.getGetNums());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TASK_INFO_DATA_CHANGE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TASK_INFO_DATA_CHANGE";
	}

	public com.gameserver.task.data.TaskData getDailyTaskData(){
		return dailyTaskData;
	}
		
	public void setDailyTaskData(com.gameserver.task.data.TaskData dailyTaskData){
		this.dailyTaskData = dailyTaskData;
	}
}