package com.gameserver.slot.redismsg;

import com.gameserver.common.Globals;
import com.gameserver.player.Player;
import com.gameserver.redis.IRedisMessage;

public class RoleRoom implements IRedisMessage {

	private long passportId;
	
	private long playerId;
	private String img;
	private String countries;
	private int level;
	private long gold;
	
	private String name;
	
	public long getPassportId() {
		return passportId;
	}


	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}


	public long getPlayerId() {
		return playerId;
	}


	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}


	public String getImg() {
		return img;
	}


	public void setImg(String img) {
		this.img = img;
	}


	public String getCountries() {
		return countries;
	}


	public void setCountries(String countries) {
		this.countries = countries;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public long getGold() {
		return gold;
	}


	public void setGold(long gold) {
		this.gold = gold;
	}

	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public void execute() {
		
		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
		
		if(player != null){
			Globals.getSlotRoomService().sendRoomMessage2(player, playerId, img, countries, level,name);
			Globals.getSlotRoomService().sendRoomMessage3(player, playerId, gold);
		}
              
	}

}
