package com.gameserver.relation.data;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;

import com.db.dao.HumanDao;
import com.db.model.HumanEntity;
import com.gameserver.common.Globals;
import com.gameserver.player.cache.PlayerCacheInfo;
import com.gameserver.redis.enums.RedisKey;
import com.gameserver.relation.Friend;

public class FriendDetailInfoData {
	private long playerId;
	private String name;
	private String img;
	private long giftTime;
	private long gold;
	private long level;
	private int facebook;
	private int sex;
	/** VIP等级 **/
	private int vipLevel;
	
	private String countries;
	
	/**是否在游戏中 1 在游戏中 0 没有在 **/
   	private int isGame;
   	
     /**玩家状态 1 在线 0 不在线 **/
  	private int playerState;
   
   	/**下线时间点**/
   	private long offlineTime;

   	/**
   	 * 是否已经邀请加入俱乐部
   	 */
   	private int alreadyInvateClub;
   	/**
   	 * 是否已经加入俱乐部
   	 */
   	private int alreadyJoinClub;

	public int getAlreadyJoinClub() {
		return alreadyJoinClub;
	}

	public void setAlreadyJoinClub(int alreadyJoinClub) {
		this.alreadyJoinClub = alreadyJoinClub;
	}

	public int getAlreadyInvateClub() {
		return alreadyInvateClub;
	}

	public void setAlreadyInvateClub(int alreadyInvateClub) {
		this.alreadyInvateClub = alreadyInvateClub;
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
	public long getGiftTime() {
		return giftTime;
	}
	public void setGiftTime(long giftTime) {
		this.giftTime = giftTime;
	}
	
	public long getGold() {
		return gold;
	}
	public void setGold(long gold) {
		this.gold = gold;
	}
	/**
	 * @return the level
	 */
	public long getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(long level) {
		this.level = level;
	}
	/**
	 * @return the facebook
	 */
	public int getFacebook() {
		return facebook;
	}
	/**
	 * @param facebook the facebook to set
	 */
	public void setFacebook(int facebook) {
		this.facebook = facebook;
	}
	
	
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getCountries() {
		return countries;
	}
	public void setCountries(String countries) {
		this.countries = countries;
	
	}
	
	public int getIsGame() {
		return isGame;
	}
	public void setIsGame(int isGame) {
		this.isGame = isGame;
	}
	public int getPlayerState() {
		return playerState;
	}
	public void setPlayerState(int playerState) {
		this.playerState = playerState;
	}
	public long getOfflineTime() {
		return offlineTime;
	}
	public void setOfflineTime(long offlineTime) {
		this.offlineTime = offlineTime;
	}
	public int getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public static FriendDetailInfoData convertFromFriendAndPlayCacheInfo(Friend friend,PlayerCacheInfo playerCacheInfo){
		FriendDetailInfoData friendDetailInfoData = new FriendDetailInfoData();
		friendDetailInfoData.setGiftTime(friend.getGiftTime());
		friendDetailInfoData.setImg(playerCacheInfo.getImg());
		friendDetailInfoData.setLevel(playerCacheInfo.getLevel());
		friendDetailInfoData.setName(playerCacheInfo.getName());
		friendDetailInfoData.setPlayerId(playerCacheInfo.getPlayerId());
		friendDetailInfoData.setGold(playerCacheInfo.getGold());
		friendDetailInfoData.setCountries(playerCacheInfo.getCountries());
		friendDetailInfoData.setSex(playerCacheInfo.getSex());
		friendDetailInfoData.setIsGame(playerCacheInfo.getIsGame());
		friendDetailInfoData.setPlayerState(playerCacheInfo.getPlayerState());
		friendDetailInfoData.setOfflineTime(playerCacheInfo.getOfflineTime());
		friendDetailInfoData.setVipLevel(playerCacheInfo.getVipLevel());
		
		HumanDao hDao = Globals.getDaoService().getHumanDao();
		String clubId = "";
		List<HumanEntity> humanEntityList = hDao.loadHumans(playerCacheInfo.getPlayerId());
		if (humanEntityList != null && humanEntityList.get(0) != null) {
			HumanEntity en = humanEntityList.get(0);
			clubId = en.getClubId();
		}
		
		friendDetailInfoData.setAlreadyJoinClub(StringUtils.isEmpty(clubId)?0:1);
		Jedis j = null;
		try
		{
			j = Globals.getRedisService().getJedisPool().getResource();
			Long c = j.zcard(RedisKey.CLUB_INVITEE_INFO_SORT_SET__+playerCacheInfo.getPlayerId());
			friendDetailInfoData.setAlreadyInvateClub(c>0?1:0);
		}
		finally
		{
			if(j!=null)
			{
				j.close();
			}
		}
	
		return friendDetailInfoData;
	}
}
