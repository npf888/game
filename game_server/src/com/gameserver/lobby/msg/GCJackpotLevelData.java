package com.gameserver.lobby.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 适合自己等级游戏的彩金
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCJackpotLevelData extends GCMessage{
	
	/** 适合自己等级游戏彩金 */
	private com.gameserver.lobby.data.JackpotfitLevelData[] jackpotfitLevelData;

	public GCJackpotLevelData (){
	}
	
	public GCJackpotLevelData (
			com.gameserver.lobby.data.JackpotfitLevelData[] jackpotfitLevelData ){
			this.jackpotfitLevelData = jackpotfitLevelData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		jackpotfitLevelData = new com.gameserver.lobby.data.JackpotfitLevelData[count];
		for(int i=0; i<count; i++){
			com.gameserver.lobby.data.JackpotfitLevelData obj = new com.gameserver.lobby.data.JackpotfitLevelData();
			obj.setGameType(readInteger());
			obj.setJackpot(readLong());
			jackpotfitLevelData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(jackpotfitLevelData.length);
		for(int i=0; i<jackpotfitLevelData.length; i++){
			writeInteger(jackpotfitLevelData[i].getGameType());
			writeLong(jackpotfitLevelData[i].getJackpot());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_JACKPOT_LEVEL_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_JACKPOT_LEVEL_DATA";
	}

	public com.gameserver.lobby.data.JackpotfitLevelData[] getJackpotfitLevelData(){
		return jackpotfitLevelData;
	}

	public void setJackpotfitLevelData(com.gameserver.lobby.data.JackpotfitLevelData[] jackpotfitLevelData){
		this.jackpotfitLevelData = jackpotfitLevelData;
	}	
}