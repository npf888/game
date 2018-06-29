package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 百家乐下注
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartSettle extends GCMessage{
	
	/** 庄家牌 */
	private int[] bankerCardList;
	/** 闲家牌 */
	private int[] playerCardList;
	/** 珠盘路 */
	private com.gameserver.baccart.data.PearlRoadData pearlRoadData;
	/** 赢钱列表 */
	private com.gameserver.baccart.data.BaccartSettleData[] settleDataList;

	public GCBaccartSettle (){
	}
	
	public GCBaccartSettle (
			int[] bankerCardList,
			int[] playerCardList,
			com.gameserver.baccart.data.PearlRoadData pearlRoadData,
			com.gameserver.baccart.data.BaccartSettleData[] settleDataList ){
			this.bankerCardList = bankerCardList;
			this.playerCardList = playerCardList;
			this.pearlRoadData = pearlRoadData;
			this.settleDataList = settleDataList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		bankerCardList = new int[count];
		for(int i=0; i<count; i++){
			bankerCardList[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		playerCardList = new int[count];
		for(int i=0; i<count; i++){
			playerCardList[i] = readInteger();
		}
		pearlRoadData = new com.gameserver.baccart.data.PearlRoadData();
					pearlRoadData.setBaccartPair(readInteger());
							pearlRoadData.setBaccartResult(readInteger());
				count = readShort();
		count = count < 0 ? 0 : count;
		settleDataList = new com.gameserver.baccart.data.BaccartSettleData[count];
		for(int i=0; i<count; i++){
			com.gameserver.baccart.data.BaccartSettleData obj = new com.gameserver.baccart.data.BaccartSettleData();
			obj.setPlayerId(readLong());
			obj.setWinCoins(readLong());
			settleDataList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(bankerCardList.length);
		for(int i=0; i<bankerCardList.length; i++){
			writeInteger(bankerCardList[i]);
		}
		writeShort(playerCardList.length);
		for(int i=0; i<playerCardList.length; i++){
			writeInteger(playerCardList[i]);
		}
		writeInteger(pearlRoadData.getBaccartPair());
		writeInteger(pearlRoadData.getBaccartResult());
		writeShort(settleDataList.length);
		for(int i=0; i<settleDataList.length; i++){
			writeLong(settleDataList[i].getPlayerId());
			writeLong(settleDataList[i].getWinCoins());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BACCART_SETTLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_SETTLE";
	}

	public int[] getBankerCardList(){
		return bankerCardList;
	}

	public void setBankerCardList(int[] bankerCardList){
		this.bankerCardList = bankerCardList;
	}	

	public int[] getPlayerCardList(){
		return playerCardList;
	}

	public void setPlayerCardList(int[] playerCardList){
		this.playerCardList = playerCardList;
	}	

	public com.gameserver.baccart.data.PearlRoadData getPearlRoadData(){
		return pearlRoadData;
	}
		
	public void setPearlRoadData(com.gameserver.baccart.data.PearlRoadData pearlRoadData){
		this.pearlRoadData = pearlRoadData;
	}

	public com.gameserver.baccart.data.BaccartSettleData[] getSettleDataList(){
		return settleDataList;
	}

	public void setSettleDataList(com.gameserver.baccart.data.BaccartSettleData[] settleDataList){
		this.settleDataList = settleDataList;
	}	
}