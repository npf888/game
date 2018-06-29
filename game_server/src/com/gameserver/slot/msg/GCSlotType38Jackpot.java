package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 万圣节老虎机   jackpot 
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType38Jackpot extends GCMessage{
	
	/** 第一部分中奖 */
	private long reward1;
	/** 第二部分中奖 */
	private long reward2;
	/** 第二部分中奖的 bonus的数量 */
	private long number;
	/** jackpot的位置 */
	private int[] JackpotList;

	public GCSlotType38Jackpot (){
	}
	
	public GCSlotType38Jackpot (
			long reward1,
			long reward2,
			long number,
			int[] JackpotList ){
			this.reward1 = reward1;
			this.reward2 = reward2;
			this.number = number;
			this.JackpotList = JackpotList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		reward1 = readLong();
		reward2 = readLong();
		number = readLong();
		count = readShort();
		count = count < 0 ? 0 : count;
		JackpotList = new int[count];
		for(int i=0; i<count; i++){
			JackpotList[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(reward1);
		writeLong(reward2);
		writeLong(number);
		writeShort(JackpotList.length);
		for(int i=0; i<JackpotList.length; i++){
			writeInteger(JackpotList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE38_JACKPOT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE38_JACKPOT";
	}

	public long getReward1(){
		return reward1;
	}
		
	public void setReward1(long reward1){
		this.reward1 = reward1;
	}

	public long getReward2(){
		return reward2;
	}
		
	public void setReward2(long reward2){
		this.reward2 = reward2;
	}

	public long getNumber(){
		return number;
	}
		
	public void setNumber(long number){
		this.number = number;
	}

	public int[] getJackpotList(){
		return JackpotList;
	}

	public void setJackpotList(int[] JackpotList){
		this.JackpotList = JackpotList;
	}	
}