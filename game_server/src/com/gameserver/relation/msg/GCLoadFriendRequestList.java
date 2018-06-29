package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 客户端请求好友列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCLoadFriendRequestList extends GCMessage{
	
	/** 玩家信息 */
	private com.gameserver.player.data.PlayerInfoData[] friendRequestInfoDataList;

	public GCLoadFriendRequestList (){
	}
	
	public GCLoadFriendRequestList (
			com.gameserver.player.data.PlayerInfoData[] friendRequestInfoDataList ){
			this.friendRequestInfoDataList = friendRequestInfoDataList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		friendRequestInfoDataList = new com.gameserver.player.data.PlayerInfoData[count];
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
			friendRequestInfoDataList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(friendRequestInfoDataList.length);
		for(int i=0; i<friendRequestInfoDataList.length; i++){
			writeLong(friendRequestInfoDataList[i].getPlayerId());
			writeString(friendRequestInfoDataList[i].getName());
			writeString(friendRequestInfoDataList[i].getImg());
			writeLong(friendRequestInfoDataList[i].getGold());
			writeLong(friendRequestInfoDataList[i].getDiamond());
			writeLong(friendRequestInfoDataList[i].getCharm());
			writeLong(friendRequestInfoDataList[i].getLevel());
			writeInteger(friendRequestInfoDataList[i].getSex());
			writeInteger(friendRequestInfoDataList[i].getViplevel());
			writeString(friendRequestInfoDataList[i].getCountries());
			writeInteger(friendRequestInfoDataList[i].getAge());
			writeLong(friendRequestInfoDataList[i].getSlotRotate());
			writeLong(friendRequestInfoDataList[i].getSlotWin());
			writeLong(friendRequestInfoDataList[i].getSlotSingleWin());
			writeLong(friendRequestInfoDataList[i].getSlotWinNum());
			writeLong(friendRequestInfoDataList[i].getIntegral());
			writeInteger(friendRequestInfoDataList[i].getIsRequest());
			writeInteger(friendRequestInfoDataList[i].getNewGuyGift());
			writeString(friendRequestInfoDataList[i].getClubId());
			writeInteger(friendRequestInfoDataList[i].getClubIco());
			writeInteger(friendRequestInfoDataList[i].getClubInvitedTimes());
			writeString(friendRequestInfoDataList[i].getAchieveRate());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LOAD_FRIEND_REQUEST_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LOAD_FRIEND_REQUEST_LIST";
	}

	public com.gameserver.player.data.PlayerInfoData[] getFriendRequestInfoDataList(){
		return friendRequestInfoDataList;
	}

	public void setFriendRequestInfoDataList(com.gameserver.player.data.PlayerInfoData[] friendRequestInfoDataList){
		this.friendRequestInfoDataList = friendRequestInfoDataList;
	}	
}