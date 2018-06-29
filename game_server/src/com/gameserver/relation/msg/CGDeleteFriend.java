package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.relation.handler.RelationHandlerFactory;

/**
 * 客户端删除好友请求
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGDeleteFriend extends CGMessage{
	
	/** 好友id */
	private long friendId;
	
	public CGDeleteFriend (){
	}
	
	public CGDeleteFriend (
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
		return MessageType.CG_DELETE_FRIEND;
	}
	
	@Override
	public String getTypeName() {
		return "CG_DELETE_FRIEND";
	}

	public long getFriendId(){
		return friendId;
	}
		
	public void setFriendId(long friendId){
		this.friendId = friendId;
	}
	


	@Override
	public void execute() {
		RelationHandlerFactory.getHandler().handleDeleteFriend(this.getSession().getPlayer(), this);
	}
}