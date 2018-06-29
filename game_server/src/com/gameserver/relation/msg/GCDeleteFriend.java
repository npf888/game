package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 客户端删除好友请求
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCDeleteFriend extends GCMessage{
	
	/** 好友id */
	private long friendId;

	public GCDeleteFriend (){
	}
	
	public GCDeleteFriend (
			long friendId ){
			this.friendId = friendId;
	}

	@Override
	protected boolean readImpl() {
		friendId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(friendId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_DELETE_FRIEND;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DELETE_FRIEND";
	}

	public long getFriendId(){
		return friendId;
	}
		
	public void setFriendId(long friendId){
		this.friendId = friendId;
	}
}