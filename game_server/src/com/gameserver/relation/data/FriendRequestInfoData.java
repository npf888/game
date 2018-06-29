package com.gameserver.relation.data;

import com.gameserver.relation.FriendRequest;

/**
 * 好友请求 
 * @author wayne
 *
 */
public class FriendRequestInfoData {
	private long playerId;

	public void setPlayId(long playerId) {
		// TODO Auto-generated method stub
		this.playerId = playerId;
	}

	public long getPlayId() {
		// TODO Auto-generated method stub
		return this.playerId;
	}

	public static FriendRequestInfoData convertFrom(FriendRequest friendRequest) {
		// TODO Auto-generated method stub
		FriendRequestInfoData friendRequestInfoData = new FriendRequestInfoData();
		friendRequestInfoData.setPlayId(friendRequest.getSendId());
		return friendRequestInfoData;
	}
}
