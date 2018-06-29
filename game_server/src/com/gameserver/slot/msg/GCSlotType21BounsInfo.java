package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 老虎老虎机bouns信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType21BounsInfo extends GCMessage{
	
	/** bouns个数 */
	private int bounsNum;
	/** bouns位置 */
	private int[] posList;
	/** 奖池的倍数从小到大 依次排列 */
	private long[] rewardTimeList;
	/** 是否匹配 */
	private int[] isMatch;
	/** 对应的奖金 */
	private long[] totalGold;
	/** 第几个奖池 */
	private int[] rewardPool;

	public GCSlotType21BounsInfo (){
	}
	
	public GCSlotType21BounsInfo (
			int bounsNum,
			int[] posList,
			long[] rewardTimeList,
			int[] isMatch,
			long[] totalGold,
			int[] rewardPool ){
			this.bounsNum = bounsNum;
			this.posList = posList;
			this.rewardTimeList = rewardTimeList;
			this.isMatch = isMatch;
			this.totalGold = totalGold;
			this.rewardPool = rewardPool;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		bounsNum = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		posList = new int[count];
		for(int i=0; i<count; i++){
			posList[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		rewardTimeList = new long[count];
		for(int i=0; i<count; i++){
			rewardTimeList[i] = readLong();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		isMatch = new int[count];
		for(int i=0; i<count; i++){
			isMatch[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		totalGold = new long[count];
		for(int i=0; i<count; i++){
			totalGold[i] = readLong();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		rewardPool = new int[count];
		for(int i=0; i<count; i++){
			rewardPool[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bounsNum);
		writeShort(posList.length);
		for(int i=0; i<posList.length; i++){
			writeInteger(posList[i]);
		}
		writeShort(rewardTimeList.length);
		for(int i=0; i<rewardTimeList.length; i++){
			writeLong(rewardTimeList[i]);
		}
		writeShort(isMatch.length);
		for(int i=0; i<isMatch.length; i++){
			writeInteger(isMatch[i]);
		}
		writeShort(totalGold.length);
		for(int i=0; i<totalGold.length; i++){
			writeLong(totalGold[i]);
		}
		writeShort(rewardPool.length);
		for(int i=0; i<rewardPool.length; i++){
			writeInteger(rewardPool[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE21_BOUNS_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE21_BOUNS_INFO";
	}

	public int getBounsNum(){
		return bounsNum;
	}
		
	public void setBounsNum(int bounsNum){
		this.bounsNum = bounsNum;
	}

	public int[] getPosList(){
		return posList;
	}

	public void setPosList(int[] posList){
		this.posList = posList;
	}	

	public long[] getRewardTimeList(){
		return rewardTimeList;
	}

	public void setRewardTimeList(long[] rewardTimeList){
		this.rewardTimeList = rewardTimeList;
	}	

	public int[] getIsMatch(){
		return isMatch;
	}

	public void setIsMatch(int[] isMatch){
		this.isMatch = isMatch;
	}	

	public long[] getTotalGold(){
		return totalGold;
	}

	public void setTotalGold(long[] totalGold){
		this.totalGold = totalGold;
	}	

	public int[] getRewardPool(){
		return rewardPool;
	}

	public void setRewardPool(int[] rewardPool){
		this.rewardPool = rewardPool;
	}	
}