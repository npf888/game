package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 日月潭老虎机返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType11 extends GCMessage{
	
	/** 日月潭老虎机中奖倍数 */
	private int[] sunMoonLakeData;

	public GCSlotType11 (){
	}
	
	public GCSlotType11 (
			int[] sunMoonLakeData ){
			this.sunMoonLakeData = sunMoonLakeData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		sunMoonLakeData = new int[count];
		for(int i=0; i<count; i++){
			sunMoonLakeData[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(sunMoonLakeData.length);
		for(int i=0; i<sunMoonLakeData.length; i++){
			writeInteger(sunMoonLakeData[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE11;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE11";
	}

	public int[] getSunMoonLakeData(){
		return sunMoonLakeData;
	}

	public void setSunMoonLakeData(int[] sunMoonLakeData){
		this.sunMoonLakeData = sunMoonLakeData;
	}	
}