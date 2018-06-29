package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 竞赛 结束奖励前三名
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotGetReward extends GCMessage{
	
	/** 奖励金币数量 */
	private long rewardNum;
	/** 名次 */
	private int rank;

	public GCSlotGetReward (){
	}
	
	public GCSlotGetReward (
			long rewardNum,
			int rank ){
			this.rewardNum = rewardNum;
			this.rank = rank;
	}

	@Override
	protected boolean readImpl() {
		rewardNum = readLong();
		rank = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(rewardNum);
		writeInteger(rank);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_GET_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_GET_REWARD";
	}

	public long getRewardNum(){
		return rewardNum;
	}
		
	public void setRewardNum(long rewardNum){
		this.rewardNum = rewardNum;
	}

	public int getRank(){
		return rank;
	}
		
	public void setRank(int rank){
		this.rank = rank;
	}
}