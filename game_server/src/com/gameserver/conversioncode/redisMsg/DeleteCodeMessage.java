package com.gameserver.conversioncode.redisMsg;

import com.gameserver.common.Globals;
import com.gameserver.redis.IRedisMessage;

public class DeleteCodeMessage implements IRedisMessage {

	private String code;
	
	private int state;
	
	
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}


	@Override
	public void execute() {
		
      Globals.getConversioncodeService().changeStata(code, state);
	}

}
