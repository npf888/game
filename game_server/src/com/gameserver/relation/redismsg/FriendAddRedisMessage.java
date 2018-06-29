package com.gameserver.relation.redismsg;

import com.db.model.FriendEntity;
import com.gameserver.redis.IRedisMessage;
import com.gameserver.relation.handler.RelationHandlerFactory;

/**
 * 添加好友
 * @author wayne
 *
 */
public class FriendAddRedisMessage implements IRedisMessage{

	public FriendEntity friendEntity;
	public FriendAddRedisMessage(){
		
	}
	
	public FriendEntity getFriendEntity() {
		return friendEntity;
	}

	public void setFriendEntity(FriendEntity friendEntity) {
		this.friendEntity = friendEntity;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		RelationHandlerFactory.getRedisHandler().handleFriendAddRedisMessage(this);
	}
	
}
