package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 竞技排行榜（始终包括自己）
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotRankList extends GCMessage{
	
	/** 竞赛类型展示 */
	private com.gameserver.slot.data.SlotRankData[] slotRankData;

	public GCSlotRankList (){
	}
	
	public GCSlotRankList (
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
		return MessageType.GC_SLOT_RANK_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_RANK_LIST";
	}

	public com.gameserver.slot.data.SlotRankData[] getSlotRankData(){
		return slotRankData;
	}

	public void setSlotRankData(com.gameserver.slot.data.SlotRankData[] slotRankData){
		this.slotRankData = slotRankData;
	}	
}