package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 客户端请求好友列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCLoadFriendList extends GCMessage{
	
	/** 玩家信息 */
	private com.gameserver.relation.data.FriendDetailInfoData[] friendDetailInfoDatalist;

	public GCLoadFriendList (){
	}
	
	public GCLoadFriendList (
			com.gameserver.relation.data.FriendDetailInfoData[] friendDetailInfoDatalist ){
			this.friendDetailInfoDatalist = friendDetailInfoDatalist;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		friendDetailInfoDatalist = new com.gameserver.relation.data.FriendDetailInfoData[count];
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
			friendDetailInfoDatalist[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(friendDetailInfoDatalist.length);
		for(int i=0; i<friendDetailInfoDatalist.length; i++){
			writeLong(friendDetailInfoDatalist[i].getPlayerId());
			writeString(friendDetailInfoDatalist[i].getName());
			writeString(friendDetailInfoDatalist[i].getImg());
			writeLong(friendDetailInfoDatalist[i].getGiftTime());
			writeLong(friendDetailInfoDatalist[i].getGold());
			writeLong(friendDetailInfoDatalist[i].getLevel());
			writeInteger(friendDetailInfoDatalist[i].getFacebook());
			writeInteger(friendDetailInfoDatalist[i].getSex());
			writeString(friendDetailInfoDatalist[i].getCountries());
			writeInteger(friendDetailInfoDatalist[i].getIsGame());
			writeInteger(friendDetailInfoDatalist[i].getPlayerState());
			writeLong(friendDetailInfoDatalist[i].getOfflineTime());
			writeInteger(friendDetailInfoDatalist[i].getVipLevel());
			writeInteger(friendDetailInfoDatalist[i].getAlreadyInvateClub());
			writeInteger(friendDetailInfoDatalist[i].getAlreadyJoinClub());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LOAD_FRIEND_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LOAD_FRIEND_LIST";
	}

	public com.gameserver.relation.data.FriendDetailInfoData[] getFriendDetailInfoDatalist(){
		return friendDetailInfoDatalist;
	}

	public void setFriendDetailInfoDatalist(com.gameserver.relation.data.FriendDetailInfoData[] friendDetailInfoDatalist){
		this.friendDetailInfoDatalist = friendDetailInfoDatalist;
	}	
}