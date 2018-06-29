package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 发送礼物
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCFriendGift extends GCMessage{
	
	/** 已发送礼物好友列表 */
	private com.gameserver.relation.data.FriendAlreadyGiftListData[] friendAlreadyGiftListData;

	public GCFriendGift (){
	}
	
	public GCFriendGift (
			com.gameserver.relation.data.FriendAlreadyGiftListData[] friendAlreadyGiftListData ){
			this.friendAlreadyGiftListData = friendAlreadyGiftListData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		friendAlreadyGiftListData = new com.gameserver.relation.data.FriendAlreadyGiftListData[count];
		for(int i=0; i<count; i++){
			com.gameserver.relation.data.FriendAlreadyGiftListData obj = new com.gameserver.relation.data.FriendAlreadyGiftListData();
			obj.setPlayId(readLong());
			friendAlreadyGiftListData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(friendAlreadyGiftListData.length);
		for(int i=0; i<friendAlreadyGiftListData.length; i++){
			writeLong(friendAlreadyGiftListData[i].getPlayId());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FRIEND_GIFT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FRIEND_GIFT";
	}

	public com.gameserver.relation.data.FriendAlreadyGiftListData[] getFriendAlreadyGiftListData(){
		return friendAlreadyGiftListData;
	}

	public void setFriendAlreadyGiftListData(com.gameserver.relation.data.FriendAlreadyGiftListData[] friendAlreadyGiftListData){
		this.friendAlreadyGiftListData = friendAlreadyGiftListData;
	}	
}