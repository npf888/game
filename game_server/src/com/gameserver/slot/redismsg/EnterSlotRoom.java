package com.gameserver.slot.redismsg;

import com.gameserver.common.Globals;
import com.gameserver.redis.IRedisMessage;

public class EnterSlotRoom implements IRedisMessage {
	
	private long passportId;
	
	private int newSlotId;
	
	private String newRoomId;
	
	private int oldSlotId;
	
	private String oldRoomId;
	
	private String serverid;
	
	
	public long getPassportId() {
		return passportId;
	}



	public void setPassportId(long passportId) {
		this.passportId = passportId;
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



	public int getOldSlotId() {
		return oldSlotId;
	}



	public void setOldSlotId(int oldSlotId) {
		this.oldSlotId = oldSlotId;
	}



	public String getOldRoomId() {
		return oldRoomId;
	}



	public void setOldRoomId(String oldRoomId) {
		this.oldRoomId = oldRoomId;
	}



	public String getServerid() {
		return serverid;
	}



	public void setServerid(String serverid) {
		this.serverid = serverid;
	}



	@Override
	public void execute() {
		String str = Globals.getSlotRoomService().enterSlotForRoomId(passportId, newSlotId, oldSlotId, newRoomId, oldRoomId);
		EnterSlotRoomReturn message = new EnterSlotRoomReturn();
		message.setPassportId(passportId);
		message.setStr(str);
		message.setNewRoomId(newRoomId);
		message.setNewSlotId(newSlotId);
		Globals.getRedisService().sendRedisMsgToServer(serverid, message);

	}

}
