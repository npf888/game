package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 石器时代老虎机 翻牌小游戏
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType14RuneBonus extends GCMessage{
	
	/** 流程-每次翻到的牌 */
	private int[] rewardPoolList;
	/** 相同 的数量 */
	private int sameNum;
	/** 获得的总的金币数量 */
	private long totalGold;

	public GCSlotType14RuneBonus (){
	}
	
	public GCSlotType14RuneBonus (
			int[] rewardPoolList,
			int sameNum,
			long totalGold ){
			this.rewardPoolList = rewardPoolList;
			this.sameNum = sameNum;
			this.totalGold = totalGold;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		rewardPoolList = new int[count];
		for(int i=0; i<count; i++){
			rewardPoolList[i] = readInteger();
		}
		sameNum = readInteger();
		totalGold = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(rewardPoolList.length);
		for(int i=0; i<rewardPoolList.length; i++){
			writeInteger(rewardPoolList[i]);
		}
		writeInteger(sameNum);
		writeLong(totalGold);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE14_RUNE_BONUS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE14_RUNE_BONUS";
	}

	public int[] getRewardPoolList(){
		return rewardPoolList;
	}

	public void setRewardPoolList(int[] rewardPoolList){
		this.rewardPoolList = rewardPoolList;
	}	

	public int getSameNum(){
		return sameNum;
	}
		
	public void setSameNum(int sameNum){
		this.sameNum = sameNum;
	}

	public long getTotalGold(){
		return totalGold;
	}
		
	public void setTotalGold(long totalGold){
		this.totalGold = totalGold;
	}
}