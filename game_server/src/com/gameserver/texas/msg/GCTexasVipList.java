package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州vip列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasVipList extends GCMessage{
	
	/** 房间列表 */
	private com.gameserver.texas.data.vip.TexasVipInfoData[] vipInfoDataList;

	public GCTexasVipList (){
	}
	
	public GCTexasVipList (
			com.gameserver.texas.data.vip.TexasVipInfoData[] vipInfoDataList ){
			this.vipInfoDataList = vipInfoDataList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		vipInfoDataList = new com.gameserver.texas.data.vip.TexasVipInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.texas.data.vip.TexasVipInfoData obj = new com.gameserver.texas.data.vip.TexasVipInfoData();
			obj.setId(readLong());
			obj.setName(readString());
			obj.setTId(readInteger());
			obj.setSecure(readInteger());
			obj.setNum(readInteger());
			vipInfoDataList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(vipInfoDataList.length);
		for(int i=0; i<vipInfoDataList.length; i++){
			writeLong(vipInfoDataList[i].getId());
			writeString(vipInfoDataList[i].getName());
			writeInteger(vipInfoDataList[i].getTId());
			writeInteger(vipInfoDataList[i].getSecure());
			writeInteger(vipInfoDataList[i].getNum());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_VIP_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_VIP_LIST";
	}

	public com.gameserver.texas.data.vip.TexasVipInfoData[] getVipInfoDataList(){
		return vipInfoDataList;
	}

	public void setVipInfoDataList(com.gameserver.texas.data.vip.TexasVipInfoData[] vipInfoDataList){
		this.vipInfoDataList = vipInfoDataList;
	}	
}