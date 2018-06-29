package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 巴西风情老虎机  喝酒  游戏
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType24BounsBar extends GCMessage{
	
	/** 是否是金币  0：不是，1：是 */
	private int[] isReward;
	/** 获得的金币数量集合 */
	private long[] rewards;
	/** 获得的总钱数 */
	private long totalGold;

	public GCSlotType24BounsBar (){
	}
	
	public GCSlotType24BounsBar (
			int[] isReward,
			long[] rewards,
			long totalGold ){
			this.isReward = isReward;
			this.rewards = rewards;
			this.totalGold = totalGold;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		isReward = new int[count];
		for(int i=0; i<count; i++){
			isReward[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		rewards = new long[count];
		for(int i=0; i<count; i++){
			rewards[i] = readLong();
		}
		totalGold = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(isReward.length);
		for(int i=0; i<isReward.length; i++){
			writeInteger(isReward[i]);
		}
		writeShort(rewards.length);
		for(int i=0; i<rewards.length; i++){
			writeLong(rewards[i]);
		}
		writeLong(totalGold);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE24_BOUNS_BAR;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE24_BOUNS_BAR";
	}

	public int[] getIsReward(){
		return isReward;
	}

	public void setIsReward(int[] isReward){
		this.isReward = isReward;
	}	

	public long[] getRewards(){
		return rewards;
	}

	public void setRewards(long[] rewards){
		this.rewards = rewards;
	}	

	public long getTotalGold(){
		return totalGold;
	}
		
	public void setTotalGold(long totalGold){
		this.totalGold = totalGold;
	}
}