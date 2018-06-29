package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州结算
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasSettle extends GCMessage{
	
	/** 玩家列表  */
	private com.gameserver.texas.data.TexasRoomPlayerSettleCardsInfoData[] playerList;
	/** 边池获胜列表  */
	private com.gameserver.texas.data.TexasPoolSettleInfoData[] settlePoolList;

	public GCTexasSettle (){
	}
	
	public GCTexasSettle (
			com.gameserver.texas.data.TexasRoomPlayerSettleCardsInfoData[] playerList,
			com.gameserver.texas.data.TexasPoolSettleInfoData[] settlePoolList ){
			this.playerList = playerList;
			this.settlePoolList = settlePoolList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		playerList = new com.gameserver.texas.data.TexasRoomPlayerSettleCardsInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.texas.data.TexasRoomPlayerSettleCardsInfoData obj = new com.gameserver.texas.data.TexasRoomPlayerSettleCardsInfoData();
			obj.setPos(readInteger());
			obj.setCardListStr(readString());
			obj.setFirstCard(readInteger());
			obj.setSecondCard(readInteger());
			playerList[i] = obj;
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		settlePoolList = new com.gameserver.texas.data.TexasPoolSettleInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.texas.data.TexasPoolSettleInfoData obj = new com.gameserver.texas.data.TexasPoolSettleInfoData();
			{
				int subCount = readShort();
							com.gameserver.texas.data.TexasRoomPlayerSettleInfoData[] subList = new com.gameserver.texas.data.TexasRoomPlayerSettleInfoData[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = new com.gameserver.texas.data.TexasRoomPlayerSettleInfoData();
													subList[j].setPos(readInteger());
													subList[j].setWinBet(readLong());
													subList[j].setHandCardType(readInteger());
															}
				obj.setWinnerList(subList);
			}
			settlePoolList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(playerList.length);
		for(int i=0; i<playerList.length; i++){
			writeInteger(playerList[i].getPos());
			writeString(playerList[i].getCardListStr());
			writeInteger(playerList[i].getFirstCard());
			writeInteger(playerList[i].getSecondCard());
		}
		writeShort(settlePoolList.length);
		for(int i=0; i<settlePoolList.length; i++){
			com.gameserver.texas.data.TexasRoomPlayerSettleInfoData[] winnerList=settlePoolList[i].getWinnerList();
			writeShort(winnerList.length);
			for(int j=0; j<winnerList.length; j++){
				writeInteger(winnerList[j].getPos());
				writeLong(winnerList[j].getWinBet());
				writeInteger(winnerList[j].getHandCardType());
			}
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_SETTLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_SETTLE";
	}

	public com.gameserver.texas.data.TexasRoomPlayerSettleCardsInfoData[] getPlayerList(){
		return playerList;
	}

	public void setPlayerList(com.gameserver.texas.data.TexasRoomPlayerSettleCardsInfoData[] playerList){
		this.playerList = playerList;
	}	

	public com.gameserver.texas.data.TexasPoolSettleInfoData[] getSettlePoolList(){
		return settlePoolList;
	}

	public void setSettlePoolList(com.gameserver.texas.data.TexasPoolSettleInfoData[] settlePoolList){
		this.settlePoolList = settlePoolList;
	}	
}