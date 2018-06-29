package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 宙斯老虎机数据推送(主动推送 全部数据回去)
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType13SendBouns extends GCMessage{
	
	/** 相对应的 金币 或 免费次数 类型，1:金币，2:免费次数 */
	private int[] mtType;
	/** 相对应的 金币或者免费次数 */
	private long[] moneyOrTimes;
	/** 初始的免费次数 */
	private long times;

	public GCSlotType13SendBouns (){
	}
	
	public GCSlotType13SendBouns (
			int[] mtType,
			long[] moneyOrTimes,
			long times ){
			this.mtType = mtType;
			this.moneyOrTimes = moneyOrTimes;
			this.times = times;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		mtType = new int[count];
		for(int i=0; i<count; i++){
			mtType[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		moneyOrTimes = new long[count];
		for(int i=0; i<count; i++){
			moneyOrTimes[i] = readLong();
		}
		times = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(mtType.length);
		for(int i=0; i<mtType.length; i++){
			writeInteger(mtType[i]);
		}
		writeShort(moneyOrTimes.length);
		for(int i=0; i<moneyOrTimes.length; i++){
			writeLong(moneyOrTimes[i]);
		}
		writeLong(times);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE13_SEND_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE13_SEND_BOUNS";
	}

	public int[] getMtType(){
		return mtType;
	}

	public void setMtType(int[] mtType){
		this.mtType = mtType;
	}	

	public long[] getMoneyOrTimes(){
		return moneyOrTimes;
	}

	public void setMoneyOrTimes(long[] moneyOrTimes){
		this.moneyOrTimes = moneyOrTimes;
	}	

	public long getTimes(){
		return times;
	}
		
	public void setTimes(long times){
		this.times = times;
	}
}