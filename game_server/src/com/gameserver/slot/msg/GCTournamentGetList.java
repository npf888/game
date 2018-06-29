package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 进入老虎机竞赛页面返回（竞赛 开始和结束都会推送此集合）
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTournamentGetList extends GCMessage{
	
	/** 竞赛类型展示 */
	private com.gameserver.slot.data.SngTournamentData[] sngTournamentData;

	public GCTournamentGetList (){
	}
	
	public GCTournamentGetList (
			com.gameserver.slot.data.SngTournamentData[] sngTournamentData ){
			this.sngTournamentData = sngTournamentData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		sngTournamentData = new com.gameserver.slot.data.SngTournamentData[count];
		for(int i=0; i<count; i++){
			com.gameserver.slot.data.SngTournamentData obj = new com.gameserver.slot.data.SngTournamentData();
			obj.setTournamentType(readInteger());
			obj.setEndTimeBlock(readLong());
			obj.setTotalHuman(readInteger());
			obj.setSngJackpot(readLong());
			obj.setVipLevel(readInteger());
			sngTournamentData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(sngTournamentData.length);
		for(int i=0; i<sngTournamentData.length; i++){
			writeInteger(sngTournamentData[i].getTournamentType());
			writeLong(sngTournamentData[i].getEndTimeBlock());
			writeInteger(sngTournamentData[i].getTotalHuman());
			writeLong(sngTournamentData[i].getSngJackpot());
			writeInteger(sngTournamentData[i].getVipLevel());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TOURNAMENT_GET_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TOURNAMENT_GET_LIST";
	}

	public com.gameserver.slot.data.SngTournamentData[] getSngTournamentData(){
		return sngTournamentData;
	}

	public void setSngTournamentData(com.gameserver.slot.data.SngTournamentData[] sngTournamentData){
		this.sngTournamentData = sngTournamentData;
	}	
}