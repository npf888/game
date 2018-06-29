package com.gameserver.slot.redismsg;

import com.gameserver.common.Globals;
import com.gameserver.player.Player;
import com.gameserver.redis.IRedisMessage;

public class NotifyFriends implements IRedisMessage {
	
	private long friendId;
	
	private long playerId;
	
	public long getFriendId() {
		return friendId;
	}




	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}




	public long getPlayerId() {
		return playerId;
	}




	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}




	@Override
	public void execute() {
		
		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(friendId);
		
		if(player != null){
			player.sendMessage(Globals.getSlotRoomService().sendFriendsRoom(playerId));
		}

	}
	

}
