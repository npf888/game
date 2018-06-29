package com.gameserver.compensation.redisMsg;


import com.gameserver.compensation.data.CompensationData;
import com.gameserver.compensation.handler.CompensationHandlerFactory;
import com.gameserver.redis.IRedisMessage;

/**
 * 补偿redis
 * @author wayne
 *
 */
public class CompensationRedisMessage implements IRedisMessage{
	private CompensationData compensationData;

	public CompensationData getCompensationData() {
		return compensationData;
	}

	public void setCompensationData(CompensationData compensationData) {
		this.compensationData = compensationData;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		CompensationHandlerFactory.getRedisHandler().handleCompensationRedisMessage(this);
	}
}
