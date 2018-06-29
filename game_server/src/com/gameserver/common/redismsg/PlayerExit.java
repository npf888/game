package com.gameserver.common.redismsg;

import com.gameserver.redis.IRedisMessage;

/**
 * 广播玩家推线
 * @author 郭君伟
 *
 */
public class PlayerExit implements IRedisMessage {

	private long passportId;
	
	
	
	
	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}




	@Override
	public void execute() {
	       

	}

}
