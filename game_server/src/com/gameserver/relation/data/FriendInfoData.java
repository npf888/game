package com.gameserver.relation.data;

import com.gameserver.relation.Friend;

/**
 * 玩家信息
 * @author wayne
 *
 */
public class FriendInfoData {
	private long playerId;
	

	public void setPlayId(long playerId) {
		// TODO Auto-generated method stub
		this.playerId = playerId;
	}

	public long getPlayId() {
		// TODO Auto-generated method stub
		return this.playerId;
	}

	public static FriendInfoData convertFrom(Friend friend)
	{
		FriendInfoData friendInfoData = new FriendInfoData();
		friendInfoData.setPlayId(friend.getFriendId());
		return friendInfoData;
	}
}
