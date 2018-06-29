package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 用户领取活动奖励
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCGetActivityReward extends GCMessage{
	
	/** 领取结果 0 失败 1 成功 */
	private int result;
	/** 活动id */
	private long activityId;
	/** 具体小条件Id数组的索引下标 */
	private int indexId;

	public GCGetActivityReward (){
	}
	
	public GCGetActivityReward (
			int result,
			long activityId,
			int indexId ){
			this.result = result;
			this.activityId = activityId;
			this.indexId = indexId;
	}

	@Override
	protected boolean readImpl() {
		result = readInteger();
		activityId = readLong();
		indexId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(result);
		writeLong(activityId);
		writeInteger(indexId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_ACTIVITY_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_ACTIVITY_REWARD";
	}

	public int getResult(){
		return result;
	}
		
	public void setResult(int result){
		this.result = result;
	}

	public long getActivityId(){
		return activityId;
	}
		
	public void setActivityId(long activityId){
		this.activityId = activityId;
	}

	public int getIndexId(){
		return indexId;
	}
		
	public void setIndexId(int indexId){
		this.indexId = indexId;
	}
}