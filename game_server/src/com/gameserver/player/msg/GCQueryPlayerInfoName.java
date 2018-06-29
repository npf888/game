package com.gameserver.player.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 客户端请求用户信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCQueryPlayerInfoName extends GCMessage{
	
	/** 玩家信息 */
	private com.gameserver.player.data.PlayerInfoData[] playerInfoData;

	public GCQueryPlayerInfoName (){
	}
	
	public GCQueryPlayerInfoName (
			com.gameserver.player.data.PlayerInfoData[] playerInfoData ){
			this.playerInfoData = playerInfoData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		playerInfoData = new com.gameserver.player.data.PlayerInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.player.data.PlayerInfoData obj = new com.gameserver.player.data.PlayerInfoData();
			obj.setPlayerId(readLong());
			obj.setName(readString());
			obj.setImg(readString());
			obj.setGold(readLong());
			obj.setDiamond(readLong());
			obj.setCharm(readLong());
			obj.setLevel(readLong());
			obj.setSex(readInteger());
			obj.setViplevel(readInteger());
			obj.setCountries(readString());
			obj.setAge(readInteger());
			obj.setSlotRotate(readLong());
			obj.setSlotWin(readLong());
			obj.setSlotSingleWin(readLong());
			obj.setSlotWinNum(readLong());
			obj.setIntegral(readLong());
			obj.setIsRequest(readInteger());
			obj.setNewGuyGift(readInteger());
			obj.setClubId(readString());
			obj.setClubIco(readInteger());
			obj.setClubInvitedTimes(readInteger());
			obj.setAchieveRate(readString());
			playerInfoData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(playerInfoData.length);
		for(int i=0; i<playerInfoData.length; i++){
			writeLong(playerInfoData[i].getPlayerId());
			writeString(playerInfoData[i].getName());
			writeString(playerInfoData[i].getImg());
			writeLong(playerInfoData[i].getGold());
			writeLong(playerInfoData[i].getDiamond());
			writeLong(playerInfoData[i].getCharm());
			writeLong(playerInfoData[i].getLevel());
			writeInteger(playerInfoData[i].getSex());
			writeInteger(playerInfoData[i].getViplevel());
			writeString(playerInfoData[i].getCountries());
			writeInteger(playerInfoData[i].getAge());
			writeLong(playerInfoData[i].getSlotRotate());
			writeLong(playerInfoData[i].getSlotWin());
			writeLong(playerInfoData[i].getSlotSingleWin());
			writeLong(playerInfoData[i].getSlotWinNum());
			writeLong(playerInfoData[i].getIntegral());
			writeInteger(playerInfoData[i].getIsRequest());
			writeInteger(playerInfoData[i].getNewGuyGift());
			writeString(playerInfoData[i].getClubId());
			writeInteger(playerInfoData[i].getClubIco());
			writeInteger(playerInfoData[i].getClubInvitedTimes());
			writeString(playerInfoData[i].getAchieveRate());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_QUERY_PLAYER_INFO_NAME;
	}
	
	@Override
	public String getTypeName() {
		return "GC_QUERY_PLAYER_INFO_NAME";
	}

	public com.gameserver.player.data.PlayerInfoData[] getPlayerInfoData(){
		return playerInfoData;
	}

	public void setPlayerInfoData(com.gameserver.player.data.PlayerInfoData[] playerInfoData){
		this.playerInfoData = playerInfoData;
	}	
}