package com.gameserver.relation.redismsg;

import com.db.model.HumanFriendGiftEntity;
import com.gameserver.redis.IRedisMessage;
import com.gameserver.relation.handler.RelationHandlerFactory;

public class FriendGiftRedisMessage implements IRedisMessage{
	private HumanFriendGiftEntity friendGiftEntity;

	
	public HumanFriendGiftEntity getFriendGiftEntity() {
		return friendGiftEntity;
	}


	public void setFriendGiftEntity(HumanFriendGiftEntity friendGiftEntity) {
		this.friendGiftEntity = friendGiftEntity;
	}


	@Override
	public void execute() {
		// TODO Auto-generated method stub
		RelationHandlerFactory.getRedisHandler().handleFriendGiftRedisMessage(this);
	}
}
