package com.gameserver.slot.redismsg;

import com.gameserver.common.Globals;
import com.gameserver.player.Player;
import com.gameserver.redis.IRedisMessage;

public class ChangeInfo4 implements IRedisMessage {
	
	
	private long passportId;
     private long playerId;
     /**大奖类型 0：没有中大奖 1 2 3 4 **/
     private int bigAward;
     
     

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



	public int getBigAward() {
		return bigAward;
	}



	public void setBigAward(int bigAward) {
		this.bigAward = bigAward;
	}



	@Override
	public void execute() {
		Player player =  Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
        if(player != null){
        	  Globals.getSlotRoomService().sendRoomMessage4(player, playerId, bigAward);
        }

	}

}
