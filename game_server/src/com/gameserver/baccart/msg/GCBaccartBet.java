package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 百家乐下注
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartBet extends GCMessage{
	
	/** 玩家id */
	private long playerId;
	/** 下注列表 */
	private com.gameserver.baccart.data.BaccartBetData[] betDataList;

	public GCBaccartBet (){
	}
	
	public GCBaccartBet (
			long playerId,
			com.gameserver.baccart.data.BaccartBetData[] betDataList ){
			this.playerId = playerId;
			this.betDataList = betDataList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		playerId = readLong();
		count = readShort();
		count = count < 0 ? 0 : count;
		betDataList = new com.gameserver.baccart.data.BaccartBetData[count];
		for(int i=0; i<count; i++){
			com.gameserver.baccart.data.BaccartBetData obj = new com.gameserver.baccart.data.BaccartBetData();
			obj.setBetType(readInteger());
			obj.setBetNum(readLong());
			betDataList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerId);
		writeShort(betDataList.length);
		for(int i=0; i<betDataList.length; i++){
			writeInteger(betDataList[i].getBetType());
			writeLong(betDataList[i].getBetNum());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BACCART_BET;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_BET";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}

	public com.gameserver.baccart.data.BaccartBetData[] getBetDataList(){
		return betDataList;
	}

	public void setBetDataList(com.gameserver.baccart.data.BaccartBetData[] betDataList){
		this.betDataList = betDataList;
	}	
}