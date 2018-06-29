package com.gameserver.task.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 领取奖励
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCDailyTaskGet extends GCMessage{
	
	/** 任务id */
	private int tId;
	/** 道具奖励 */
	private com.gameserver.common.data.RandRewardData[] randRewardList;

	public GCDailyTaskGet (){
	}
	
	public GCDailyTaskGet (
			int tId,
			com.gameserver.common.data.RandRewardData[] randRewardList ){
			this.tId = tId;
			this.randRewardList = randRewardList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		tId = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		randRewardList = new com.gameserver.common.data.RandRewardData[count];
		for(int i=0; i<count; i++){
			com.gameserver.common.data.RandRewardData obj = new com.gameserver.common.data.RandRewardData();
			obj.setRewardId(readInteger());
			obj.setRewardCount(readInteger());
			randRewardList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(tId);
		writeShort(randRewardList.length);
		for(int i=0; i<randRewardList.length; i++){
			writeInteger(randRewardList[i].getRewardId());
			writeInteger(randRewardList[i].getRewardCount());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_DAILY_TASK_GET;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DAILY_TASK_GET";
	}

	public int getTId(){
		return tId;
	}
		
	public void setTId(int tId){
		this.tId = tId;
	}

	public com.gameserver.common.data.RandRewardData[] getRandRewardList(){
		return randRewardList;
	}

	public void setRandRewardList(com.gameserver.common.data.RandRewardData[] randRewardList){
		this.randRewardList = randRewardList;
	}	
}