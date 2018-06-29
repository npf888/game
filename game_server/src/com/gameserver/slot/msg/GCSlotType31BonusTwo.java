package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 海盗老虎机:bonus 游戏 第二个-海岛钓鱼
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType31BonusTwo extends GCMessage{
	
	/** 奖池的倍数从小到大 依次排列 */
	private int[] rewardPoolList;
	/** 钓鱼轨迹 */
	private int[] matchNumList;
	/** 最终获得的奖励的金币数量 */
	private long gold;

	public GCSlotType31BonusTwo (){
	}
	
	public GCSlotType31BonusTwo (
			int[] rewardPoolList,
			int[] matchNumList,
			long gold ){
			this.rewardPoolList = rewardPoolList;
			this.matchNumList = matchNumList;
			this.gold = gold;
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
		count = readShort();
		count = count < 0 ? 0 : count;
		matchNumList = new int[count];
		for(int i=0; i<count; i++){
			matchNumList[i] = readInteger();
		}
		gold = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(rewardPoolList.length);
		for(int i=0; i<rewardPoolList.length; i++){
			writeInteger(rewardPoolList[i]);
		}
		writeShort(matchNumList.length);
		for(int i=0; i<matchNumList.length; i++){
			writeInteger(matchNumList[i]);
		}
		writeLong(gold);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE31_BONUS_TWO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE31_BONUS_TWO";
	}

	public int[] getRewardPoolList(){
		return rewardPoolList;
	}

	public void setRewardPoolList(int[] rewardPoolList){
		this.rewardPoolList = rewardPoolList;
	}	

	public int[] getMatchNumList(){
		return matchNumList;
	}

	public void setMatchNumList(int[] matchNumList){
		this.matchNumList = matchNumList;
	}	

	public long getGold(){
		return gold;
	}
		
	public void setGold(long gold){
		this.gold = gold;
	}
}