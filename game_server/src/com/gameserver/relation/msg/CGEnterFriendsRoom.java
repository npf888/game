package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.relation.handler.RelationHandlerFactory;

/**
 * 进入好友房间
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGEnterFriendsRoom extends CGMessage{
	
	/** 好友ID */
	private long friendId;
	
	public CGEnterFriendsRoom (){
	}
	
	public CGEnterFriendsRoom (
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
		return MessageType.CG_ENTER_FRIENDS_ROOM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ENTER_FRIENDS_ROOM";
	}

	public long getFriendId(){
		return friendId;
	}
		
	public void setFriendId(long friendId){
		this.friendId = friendId;
	}
	


	@Override
	public void execute() {
		RelationHandlerFactory.getHandler().handleEnterFriendsRoom(this.getSession().getPlayer(), this);
	}
}