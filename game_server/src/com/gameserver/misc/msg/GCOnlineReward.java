package com.gameserver.misc.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 在线奖励
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCOnlineReward extends GCMessage{
	
	/** 内容 */
	private com.gameserver.common.data.RandRewardData reward;

	public GCOnlineReward (){
	}
	
	public GCOnlineReward (
			com.gameserver.common.data.RandRewardData reward ){
			this.reward = reward;
	}

	@Override
	protected boolean readImpl() {
		reward = new com.gameserver.common.data.RandRewardData();
					reward.setRewardId(readInteger());
							reward.setRewardCount(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(reward.getRewardId());
		writeInteger(reward.getRewardCount());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ONLINE_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ONLINE_REWARD";
	}

	public com.gameserver.common.data.RandRewardData getReward(){
		return reward;
	}
		
	public void setReward(com.gameserver.common.data.RandRewardData reward){
		this.reward = reward;
	}
}