package com.gameserver.slot.redismsg;

import com.gameserver.common.Globals;
import com.gameserver.player.Player;
import com.gameserver.redis.IRedisMessage;
import com.gameserver.slot.Slot;

public class EnterSlotRoomReturn implements IRedisMessage {
	
    private long passportId;
	
    private int newSlotId;
	
	private String newRoomId;

	private String str;
	
	public long getPassportId() {
		return passportId;
	}



	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}



	public String getStr() {
		return str;
	}



	public void setStr(String str) {
		this.str = str;
	}



	public int getNewSlotId() {
		return newSlotId;
	}



	public void setNewSlotId(int newSlotId) {
		this.newSlotId = newSlotId;
	}



	public String getNewRoomId() {
		return newRoomId;
	}



	public void setNewRoomId(String newRoomId) {
		this.newRoomId = newRoomId;
	}



	@Override
	public void execute() {
		
		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
		if(player != null){
			Slot slot = Globals.getSlotService().getSlotByTemplateId(newSlotId);
			Globals.getSlotRoomService().enterSlotzz(player,str,slot,newRoomId,newSlotId);
		}

	}

}
