package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 请求添加好友同步
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRequestFriendSync extends GCMessage{
	
	/** 玩家信息 */
	private com.gameserver.player.data.PlayerInfoData friendRequestInfoData;

	public GCRequestFriendSync (){
	}
	
	public GCRequestFriendSync (
			com.gameserver.player.data.PlayerInfoData friendRequestInfoData ){
			this.friendRequestInfoData = friendRequestInfoData;
	}

	@Override
	protected boolean readImpl() {
		friendRequestInfoData = new com.gameserver.player.data.PlayerInfoData();
					friendRequestInfoData.setPlayerId(readLong());
							friendRequestInfoData.setName(readString());
							friendRequestInfoData.setImg(readString());
							friendRequestInfoData.setGold(readLong());
							friendRequestInfoData.setDiamond(readLong());
							friendRequestInfoData.setCharm(readLong());
							friendRequestInfoData.setLevel(readLong());
							friendRequestInfoData.setSex(readInteger());
							friendRequestInfoData.setViplevel(readInteger());
							friendRequestInfoData.setCountries(readString());
							friendRequestInfoData.setAge(readInteger());
							friendRequestInfoData.setSlotRotate(readLong());
							friendRequestInfoData.setSlotWin(readLong());
							friendRequestInfoData.setSlotSingleWin(readLong());
							friendRequestInfoData.setSlotWinNum(readLong());
							friendRequestInfoData.setIntegral(readLong());
							friendRequestInfoData.setIsRequest(readInteger());
							friendRequestInfoData.setNewGuyGift(readInteger());
							friendRequestInfoData.setClubId(readString());
							friendRequestInfoData.setClubIco(readInteger());
							friendRequestInfoData.setClubInvitedTimes(readInteger());
							friendRequestInfoData.setAchieveRate(readString());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(friendRequestInfoData.getPlayerId());
		writeString(friendRequestInfoData.getName());
		writeString(friendRequestInfoData.getImg());
		writeLong(friendRequestInfoData.getGold());
		writeLong(friendRequestInfoData.getDiamond());
		writeLong(friendRequestInfoData.getCharm());
		writeLong(friendRequestInfoData.getLevel());
		writeInteger(friendRequestInfoData.getSex());
		writeInteger(friendRequestInfoData.getViplevel());
		writeString(friendRequestInfoData.getCountries());
		writeInteger(friendRequestInfoData.getAge());
		writeLong(friendRequestInfoData.getSlotRotate());
		writeLong(friendRequestInfoData.getSlotWin());
		writeLong(friendRequestInfoData.getSlotSingleWin());
		writeLong(friendRequestInfoData.getSlotWinNum());
		writeLong(friendRequestInfoData.getIntegral());
		writeInteger(friendRequestInfoData.getIsRequest());
		writeInteger(friendRequestInfoData.getNewGuyGift());
		writeString(friendRequestInfoData.getClubId());
		writeInteger(friendRequestInfoData.getClubIco());
		writeInteger(friendRequestInfoData.getClubInvitedTimes());
		writeString(friendRequestInfoData.getAchieveRate());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_REQUEST_FRIEND_SYNC;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REQUEST_FRIEND_SYNC";
	}

	public com.gameserver.player.data.PlayerInfoData getFriendRequestInfoData(){
		return friendRequestInfoData;
	}
		
	public void setFriendRequestInfoData(com.gameserver.player.data.PlayerInfoData friendRequestInfoData){
		this.friendRequestInfoData = friendRequestInfoData;
	}
}