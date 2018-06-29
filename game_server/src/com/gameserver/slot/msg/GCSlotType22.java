package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 西部牛仔老虎机挖矿小游戏返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType22 extends GCMessage{
	
	/** 剩余次数 */
	private int remaining;
	/** 每次获得的金币 */
	private long[] rewardNum;

	public GCSlotType22 (){
	}
	
	public GCSlotType22 (
			int remaining,
			long[] rewardNum ){
			this.remaining = remaining;
			this.rewardNum = rewardNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		remaining = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		rewardNum = new long[count];
		for(int i=0; i<count; i++){
			rewardNum[i] = readLong();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remaining);
		writeShort(rewardNum.length);
		for(int i=0; i<rewardNum.length; i++){
			writeLong(rewardNum[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE22;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE22";
	}

	public int getRemaining(){
		return remaining;
	}
		
	public void setRemaining(int remaining){
		this.remaining = remaining;
	}

	public long[] getRewardNum(){
		return rewardNum;
	}

	public void setRewardNum(long[] rewardNum){
		this.rewardNum = rewardNum;
	}	
}