package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 百家乐筹码同步
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanBaccartCoinsSync extends GCMessage{
	
	/** 百家乐 */
	private com.gameserver.baccart.data.BaccartCoinSyncData[] baccartCoinSyncDataList;

	public GCHumanBaccartCoinsSync (){
	}
	
	public GCHumanBaccartCoinsSync (
			com.gameserver.baccart.data.BaccartCoinSyncData[] baccartCoinSyncDataList ){
			this.baccartCoinSyncDataList = baccartCoinSyncDataList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		baccartCoinSyncDataList = new com.gameserver.baccart.data.BaccartCoinSyncData[count];
		for(int i=0; i<count; i++){
			com.gameserver.baccart.data.BaccartCoinSyncData obj = new com.gameserver.baccart.data.BaccartCoinSyncData();
			obj.setCoins(readLong());
			obj.setPlayerId(readLong());
			baccartCoinSyncDataList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(baccartCoinSyncDataList.length);
		for(int i=0; i<baccartCoinSyncDataList.length; i++){
			writeLong(baccartCoinSyncDataList[i].getCoins());
			writeLong(baccartCoinSyncDataList[i].getPlayerId());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_BACCART_COINS_SYNC;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_BACCART_COINS_SYNC";
	}

	public com.gameserver.baccart.data.BaccartCoinSyncData[] getBaccartCoinSyncDataList(){
		return baccartCoinSyncDataList;
	}

	public void setBaccartCoinSyncDataList(com.gameserver.baccart.data.BaccartCoinSyncData[] baccartCoinSyncDataList){
		this.baccartCoinSyncDataList = baccartCoinSyncDataList;
	}	
}