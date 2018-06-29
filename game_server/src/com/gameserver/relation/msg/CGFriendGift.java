package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.relation.handler.RelationHandlerFactory;

/**
 * 发送礼物
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGFriendGift extends CGMessage{
	
	/** 好友id */
	private long friendId;
	
	public CGFriendGift (){
	}
	
	public CGFriendGift (
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
		return MessageType.CG_FRIEND_GIFT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_FRIEND_GIFT";
	}

	public long getFriendId(){
		return friendId;
	}
		
	public void setFriendId(long friendId){
		this.friendId = friendId;
	}
	


	@Override
	public void execute() {
		RelationHandlerFactory.getHandler().handleFriendGift(this.getSession().getPlayer(), this);
	}
}