package com.gameserver.relation.redismsg;

import com.db.model.FriendRequestEntity;
import com.gameserver.redis.IRedisMessage;
import com.gameserver.relation.handler.RelationHandlerFactory;

/**
 * 请求加好友内部消息
 * @author wayne
 *
 */
public class FriendRequestRedisMessage implements IRedisMessage{

	
	
	private FriendRequestEntity friendRequestEntity;
	
	public FriendRequestRedisMessage(){
		
	}
	
	public FriendRequestEntity getFriendRequestEntity() {
		return friendRequestEntity;
	}

	public void setFriendRequestEntity(FriendRequestEntity friendRequestEntity) {
		this.friendRequestEntity = friendRequestEntity;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		RelationHandlerFactory.getRedisHandler().handleFriendRequestRedisMessage(this);
	}

}
