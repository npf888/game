package com.gameserver.lobby.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 最高彩金获得者前20位
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCJackpotListData extends GCMessage{
	
	/** 彩金获得者数据 */
	private com.gameserver.lobby.data.JackpotList[] jackpotList;

	public GCJackpotListData (){
	}
	
	public GCJackpotListData (
			com.gameserver.lobby.data.JackpotList[] jackpotList ){
			this.jackpotList = jackpotList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		jackpotList = new com.gameserver.lobby.data.JackpotList[count];
		for(int i=0; i<count; i++){
			com.gameserver.lobby.data.JackpotList obj = new com.gameserver.lobby.data.JackpotList();
			obj.setUserId(readLong());
			obj.setImg(readString());
			obj.setName(readString());
			obj.setJackpot(readLong());
			obj.setGameType(readInteger());
			jackpotList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(jackpotList.length);
		for(int i=0; i<jackpotList.length; i++){
			writeLong(jackpotList[i].getUserId());
			writeString(jackpotList[i].getImg());
			writeString(jackpotList[i].getName());
			writeLong(jackpotList[i].getJackpot());
			writeInteger(jackpotList[i].getGameType());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_JACKPOT_LIST_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_JACKPOT_LIST_DATA";
	}

	public com.gameserver.lobby.data.JackpotList[] getJackpotList(){
		return jackpotList;
	}

	public void setJackpotList(com.gameserver.lobby.data.JackpotList[] jackpotList){
		this.jackpotList = jackpotList;
	}	
}