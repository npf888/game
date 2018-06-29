package com.gameserver.relation.redismsg;

import com.gameserver.redis.IRedisMessage;
import com.gameserver.relation.handler.RelationHandlerFactory;

public class FriendDeleteRedisMessage  implements IRedisMessage{

	private long playerId;
	private long friendId;
	
	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	public long getFriendId() {
		return friendId;
	}
	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		RelationHandlerFactory.getRedisHandler().handleFriendDeleteRedisMessage(this);
	}
	

}
