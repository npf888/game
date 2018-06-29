package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 维密老虎机自由转动结束后发送这个消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType12Free extends GCMessage{
	
	/** 奖金数没有乘倍数前的 */
	private long rewardNum;
	/** 倍数 */
	private int multiple;

	public GCSlotType12Free (){
	}
	
	public GCSlotType12Free (
			long rewardNum,
			int multiple ){
			this.rewardNum = rewardNum;
			this.multiple = multiple;
	}

	@Override
	protected boolean readImpl() {
		rewardNum = readLong();
		multiple = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(rewardNum);
		writeInteger(multiple);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE12_FREE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE12_FREE";
	}

	public long getRewardNum(){
		return rewardNum;
	}
		
	public void setRewardNum(long rewardNum){
		this.rewardNum = rewardNum;
	}

	public int getMultiple(){
		return multiple;
	}
		
	public void setMultiple(int multiple){
		this.multiple = multiple;
	}
}