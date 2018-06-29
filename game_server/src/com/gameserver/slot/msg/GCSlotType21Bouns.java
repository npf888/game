package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 老虎老虎机bouns信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType21Bouns extends GCMessage{
	
	/** 第几个奖池 */
	private int rewardPool;
	/** 是否匹配：1 是，0 否 */
	private int isMatch;
	/** 总金币 */
	private int totalGold;

	public GCSlotType21Bouns (){
	}
	
	public GCSlotType21Bouns (
			int rewardPool,
			int isMatch,
			int totalGold ){
			this.rewardPool = rewardPool;
			this.isMatch = isMatch;
			this.totalGold = totalGold;
	}

	@Override
	protected boolean readImpl() {
		rewardPool = readInteger();
		isMatch = readInteger();
		totalGold = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rewardPool);
		writeInteger(isMatch);
		writeInteger(totalGold);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE21_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE21_BOUNS";
	}

	public int getRewardPool(){
		return rewardPool;
	}
		
	public void setRewardPool(int rewardPool){
		this.rewardPool = rewardPool;
	}

	public int getIsMatch(){
		return isMatch;
	}
		
	public void setIsMatch(int isMatch){
		this.isMatch = isMatch;
	}

	public int getTotalGold(){
		return totalGold;
	}
		
	public void setTotalGold(int totalGold){
		this.totalGold = totalGold;
	}
}