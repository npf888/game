package com.gameserver.relation.data;

import com.gameserver.player.cache.PlayerCacheInfo;
import com.gameserver.relation.FriendGift;

/**
 * 好友礼物
 * @author wayne
 *
 */
public class FriendGiftInfoData {
	private long giftId;
	private long playerId;
	private String name;
	private String img;
	private long sendTime;
	private long getTime;
	public long getGiftId() {
		return giftId;
	}
	public void setGiftId(long giftId) {
		this.giftId = giftId;
	}
	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public long getSendTime() {
		return sendTime;
	}
	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}
	public long getGetTime() {
		return getTime;
	}
	public void setGetTime(long getTime) {
		this.getTime = getTime;
	}
	
	public static FriendGiftInfoData convertFromFriendGiftAndPlayerCache(FriendGift friendGift,PlayerCacheInfo playerCacheInfo){
		FriendGiftInfoData friendGiftInfoData = new FriendGiftInfoData();
		friendGiftInfoData.setGiftId(friendGift.getDbId());
		friendGiftInfoData.setPlayerId(playerCacheInfo.getPlayerId());
		friendGiftInfoData.setName(playerCacheInfo.getName());
		friendGiftInfoData.setImg(playerCacheInfo.getImg());
		friendGiftInfoData.setGetTime(friendGift.getGetTime());
		friendGiftInfoData.setSendTime(friendGift.getCreateTime());
		return friendGiftInfoData;
	}
}
