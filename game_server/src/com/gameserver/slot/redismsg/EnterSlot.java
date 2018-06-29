package com.gameserver.slot.redismsg;

import com.gameserver.common.Globals;
import com.gameserver.redis.IRedisMessage;
/**
 * 进入老虎机
 * @author 郭君伟
 *
 */
public class EnterSlot implements IRedisMessage {
	
	private long passportId;
	
	private int oldslotId;
	
	private int newslotId;
	
	private String roomId;
	
	private String serverid;
	
	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	public int getOldslotId() {
		return oldslotId;
	}

	public void setOldslotId(int oldslotId) {
		this.oldslotId = oldslotId;
	}

	public int getNewslotId() {
		return newslotId;
	}

	public void setNewslotId(int newslotId) {
		this.newslotId = newslotId;
	}

	public String getRoomId() {
		return roomId;
	}



	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}


	

	public String getServerid() {
		return serverid;
	}

	public void setServerid(String serverid) {
		this.serverid = serverid;
	}

	@Override
	public void execute() {
		
       Globals.getSlotRoomService().enterSlotRedis(passportId, oldslotId,newslotId, roomId,serverid);
	}

}
