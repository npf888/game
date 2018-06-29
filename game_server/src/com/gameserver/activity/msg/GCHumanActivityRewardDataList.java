package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 用户活动奖励列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanActivityRewardDataList extends GCMessage{
	
	/** 活动奖励信息 */
	private com.gameserver.activity.data.HumanSimpleRewardInfoData[] humanSimpleRewardInfoData;

	public GCHumanActivityRewardDataList (){
	}
	
	public GCHumanActivityRewardDataList (
			com.gameserver.activity.data.HumanSimpleRewardInfoData[] humanSimpleRewardInfoData ){
			this.humanSimpleRewardInfoData = humanSimpleRewardInfoData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		humanSimpleRewardInfoData = new com.gameserver.activity.data.HumanSimpleRewardInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.activity.data.HumanSimpleRewardInfoData obj = new com.gameserver.activity.data.HumanSimpleRewardInfoData();
			obj.setActivityId(readLong());
			{
				int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
				obj.setStateListist(subList);
			}
			humanSimpleRewardInfoData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(humanSimpleRewardInfoData.length);
		for(int i=0; i<humanSimpleRewardInfoData.length; i++){
			writeLong(humanSimpleRewardInfoData[i].getActivityId());
			int[] stateListist=humanSimpleRewardInfoData[i].getStateListist();
			writeShort(stateListist.length);
			for(int j=0; j<stateListist.length; j++){
				writeInteger(stateListist[j]);
			}
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_ACTIVITY_REWARD_DATA_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_ACTIVITY_REWARD_DATA_LIST";
	}

	public com.gameserver.activity.data.HumanSimpleRewardInfoData[] getHumanSimpleRewardInfoData(){
		return humanSimpleRewardInfoData;
	}

	public void setHumanSimpleRewardInfoData(com.gameserver.activity.data.HumanSimpleRewardInfoData[] humanSimpleRewardInfoData){
		this.humanSimpleRewardInfoData = humanSimpleRewardInfoData;
	}	
}