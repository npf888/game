package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 石器时代老虎机 苹果
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType14AppleBonus extends GCMessage{
	
	/** 每次敲打的苹果的数据 */
	private com.gameserver.slot.data.Bonus14Data[] bonus14Data;

	public GCSlotType14AppleBonus (){
	}
	
	public GCSlotType14AppleBonus (
			com.gameserver.slot.data.Bonus14Data[] bonus14Data ){
			this.bonus14Data = bonus14Data;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		bonus14Data = new com.gameserver.slot.data.Bonus14Data[count];
		for(int i=0; i<count; i++){
			com.gameserver.slot.data.Bonus14Data obj = new com.gameserver.slot.data.Bonus14Data();
			obj.setTimes(readInteger());
			obj.setSingleGold(readLong());
			obj.setOverlyingGold(readLong());
			obj.setSingleCollectNum(readInteger());
			obj.setOverlyingCollectNum(readInteger());
			bonus14Data[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(bonus14Data.length);
		for(int i=0; i<bonus14Data.length; i++){
			writeInteger(bonus14Data[i].getTimes());
			writeLong(bonus14Data[i].getSingleGold());
			writeLong(bonus14Data[i].getOverlyingGold());
			writeInteger(bonus14Data[i].getSingleCollectNum());
			writeInteger(bonus14Data[i].getOverlyingCollectNum());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE14_APPLE_BONUS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE14_APPLE_BONUS";
	}

	public com.gameserver.slot.data.Bonus14Data[] getBonus14Data(){
		return bonus14Data;
	}

	public void setBonus14Data(com.gameserver.slot.data.Bonus14Data[] bonus14Data){
		this.bonus14Data = bonus14Data;
	}	
}