package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.activity.handler.ActivityHandlerFactory;

/**
 * 用户领取活动奖励
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGetActivityReward extends CGMessage{
	
	/** 活动id */
	private long activityId;
	/** 具体小条件Id数组的索引下标 */
	private int indexId;
	
	public CGGetActivityReward (){
	}
	
	public CGGetActivityReward (
			long activityId,
			int indexId ){
			this.activityId = activityId;
			this.indexId = indexId;
	}
	
	@Override
	protected boolean readImpl() {
		activityId = readLong();
		indexId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(activityId);
		writeInteger(indexId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_ACTIVITY_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_ACTIVITY_REWARD";
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
	


	@Override
	public void execute() {
		ActivityHandlerFactory.getHandler().handleGetActivityReward(this.getSession().getPlayer(), this);
	}
}