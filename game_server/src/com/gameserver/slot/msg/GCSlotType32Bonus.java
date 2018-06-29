package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 斯巴达老虎机 闯关游戏
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType32Bonus extends GCMessage{
	
	/** 有几条数据就是走了几关，然后每一条数据是每一关 攻击或防守的次数 */
	private int[] levelList;
	/** 每一次攻击是否成功，0否，1是  ，顺序排列 */
	private int[] isSuccessList;
	/** 每一次攻击获得的奖励，失败就是0 */
	private long[] rewardNumList;

	public GCSlotType32Bonus (){
	}
	
	public GCSlotType32Bonus (
			int[] levelList,
			int[] isSuccessList,
			long[] rewardNumList ){
			this.levelList = levelList;
			this.isSuccessList = isSuccessList;
			this.rewardNumList = rewardNumList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		levelList = new int[count];
		for(int i=0; i<count; i++){
			levelList[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		isSuccessList = new int[count];
		for(int i=0; i<count; i++){
			isSuccessList[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		rewardNumList = new long[count];
		for(int i=0; i<count; i++){
			rewardNumList[i] = readLong();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(levelList.length);
		for(int i=0; i<levelList.length; i++){
			writeInteger(levelList[i]);
		}
		writeShort(isSuccessList.length);
		for(int i=0; i<isSuccessList.length; i++){
			writeInteger(isSuccessList[i]);
		}
		writeShort(rewardNumList.length);
		for(int i=0; i<rewardNumList.length; i++){
			writeLong(rewardNumList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE32_BONUS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE32_BONUS";
	}

	public int[] getLevelList(){
		return levelList;
	}

	public void setLevelList(int[] levelList){
		this.levelList = levelList;
	}	

	public int[] getIsSuccessList(){
		return isSuccessList;
	}

	public void setIsSuccessList(int[] isSuccessList){
		this.isSuccessList = isSuccessList;
	}	

	public long[] getRewardNumList(){
		return rewardNumList;
	}

	public void setRewardNumList(long[] rewardNumList){
		this.rewardNumList = rewardNumList;
	}	
}