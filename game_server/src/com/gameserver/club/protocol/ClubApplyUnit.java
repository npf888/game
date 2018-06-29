package com.gameserver.club.protocol;

import com.gameserver.club.redis.ClubApplyData;

public class ClubApplyUnit {

    private long playerId;
    private String playerName;
    private String img;
    private String country;
    private long level;
    private long time;

  
    public long getPlayerId() {
		return playerId;
	}


	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}


	public String getPlayerName() {
		return playerName;
	}


	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}


	public String getImg() {
		return img;
	}


	public void setImg(String img) {
		this.img = img;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public long getLevel() {
		return level;
	}


	public void setLevel(long level) {
		this.level = level;
	}


	public long getTime() {
		return time;
	}


	public void setTime(long time) {
		this.time = time;
	}

//
//	public static ClubApplyUnit toProtocol(ClubApplyModel clubApplyModel, PlayerCacheInfo playerCacheInfo) {
//        ClubApplyUnit protocol = new ClubApplyUnit();
//		protocol.setPlayerId(clubApplyModel.getPlayerId());
//		protocol.setCountry(playerCacheInfo.getCountries());
//		protocol.setImg(playerCacheInfo.getImg());
//		protocol.setLevel(playerCacheInfo.getLevel());
//		protocol.setPlayerName(playerCacheInfo.getName());
//		protocol.setTime(clubApplyModel.getApplyTime().getTime());
//        return protocol;
//    }
	
	public static ClubApplyUnit toProtocol(ClubApplyData clubApplyData, long ts) {
        ClubApplyUnit protocol = new ClubApplyUnit();
		protocol.setPlayerId(clubApplyData.passportId);
		protocol.setCountry(clubApplyData.country);
		protocol.setImg(clubApplyData.img);
		protocol.setLevel(clubApplyData.level);
		protocol.setPlayerName(clubApplyData.name);
		protocol.setTime(ts);
        return protocol;
    }
}
