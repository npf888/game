package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * slot
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCFreeSlotReward extends GCMessage{
	
	/** 盒子列表 */
	private int[] boxList;
	/** 盒子位置 */
	private int pos;
	/** 奖金数 */
	private long rewardNum;

	public GCFreeSlotReward (){
	}
	
	public GCFreeSlotReward (
			int[] boxList,
			int pos,
			long rewardNum ){
			this.boxList = boxList;
			this.pos = pos;
			this.rewardNum = rewardNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		boxList = new int[count];
		for(int i=0; i<count; i++){
			boxList[i] = readInteger();
		}
		pos = readInteger();
		rewardNum = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(boxList.length);
		for(int i=0; i<boxList.length; i++){
			writeInteger(boxList[i]);
		}
		writeInteger(pos);
		writeLong(rewardNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FREE_SLOT_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FREE_SLOT_REWARD";
	}

	public int[] getBoxList(){
		return boxList;
	}

	public void setBoxList(int[] boxList){
		this.boxList = boxList;
	}	

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}

	public long getRewardNum(){
		return rewardNum;
	}
		
	public void setRewardNum(long rewardNum){
		this.rewardNum = rewardNum;
	}
}