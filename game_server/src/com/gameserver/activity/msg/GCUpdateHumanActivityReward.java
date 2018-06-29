package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 用户活动奖励列表更新
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCUpdateHumanActivityReward extends GCMessage{
	
	/** 活动奖励信息 */
	private com.gameserver.activity.data.HumanSimpleRewardInfoData humanSimpleRewardInfoData;

	public GCUpdateHumanActivityReward (){
	}
	
	public GCUpdateHumanActivityReward (
			com.gameserver.activity.data.HumanSimpleRewardInfoData humanSimpleRewardInfoData ){
			this.humanSimpleRewardInfoData = humanSimpleRewardInfoData;
	}

	@Override
	protected boolean readImpl() {
		humanSimpleRewardInfoData = new com.gameserver.activity.data.HumanSimpleRewardInfoData();
					humanSimpleRewardInfoData.setActivityId(readLong());
						{
			int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
			humanSimpleRewardInfoData.setStateListist(subList);
		}
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(humanSimpleRewardInfoData.getActivityId());
		int[] stateListist=humanSimpleRewardInfoData.getStateListist();
		writeShort(stateListist.length);
		for(int i=0; i<stateListist.length; i++){	
				writeInteger(stateListist[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_HUMAN_ACTIVITY_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_HUMAN_ACTIVITY_REWARD";
	}

	public com.gameserver.activity.data.HumanSimpleRewardInfoData getHumanSimpleRewardInfoData(){
		return humanSimpleRewardInfoData;
	}
		
	public void setHumanSimpleRewardInfoData(com.gameserver.activity.data.HumanSimpleRewardInfoData humanSimpleRewardInfoData){
		this.humanSimpleRewardInfoData = humanSimpleRewardInfoData;
	}
}