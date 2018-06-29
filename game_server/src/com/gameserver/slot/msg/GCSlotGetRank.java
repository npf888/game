package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 返回老虎机前3名排行数据 变化的时候也会主动推给客户端
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotGetRank extends GCMessage{
	
	/** 排行榜列表 */
	private com.gameserver.slot.data.SlotRankData[] slotRankData;

	public GCSlotGetRank (){
	}
	
	public GCSlotGetRank (
			com.gameserver.slot.data.SlotRankData[] slotRankData ){
			this.slotRankData = slotRankData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		slotRankData = new com.gameserver.slot.data.SlotRankData[count];
		for(int i=0; i<count; i++){
			com.gameserver.slot.data.SlotRankData obj = new com.gameserver.slot.data.SlotRankData();
			obj.setTournamentType(readInteger());
			obj.setRank(readInteger());
			obj.setImg(readString());
			obj.setName(readString());
			obj.setBonus(readLong());
			obj.setPassportId(readLong());
			obj.setVipLevel(readInteger());
			slotRankData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(slotRankData.length);
		for(int i=0; i<slotRankData.length; i++){
			writeInteger(slotRankData[i].getTournamentType());
			writeInteger(slotRankData[i].getRank());
			writeString(slotRankData[i].getImg());
			writeString(slotRankData[i].getName());
			writeLong(slotRankData[i].getBonus());
			writeLong(slotRankData[i].getPassportId());
			writeInteger(slotRankData[i].getVipLevel());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_GET_RANK;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_GET_RANK";
	}

	public com.gameserver.slot.data.SlotRankData[] getSlotRankData(){
		return slotRankData;
	}

	public void setSlotRankData(com.gameserver.slot.data.SlotRankData[] slotRankData){
		this.slotRankData = slotRankData;
	}	
}