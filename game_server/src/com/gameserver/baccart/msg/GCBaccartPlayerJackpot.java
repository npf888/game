package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 玩家彩金
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartPlayerJackpot extends GCMessage{
	
	/** 个人彩金信息 */
	private com.gameserver.baccart.data.BaccartJackpotData[] jackpotDataList;

	public GCBaccartPlayerJackpot (){
	}
	
	public GCBaccartPlayerJackpot (
			com.gameserver.baccart.data.BaccartJackpotData[] jackpotDataList ){
			this.jackpotDataList = jackpotDataList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		jackpotDataList = new com.gameserver.baccart.data.BaccartJackpotData[count];
		for(int i=0; i<count; i++){
			com.gameserver.baccart.data.BaccartJackpotData obj = new com.gameserver.baccart.data.BaccartJackpotData();
			obj.setJackpot(readLong());
			obj.setPlayerId(readLong());
			jackpotDataList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(jackpotDataList.length);
		for(int i=0; i<jackpotDataList.length; i++){
			writeLong(jackpotDataList[i].getJackpot());
			writeLong(jackpotDataList[i].getPlayerId());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BACCART_PLAYER_JACKPOT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_PLAYER_JACKPOT";
	}

	public com.gameserver.baccart.data.BaccartJackpotData[] getJackpotDataList(){
		return jackpotDataList;
	}

	public void setJackpotDataList(com.gameserver.baccart.data.BaccartJackpotData[] jackpotDataList){
		this.jackpotDataList = jackpotDataList;
	}	
}