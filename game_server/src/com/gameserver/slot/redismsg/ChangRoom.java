package com.gameserver.slot.redismsg;

import com.gameserver.common.Globals;
import com.gameserver.redis.IRedisMessage;

public class ChangRoom implements IRedisMessage {
	
	private long passportId;

	private String roomId;
	
	
	
	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}



	@Override
	public void execute() {
        Globals.getSlotRoomService().changeRoomId(passportId, roomId);
	}

}
