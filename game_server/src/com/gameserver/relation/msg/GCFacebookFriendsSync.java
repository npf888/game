package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * facebook好友
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCFacebookFriendsSync extends GCMessage{
	
	/** 玩家信息 */
	private com.gameserver.relation.data.FriendDetailInfoData[] friendRequestInfoData;

	public GCFacebookFriendsSync (){
	}
	
	public GCFacebookFriendsSync (
			com.gameserver.relation.data.FriendDetailInfoData[] friendRequestInfoData ){
			this.friendRequestInfoData = friendRequestInfoData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		friendRequestInfoData = new com.gameserver.relation.data.FriendDetailInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.relation.data.FriendDetailInfoData obj = new com.gameserver.relation.data.FriendDetailInfoData();
			obj.setPlayerId(readLong());
			obj.setName(readString());
			obj.setImg(readString());
			obj.setGiftTime(readLong());
			obj.setGold(readLong());
			obj.setLevel(readLong());
			obj.setFacebook(readInteger());
			obj.setSex(readInteger());
			obj.setCountries(readString());
			obj.setIsGame(readInteger());
			obj.setPlayerState(readInteger());
			obj.setOfflineTime(readLong());
			obj.setVipLevel(readInteger());
			obj.setAlreadyInvateClub(readInteger());
			obj.setAlreadyJoinClub(readInteger());
			friendRequestInfoData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(friendRequestInfoData.length);
		for(int i=0; i<friendRequestInfoData.length; i++){
			writeLong(friendRequestInfoData[i].getPlayerId());
			writeString(friendRequestInfoData[i].getName());
			writeString(friendRequestInfoData[i].getImg());
			writeLong(friendRequestInfoData[i].getGiftTime());
			writeLong(friendRequestInfoData[i].getGold());
			writeLong(friendRequestInfoData[i].getLevel());
			writeInteger(friendRequestInfoData[i].getFacebook());
			writeInteger(friendRequestInfoData[i].getSex());
			writeString(friendRequestInfoData[i].getCountries());
			writeInteger(friendRequestInfoData[i].getIsGame());
			writeInteger(friendRequestInfoData[i].getPlayerState());
			writeLong(friendRequestInfoData[i].getOfflineTime());
			writeInteger(friendRequestInfoData[i].getVipLevel());
			writeInteger(friendRequestInfoData[i].getAlreadyInvateClub());
			writeInteger(friendRequestInfoData[i].getAlreadyJoinClub());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FACEBOOK_FRIENDS_SYNC;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FACEBOOK_FRIENDS_SYNC";
	}

	public com.gameserver.relation.data.FriendDetailInfoData[] getFriendRequestInfoData(){
		return friendRequestInfoData;
	}

	public void setFriendRequestInfoData(com.gameserver.relation.data.FriendDetailInfoData[] friendRequestInfoData){
		this.friendRequestInfoData = friendRequestInfoData;
	}	
}