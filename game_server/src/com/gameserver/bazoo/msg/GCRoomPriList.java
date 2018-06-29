package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 所有的房间的返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRoomPriList extends GCMessage{
	
	/** 所有的房间 */
	private com.gameserver.bazoo.data.PriRoomData[] priRoomData;

	public GCRoomPriList (){
	}
	
	public GCRoomPriList (
			com.gameserver.bazoo.data.PriRoomData[] priRoomData ){
			this.priRoomData = priRoomData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		priRoomData = new com.gameserver.bazoo.data.PriRoomData[count];
		for(int i=0; i<count; i++){
			com.gameserver.bazoo.data.PriRoomData obj = new com.gameserver.bazoo.data.PriRoomData();
			obj.setRoomNumber(readString());
			obj.setCreater(readString());
			obj.setCreaterPassportId(readLong());
			obj.setFlag(readInteger());
			obj.setVip(readInteger());
			obj.setModeType(readInteger());
			obj.setBet(readInteger());
			obj.setImg(readString());
			obj.setNumTotalNum(readString());
			obj.setIsNeedPassword(readInteger());
			priRoomData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(priRoomData.length);
		for(int i=0; i<priRoomData.length; i++){
			writeString(priRoomData[i].getRoomNumber());
			writeString(priRoomData[i].getCreater());
			writeLong(priRoomData[i].getCreaterPassportId());
			writeInteger(priRoomData[i].getFlag());
			writeInteger(priRoomData[i].getVip());
			writeInteger(priRoomData[i].getModeType());
			writeInteger(priRoomData[i].getBet());
			writeString(priRoomData[i].getImg());
			writeString(priRoomData[i].getNumTotalNum());
			writeInteger(priRoomData[i].getIsNeedPassword());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ROOM_PRI_List;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROOM_PRI_List";
	}

	public com.gameserver.bazoo.data.PriRoomData[] getPriRoomData(){
		return priRoomData;
	}

	public void setPriRoomData(com.gameserver.bazoo.data.PriRoomData[] priRoomData){
		this.priRoomData = priRoomData;
	}	
}