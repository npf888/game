package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 好友进入游戏通知
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCFriendGame extends GCMessage{
	
	/** 好友ID */
	private long friendId;

	public GCFriendGame (){
	}
	
	public GCFriendGame (
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
		return MessageType.GC_FRIEND_GAME;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FRIEND_GAME";
	}

	public long getFriendId(){
		return friendId;
	}
		
	public void setFriendId(long friendId){
		this.friendId = friendId;
	}
}