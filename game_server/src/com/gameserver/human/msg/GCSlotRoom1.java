package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 玩家进入老虎机获取其他人信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotRoom1 extends GCMessage{
	
	/** 房间玩家列表  */
	private com.gameserver.slot.data.HumanBroadcastInfo[] humanBroadcastInfo;

	public GCSlotRoom1 (){
	}
	
	public GCSlotRoom1 (
			com.gameserver.slot.data.HumanBroadcastInfo[] humanBroadcastInfo ){
			this.humanBroadcastInfo = humanBroadcastInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		humanBroadcastInfo = new com.gameserver.slot.data.HumanBroadcastInfo[count];
		for(int i=0; i<count; i++){
			com.gameserver.slot.data.HumanBroadcastInfo obj = new com.gameserver.slot.data.HumanBroadcastInfo();
			obj.setPlayerId(readLong());
			obj.setImg(readString());
			obj.setLevel(readInteger());
			obj.setCountries(readString());
			obj.setGold(readLong());
			obj.setName(readString());
			obj.setGiftId(readInteger());
			obj.setIsRequest(readInteger());
			obj.setVipLevel(readInteger());
			obj.setGirlFlag(readInteger());
			humanBroadcastInfo[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(humanBroadcastInfo.length);
		for(int i=0; i<humanBroadcastInfo.length; i++){
			writeLong(humanBroadcastInfo[i].getPlayerId());
			writeString(humanBroadcastInfo[i].getImg());
			writeInteger(humanBroadcastInfo[i].getLevel());
			writeString(humanBroadcastInfo[i].getCountries());
			writeLong(humanBroadcastInfo[i].getGold());
			writeString(humanBroadcastInfo[i].getName());
			writeInteger(humanBroadcastInfo[i].getGiftId());
			writeInteger(humanBroadcastInfo[i].getIsRequest());
			writeInteger(humanBroadcastInfo[i].getVipLevel());
			writeInteger(humanBroadcastInfo[i].getGirlFlag());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_ROOM1;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_ROOM1";
	}

	public com.gameserver.slot.data.HumanBroadcastInfo[] getHumanBroadcastInfo(){
		return humanBroadcastInfo;
	}

	public void setHumanBroadcastInfo(com.gameserver.slot.data.HumanBroadcastInfo[] humanBroadcastInfo){
		this.humanBroadcastInfo = humanBroadcastInfo;
	}	
}