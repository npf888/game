package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 加入德州
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCJoinTexas extends GCMessage{
	
	/** 玩家列表 */
	private com.gameserver.texas.data.TexasRoomPlayerInfoData[] playerList;
	/** 房间类型 */
	private int roomType;
	/** sng或普通房 */
	private int texasRoomEnum;

	public GCJoinTexas (){
	}
	
	public GCJoinTexas (
			com.gameserver.texas.data.TexasRoomPlayerInfoData[] playerList,
			int roomType,
			int texasRoomEnum ){
			this.playerList = playerList;
			this.roomType = roomType;
			this.texasRoomEnum = texasRoomEnum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		playerList = new com.gameserver.texas.data.TexasRoomPlayerInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.texas.data.TexasRoomPlayerInfoData obj = new com.gameserver.texas.data.TexasRoomPlayerInfoData();
			obj.setPlayerId(readLong());
			obj.setName(readString());
			obj.setImg(readString());
			obj.setPlayerState(readInteger());
			obj.setCoins(readLong());
			obj.setVip(readInteger());
			obj.setPos(readInteger());
			obj.setCurrentBet(readLong());
			obj.setAllBet(readLong());
			playerList[i] = obj;
		}
		roomType = readInteger();
		texasRoomEnum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(playerList.length);
		for(int i=0; i<playerList.length; i++){
			writeLong(playerList[i].getPlayerId());
			writeString(playerList[i].getName());
			writeString(playerList[i].getImg());
			writeInteger(playerList[i].getPlayerState());
			writeLong(playerList[i].getCoins());
			writeInteger(playerList[i].getVip());
			writeInteger(playerList[i].getPos());
			writeLong(playerList[i].getCurrentBet());
			writeLong(playerList[i].getAllBet());
		}
		writeInteger(roomType);
		writeInteger(texasRoomEnum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_JOIN_TEXAS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_JOIN_TEXAS";
	}

	public com.gameserver.texas.data.TexasRoomPlayerInfoData[] getPlayerList(){
		return playerList;
	}

	public void setPlayerList(com.gameserver.texas.data.TexasRoomPlayerInfoData[] playerList){
		this.playerList = playerList;
	}	

	public int getRoomType(){
		return roomType;
	}
		
	public void setRoomType(int roomType){
		this.roomType = roomType;
	}

	public int getTexasRoomEnum(){
		return texasRoomEnum;
	}
		
	public void setTexasRoomEnum(int texasRoomEnum){
		this.texasRoomEnum = texasRoomEnum;
	}
}