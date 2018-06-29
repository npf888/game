package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州sng列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasSngList extends GCMessage{
	
	/** 玩家列表 */
	private com.gameserver.texas.data.sng.TexasSngInfoData[] sngInfoDataList;

	public GCTexasSngList (){
	}
	
	public GCTexasSngList (
			com.gameserver.texas.data.sng.TexasSngInfoData[] sngInfoDataList ){
			this.sngInfoDataList = sngInfoDataList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		sngInfoDataList = new com.gameserver.texas.data.sng.TexasSngInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.texas.data.sng.TexasSngInfoData obj = new com.gameserver.texas.data.sng.TexasSngInfoData();
			obj.setId(readInteger());
			obj.setOpenUp(readInteger());
			obj.setNums(readInteger());
			sngInfoDataList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(sngInfoDataList.length);
		for(int i=0; i<sngInfoDataList.length; i++){
			writeInteger(sngInfoDataList[i].getId());
			writeInteger(sngInfoDataList[i].getOpenUp());
			writeInteger(sngInfoDataList[i].getNums());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_SNG_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_SNG_LIST";
	}

	public com.gameserver.texas.data.sng.TexasSngInfoData[] getSngInfoDataList(){
		return sngInfoDataList;
	}

	public void setSngInfoDataList(com.gameserver.texas.data.sng.TexasSngInfoData[] sngInfoDataList){
		this.sngInfoDataList = sngInfoDataList;
	}	
}