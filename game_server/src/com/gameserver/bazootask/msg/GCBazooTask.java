package com.gameserver.bazootask.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 签到返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBazooTask extends GCMessage{
	
	/** 任务列表 */
	private com.gameserver.bazootask.data.BazooTaskInfo[] bazooTaskInfo;

	public GCBazooTask (){
	}
	
	public GCBazooTask (
			com.gameserver.bazootask.data.BazooTaskInfo[] bazooTaskInfo ){
			this.bazooTaskInfo = bazooTaskInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		bazooTaskInfo = new com.gameserver.bazootask.data.BazooTaskInfo[count];
		for(int i=0; i<count; i++){
			com.gameserver.bazootask.data.BazooTaskInfo obj = new com.gameserver.bazootask.data.BazooTaskInfo();
			obj.setTaskId(readInteger());
			obj.setRefreshtype(readInteger());
			obj.setModeType(readInteger());
			obj.setBigType(readInteger());
			obj.setWayOfPlay(readInteger());
			obj.setCondition(readInteger());
			obj.setRewardNum(readInteger());
			obj.setFinishTimes(readInteger());
			obj.setIsFinish(readInteger());
			obj.setNameId(readInteger());
			obj.setDescrip(readInteger());
			obj.setIcon(readString());
			bazooTaskInfo[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(bazooTaskInfo.length);
		for(int i=0; i<bazooTaskInfo.length; i++){
			writeInteger(bazooTaskInfo[i].getTaskId());
			writeInteger(bazooTaskInfo[i].getRefreshtype());
			writeInteger(bazooTaskInfo[i].getModeType());
			writeInteger(bazooTaskInfo[i].getBigType());
			writeInteger(bazooTaskInfo[i].getWayOfPlay());
			writeInteger(bazooTaskInfo[i].getCondition());
			writeInteger(bazooTaskInfo[i].getRewardNum());
			writeInteger(bazooTaskInfo[i].getFinishTimes());
			writeInteger(bazooTaskInfo[i].getIsFinish());
			writeInteger(bazooTaskInfo[i].getNameId());
			writeInteger(bazooTaskInfo[i].getDescrip());
			writeString(bazooTaskInfo[i].getIcon());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BAZOO_TASK;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAZOO_TASK";
	}

	public com.gameserver.bazootask.data.BazooTaskInfo[] getBazooTaskInfo(){
		return bazooTaskInfo;
	}

	public void setBazooTaskInfo(com.gameserver.bazootask.data.BazooTaskInfo[] bazooTaskInfo){
		this.bazooTaskInfo = bazooTaskInfo;
	}	
}