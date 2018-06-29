package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 大厅通知前端, 状态 是否有没做的事（会显示小红点）
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBazooHallStatus extends GCMessage{
	
	/** 0:没有领取,1:已经领取 */
	private int signInStatus;
	/** 任务 已完成未领取的数量 */
	private int taskNumber;

	public GCBazooHallStatus (){
	}
	
	public GCBazooHallStatus (
			int signInStatus,
			int taskNumber ){
			this.signInStatus = signInStatus;
			this.taskNumber = taskNumber;
	}

	@Override
	protected boolean readImpl() {
		signInStatus = readInteger();
		taskNumber = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(signInStatus);
		writeInteger(taskNumber);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BAZOO_HALL_STATUS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAZOO_HALL_STATUS";
	}

	public int getSignInStatus(){
		return signInStatus;
	}
		
	public void setSignInStatus(int signInStatus){
		this.signInStatus = signInStatus;
	}

	public int getTaskNumber(){
		return taskNumber;
	}
		
	public void setTaskNumber(int taskNumber){
		this.taskNumber = taskNumber;
	}
}