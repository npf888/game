package com.gameserver.slot.redismsg;

import com.gameserver.common.Globals;
import com.gameserver.redis.IRedisMessage;
/**
 * 玩家退出房间
 * @author 郭君伟
 *
 */
public class OutRoom implements IRedisMessage {

	//玩家ID
	private long passportId;
	
	//退出房间的角色ID
	private long outPassportId;
	
	
	public long getPassportId() {
		return passportId;
	}


	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}


	public long getOutPassportId() {
		return outPassportId;
	}


	public void setOutPassportId(long outPassportId) {
		this.outPassportId = outPassportId;
	}


	@Override
	public void execute() {
        Globals.getSlotRoomService().sendOutRoom(passportId, outPassportId,false);
	}

}
