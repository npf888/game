package com.gameserver.task.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 任务
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTaskInfoData extends GCMessage{
	
	/** 任务信息 */
	private com.gameserver.task.data.TaskData[] dailyTaskDataList;

	public GCTaskInfoData (){
	}
	
	public GCTaskInfoData (
			com.gameserver.task.data.TaskData[] dailyTaskDataList ){
			this.dailyTaskDataList = dailyTaskDataList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		dailyTaskDataList = new com.gameserver.task.data.TaskData[count];
		for(int i=0; i<count; i++){
			com.gameserver.task.data.TaskData obj = new com.gameserver.task.data.TaskData();
			obj.setTaskId(readInteger());
			obj.setFinished(readInteger());
			obj.setGetNums(readInteger());
			dailyTaskDataList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(dailyTaskDataList.length);
		for(int i=0; i<dailyTaskDataList.length; i++){
			writeInteger(dailyTaskDataList[i].getTaskId());
			writeInteger(dailyTaskDataList[i].getFinished());
			writeInteger(dailyTaskDataList[i].getGetNums());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TASK_INFO_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TASK_INFO_DATA";
	}

	public com.gameserver.task.data.TaskData[] getDailyTaskDataList(){
		return dailyTaskDataList;
	}

	public void setDailyTaskDataList(com.gameserver.task.data.TaskData[] dailyTaskDataList){
		this.dailyTaskDataList = dailyTaskDataList;
	}	
}