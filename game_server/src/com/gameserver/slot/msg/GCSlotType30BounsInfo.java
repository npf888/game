package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 福尔摩斯老虎机bouns信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType30BounsInfo extends GCMessage{
	
	/** bouns个数 */
	private int bounsNum;
	/** bouns位置 */
	private int[] posList;
	/** 第几次选择 */
	private int[] times;
	/** 第几次的奖励 */
	private long[] reward;

	public GCSlotType30BounsInfo (){
	}
	
	public GCSlotType30BounsInfo (
			int bounsNum,
			int[] posList,
			int[] times,
			long[] reward ){
			this.bounsNum = bounsNum;
			this.posList = posList;
			this.times = times;
			this.reward = reward;
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
		times = new int[count];
		for(int i=0; i<count; i++){
			times[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		reward = new long[count];
		for(int i=0; i<count; i++){
			reward[i] = readLong();
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
		writeShort(times.length);
		for(int i=0; i<times.length; i++){
			writeInteger(times[i]);
		}
		writeShort(reward.length);
		for(int i=0; i<reward.length; i++){
			writeLong(reward[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE30_BOUNS_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE30_BOUNS_INFO";
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

	public int[] getTimes(){
		return times;
	}

	public void setTimes(int[] times){
		this.times = times;
	}	

	public long[] getReward(){
		return reward;
	}

	public void setReward(long[] reward){
		this.reward = reward;
	}	
}