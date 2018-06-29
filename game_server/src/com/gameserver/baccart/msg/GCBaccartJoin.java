package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 玩家加入
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartJoin extends GCMessage{
	
	/** 玩家信息列表 */
	private com.gameserver.baccart.data.BaccartPlayerData[] playerDataList;
	/** 珠盘路 */
	private com.gameserver.baccart.data.PearlRoadData[] pearlRoadDataList;
	/** 下注信息 */
	private com.gameserver.baccart.data.BaccartBetData[] betDataList;
	/** 房间状态 */
	private int roomState;
	/** jackpot */
	private long jackpot;
	/** 房间id */
	private int roomId;
	/** 剩余牌数 */
	private int remainCards;
	/** 剩余时间 */
	private long remainTime;

	public GCBaccartJoin (){
	}
	
	public GCBaccartJoin (
			com.gameserver.baccart.data.BaccartPlayerData[] playerDataList,
			com.gameserver.baccart.data.PearlRoadData[] pearlRoadDataList,
			com.gameserver.baccart.data.BaccartBetData[] betDataList,
			int roomState,
			long jackpot,
			int roomId,
			int remainCards,
			long remainTime ){
			this.playerDataList = playerDataList;
			this.pearlRoadDataList = pearlRoadDataList;
			this.betDataList = betDataList;
			this.roomState = roomState;
			this.jackpot = jackpot;
			this.roomId = roomId;
			this.remainCards = remainCards;
			this.remainTime = remainTime;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		playerDataList = new com.gameserver.baccart.data.BaccartPlayerData[count];
		for(int i=0; i<count; i++){
			com.gameserver.baccart.data.BaccartPlayerData obj = new com.gameserver.baccart.data.BaccartPlayerData();
			obj.setPlayerId(readLong());
			obj.setName(readString());
			obj.setImg(readString());
			obj.setGold(readLong());
			obj.setVip(readInteger());
			obj.setPos(readInteger());
			playerDataList[i] = obj;
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		pearlRoadDataList = new com.gameserver.baccart.data.PearlRoadData[count];
		for(int i=0; i<count; i++){
			com.gameserver.baccart.data.PearlRoadData obj = new com.gameserver.baccart.data.PearlRoadData();
			obj.setBaccartPair(readInteger());
			obj.setBaccartResult(readInteger());
			pearlRoadDataList[i] = obj;
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		betDataList = new com.gameserver.baccart.data.BaccartBetData[count];
		for(int i=0; i<count; i++){
			com.gameserver.baccart.data.BaccartBetData obj = new com.gameserver.baccart.data.BaccartBetData();
			obj.setBetType(readInteger());
			obj.setBetNum(readLong());
			betDataList[i] = obj;
		}
		roomState = readInteger();
		jackpot = readLong();
		roomId = readInteger();
		remainCards = readInteger();
		remainTime = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(playerDataList.length);
		for(int i=0; i<playerDataList.length; i++){
			writeLong(playerDataList[i].getPlayerId());
			writeString(playerDataList[i].getName());
			writeString(playerDataList[i].getImg());
			writeLong(playerDataList[i].getGold());
			writeInteger(playerDataList[i].getVip());
			writeInteger(playerDataList[i].getPos());
		}
		writeShort(pearlRoadDataList.length);
		for(int i=0; i<pearlRoadDataList.length; i++){
			writeInteger(pearlRoadDataList[i].getBaccartPair());
			writeInteger(pearlRoadDataList[i].getBaccartResult());
		}
		writeShort(betDataList.length);
		for(int i=0; i<betDataList.length; i++){
			writeInteger(betDataList[i].getBetType());
			writeLong(betDataList[i].getBetNum());
		}
		writeInteger(roomState);
		writeLong(jackpot);
		writeInteger(roomId);
		writeInteger(remainCards);
		writeLong(remainTime);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BACCART_JOIN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_JOIN";
	}

	public com.gameserver.baccart.data.BaccartPlayerData[] getPlayerDataList(){
		return playerDataList;
	}

	public void setPlayerDataList(com.gameserver.baccart.data.BaccartPlayerData[] playerDataList){
		this.playerDataList = playerDataList;
	}	

	public com.gameserver.baccart.data.PearlRoadData[] getPearlRoadDataList(){
		return pearlRoadDataList;
	}

	public void setPearlRoadDataList(com.gameserver.baccart.data.PearlRoadData[] pearlRoadDataList){
		this.pearlRoadDataList = pearlRoadDataList;
	}	

	public com.gameserver.baccart.data.BaccartBetData[] getBetDataList(){
		return betDataList;
	}

	public void setBetDataList(com.gameserver.baccart.data.BaccartBetData[] betDataList){
		this.betDataList = betDataList;
	}	

	public int getRoomState(){
		return roomState;
	}
		
	public void setRoomState(int roomState){
		this.roomState = roomState;
	}

	public long getJackpot(){
		return jackpot;
	}
		
	public void setJackpot(long jackpot){
		this.jackpot = jackpot;
	}

	public int getRoomId(){
		return roomId;
	}
		
	public void setRoomId(int roomId){
		this.roomId = roomId;
	}

	public int getRemainCards(){
		return remainCards;
	}
		
	public void setRemainCards(int remainCards){
		this.remainCards = remainCards;
	}

	public long getRemainTime(){
		return remainTime;
	}
		
	public void setRemainTime(long remainTime){
		this.remainTime = remainTime;
	}
}