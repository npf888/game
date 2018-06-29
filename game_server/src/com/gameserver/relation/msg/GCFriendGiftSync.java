package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 发送礼物同步
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCFriendGiftSync extends GCMessage{
	
	/** 玩家信息 */
	private com.gameserver.relation.data.FriendGiftInfoData friendGiftInfoData;

	public GCFriendGiftSync (){
	}
	
	public GCFriendGiftSync (
			com.gameserver.relation.data.FriendGiftInfoData friendGiftInfoData ){
			this.friendGiftInfoData = friendGiftInfoData;
	}

	@Override
	protected boolean readImpl() {
		friendGiftInfoData = new com.gameserver.relation.data.FriendGiftInfoData();
					friendGiftInfoData.setGiftId(readLong());
							friendGiftInfoData.setPlayerId(readLong());
							friendGiftInfoData.setName(readString());
							friendGiftInfoData.setImg(readString());
							friendGiftInfoData.setSendTime(readLong());
							friendGiftInfoData.setGetTime(readLong());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(friendGiftInfoData.getGiftId());
		writeLong(friendGiftInfoData.getPlayerId());
		writeString(friendGiftInfoData.getName());
		writeString(friendGiftInfoData.getImg());
		writeLong(friendGiftInfoData.getSendTime());
		writeLong(friendGiftInfoData.getGetTime());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FRIEND_GIFT_SYNC;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FRIEND_GIFT_SYNC";
	}

	public com.gameserver.relation.data.FriendGiftInfoData getFriendGiftInfoData(){
		return friendGiftInfoData;
	}
		
	public void setFriendGiftInfoData(com.gameserver.relation.data.FriendGiftInfoData friendGiftInfoData){
		this.friendGiftInfoData = friendGiftInfoData;
	}
}