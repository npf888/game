package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 客户端处理好友请求
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCAddFriend extends GCMessage{
	
	/** 玩家信息 */
	private com.gameserver.relation.data.FriendDetailInfoData friendDetailInfoData;

	public GCAddFriend (){
	}
	
	public GCAddFriend (
			com.gameserver.relation.data.FriendDetailInfoData friendDetailInfoData ){
			this.friendDetailInfoData = friendDetailInfoData;
	}

	@Override
	protected boolean readImpl() {
		friendDetailInfoData = new com.gameserver.relation.data.FriendDetailInfoData();
					friendDetailInfoData.setPlayerId(readLong());
							friendDetailInfoData.setName(readString());
							friendDetailInfoData.setImg(readString());
							friendDetailInfoData.setGiftTime(readLong());
							friendDetailInfoData.setGold(readLong());
							friendDetailInfoData.setLevel(readLong());
							friendDetailInfoData.setFacebook(readInteger());
							friendDetailInfoData.setSex(readInteger());
							friendDetailInfoData.setCountries(readString());
							friendDetailInfoData.setIsGame(readInteger());
							friendDetailInfoData.setPlayerState(readInteger());
							friendDetailInfoData.setOfflineTime(readLong());
							friendDetailInfoData.setVipLevel(readInteger());
							friendDetailInfoData.setAlreadyInvateClub(readInteger());
							friendDetailInfoData.setAlreadyJoinClub(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(friendDetailInfoData.getPlayerId());
		writeString(friendDetailInfoData.getName());
		writeString(friendDetailInfoData.getImg());
		writeLong(friendDetailInfoData.getGiftTime());
		writeLong(friendDetailInfoData.getGold());
		writeLong(friendDetailInfoData.getLevel());
		writeInteger(friendDetailInfoData.getFacebook());
		writeInteger(friendDetailInfoData.getSex());
		writeString(friendDetailInfoData.getCountries());
		writeInteger(friendDetailInfoData.getIsGame());
		writeInteger(friendDetailInfoData.getPlayerState());
		writeLong(friendDetailInfoData.getOfflineTime());
		writeInteger(friendDetailInfoData.getVipLevel());
		writeInteger(friendDetailInfoData.getAlreadyInvateClub());
		writeInteger(friendDetailInfoData.getAlreadyJoinClub());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ADD_FRIEND;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ADD_FRIEND";
	}

	public com.gameserver.relation.data.FriendDetailInfoData getFriendDetailInfoData(){
		return friendDetailInfoData;
	}
		
	public void setFriendDetailInfoData(com.gameserver.relation.data.FriendDetailInfoData friendDetailInfoData){
		this.friendDetailInfoData = friendDetailInfoData;
	}
}