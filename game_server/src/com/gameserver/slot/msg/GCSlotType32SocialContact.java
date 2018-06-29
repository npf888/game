package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 斯巴达老虎机社交活动开启
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType32SocialContact extends GCMessage{
	
	/** 社交的自由转动数量 */
	private int freeNum;
	/** 社交的奖励 一次排列  */
	private long[] rewardList;

	public GCSlotType32SocialContact (){
	}
	
	public GCSlotType32SocialContact (
			int freeNum,
			long[] rewardList ){
			this.freeNum = freeNum;
			this.rewardList = rewardList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		freeNum = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		rewardList = new long[count];
		for(int i=0; i<count; i++){
			rewardList[i] = readLong();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(freeNum);
		writeShort(rewardList.length);
		for(int i=0; i<rewardList.length; i++){
			writeLong(rewardList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE32_SOCIAL_CONTACT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE32_SOCIAL_CONTACT";
	}

	public int getFreeNum(){
		return freeNum;
	}
		
	public void setFreeNum(int freeNum){
		this.freeNum = freeNum;
	}

	public long[] getRewardList(){
		return rewardList;
	}

	public void setRewardList(long[] rewardList){
		this.rewardList = rewardList;
	}	
}