package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 海盗老虎机:bonus 游戏 第三个-宝藏探秘
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType31BonusThree extends GCMessage{
	
	/** 第几关 */
	private int whichNum;
	/** 类型 的集合 和 下边顺序对应  奖励类型： 0.金币 1.次数 2.下一关（也有金币奖励） */
	private int[] rewardTypeList;
	/** 奖励集合 和上边 顺序对应 */
	private long[] rewardNumList;
	/** 用户初始 点击宝藏次数 */
	private int startNum;

	public GCSlotType31BonusThree (){
	}
	
	public GCSlotType31BonusThree (
			int whichNum,
			int[] rewardTypeList,
			long[] rewardNumList,
			int startNum ){
			this.whichNum = whichNum;
			this.rewardTypeList = rewardTypeList;
			this.rewardNumList = rewardNumList;
			this.startNum = startNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		whichNum = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		rewardTypeList = new int[count];
		for(int i=0; i<count; i++){
			rewardTypeList[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		rewardNumList = new long[count];
		for(int i=0; i<count; i++){
			rewardNumList[i] = readLong();
		}
		startNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(whichNum);
		writeShort(rewardTypeList.length);
		for(int i=0; i<rewardTypeList.length; i++){
			writeInteger(rewardTypeList[i]);
		}
		writeShort(rewardNumList.length);
		for(int i=0; i<rewardNumList.length; i++){
			writeLong(rewardNumList[i]);
		}
		writeInteger(startNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE31_BONUS_THREE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE31_BONUS_THREE";
	}

	public int getWhichNum(){
		return whichNum;
	}
		
	public void setWhichNum(int whichNum){
		this.whichNum = whichNum;
	}

	public int[] getRewardTypeList(){
		return rewardTypeList;
	}

	public void setRewardTypeList(int[] rewardTypeList){
		this.rewardTypeList = rewardTypeList;
	}	

	public long[] getRewardNumList(){
		return rewardNumList;
	}

	public void setRewardNumList(long[] rewardNumList){
		this.rewardNumList = rewardNumList;
	}	

	public int getStartNum(){
		return startNum;
	}
		
	public void setStartNum(int startNum){
		this.startNum = startNum;
	}
}