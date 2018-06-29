package com.gameserver.conversioncode.redisMsg;

import com.gameserver.common.Globals;
import com.gameserver.redis.IRedisMessage;

public class AddCodeMessage implements IRedisMessage {

	private String code;
	
	private long endTime;
	
	private long gold;
	
	
	
	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public long getEndTime() {
		return endTime;
	}



	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}



	public long getGold() {
		return gold;
	}



	public void setGold(long gold) {
		this.gold = gold;
	}



	@Override
	public void execute() {
         Globals.getConversioncodeService().putDate(code, gold, endTime);
	}

}
