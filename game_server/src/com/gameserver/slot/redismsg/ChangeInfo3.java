package com.gameserver.slot.redismsg;

import com.gameserver.common.Globals;
import com.gameserver.player.Player;
import com.gameserver.redis.IRedisMessage;

public class ChangeInfo3 implements IRedisMessage {


	private long passportId;
	
	 private long playerId;
		
	 private Long gold;
	 
	 
	 
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



	public Long getGold() {
		return gold;
	}



	public void setGold(Long gold) {
		this.gold = gold;
	}



	@Override
	public void execute() {
		Player player =  Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
        if(player != null){
        	  Globals.getSlotRoomService().sendRoomMessage3(player, playerId, gold);
        }

	}

}
