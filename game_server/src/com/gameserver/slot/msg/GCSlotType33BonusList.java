package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 小红帽老虎机bonus小游戏  返回对象
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType33BonusList extends GCMessage{
	
	/** 每次摇色子的类型 */
	private int[] rollType;
	/** 每次摇色子的 值 */
	private long[] reward;
	/** 摇色子的次数 */
	private int selectNum;

	public GCSlotType33BonusList (){
	}
	
	public GCSlotType33BonusList (
			int[] rollType,
			long[] reward,
			int selectNum ){
			this.rollType = rollType;
			this.reward = reward;
			this.selectNum = selectNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		rollType = new int[count];
		for(int i=0; i<count; i++){
			rollType[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		reward = new long[count];
		for(int i=0; i<count; i++){
			reward[i] = readLong();
		}
		selectNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(rollType.length);
		for(int i=0; i<rollType.length; i++){
			writeInteger(rollType[i]);
		}
		writeShort(reward.length);
		for(int i=0; i<reward.length; i++){
			writeLong(reward[i]);
		}
		writeInteger(selectNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE33_BONUS_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE33_BONUS_LIST";
	}

	public int[] getRollType(){
		return rollType;
	}

	public void setRollType(int[] rollType){
		this.rollType = rollType;
	}	

	public long[] getReward(){
		return reward;
	}

	public void setReward(long[] reward){
		this.reward = reward;
	}	

	public int getSelectNum(){
		return selectNum;
	}
		
	public void setSelectNum(int selectNum){
		this.selectNum = selectNum;
	}
}