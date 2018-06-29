package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 客户端请求好友礼物列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCLoadFriendGiftList extends GCMessage{
	
	/** 玩家信息 */
	private com.gameserver.relation.data.FriendGiftInfoData[] friendGiftInfoDataList;

	public GCLoadFriendGiftList (){
	}
	
	public GCLoadFriendGiftList (
			com.gameserver.relation.data.FriendGiftInfoData[] friendGiftInfoDataList ){
			this.friendGiftInfoDataList = friendGiftInfoDataList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		friendGiftInfoDataList = new com.gameserver.relation.data.FriendGiftInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.relation.data.FriendGiftInfoData obj = new com.gameserver.relation.data.FriendGiftInfoData();
			obj.setGiftId(readLong());
			obj.setPlayerId(readLong());
			obj.setName(readString());
			obj.setImg(readString());
			obj.setSendTime(readLong());
			obj.setGetTime(readLong());
			friendGiftInfoDataList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(friendGiftInfoDataList.length);
		for(int i=0; i<friendGiftInfoDataList.length; i++){
			writeLong(friendGiftInfoDataList[i].getGiftId());
			writeLong(friendGiftInfoDataList[i].getPlayerId());
			writeString(friendGiftInfoDataList[i].getName());
			writeString(friendGiftInfoDataList[i].getImg());
			writeLong(friendGiftInfoDataList[i].getSendTime());
			writeLong(friendGiftInfoDataList[i].getGetTime());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LOAD_FRIEND_GIFT_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LOAD_FRIEND_GIFT_LIST";
	}

	public com.gameserver.relation.data.FriendGiftInfoData[] getFriendGiftInfoDataList(){
		return friendGiftInfoDataList;
	}

	public void setFriendGiftInfoDataList(com.gameserver.relation.data.FriendGiftInfoData[] friendGiftInfoDataList){
		this.friendGiftInfoDataList = friendGiftInfoDataList;
	}	
}