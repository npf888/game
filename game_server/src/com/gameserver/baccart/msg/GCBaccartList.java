package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 百家乐列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartList extends GCMessage{
	
	/** 百家乐房间列表 */
	private com.gameserver.baccart.data.BaccartRoomData[] baccartRoomDataList;

	public GCBaccartList (){
	}
	
	public GCBaccartList (
			com.gameserver.baccart.data.BaccartRoomData[] baccartRoomDataList ){
			this.baccartRoomDataList = baccartRoomDataList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		baccartRoomDataList = new com.gameserver.baccart.data.BaccartRoomData[count];
		for(int i=0; i<count; i++){
			com.gameserver.baccart.data.BaccartRoomData obj = new com.gameserver.baccart.data.BaccartRoomData();
			obj.setRoomId(readInteger());
			obj.setNum(readInteger());
			obj.setJackpot(readLong());
			obj.setClosed(readInteger());
			baccartRoomDataList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(baccartRoomDataList.length);
		for(int i=0; i<baccartRoomDataList.length; i++){
			writeInteger(baccartRoomDataList[i].getRoomId());
			writeInteger(baccartRoomDataList[i].getNum());
			writeLong(baccartRoomDataList[i].getJackpot());
			writeInteger(baccartRoomDataList[i].getClosed());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BACCART_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_LIST";
	}

	public com.gameserver.baccart.data.BaccartRoomData[] getBaccartRoomDataList(){
		return baccartRoomDataList;
	}

	public void setBaccartRoomDataList(com.gameserver.baccart.data.BaccartRoomData[] baccartRoomDataList){
		this.baccartRoomDataList = baccartRoomDataList;
	}	
}