package com.gameserver.compensation.handler;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.common.Globals;
import com.gameserver.compensation.Compensation;
import com.gameserver.compensation.data.CompensationData;
import com.gameserver.compensation.redisMsg.CompensationRedisMessage;
import com.gameserver.compensation.redisMsg.DeleteCompensationRedisMessage;

/**
 * 补偿redis message 处理器
 * @author wayne
 *
 */
public class CompensationRedisMessageHandler {
	private Logger logger = Loggers.compensationLogger;
	
	/**
	 * 更新补偿
	 * @param compensationRedisMessage
	 */
	public void handleCompensationRedisMessage(
			CompensationRedisMessage compensationRedisMessage) {
		// TODO Auto-generated method stub
		Compensation compensation = new Compensation();
		compensation.fromEntity( CompensationData.convertFromCompensationData(compensationRedisMessage.getCompensationData()));
		Globals.getCompensationService().addCompensation(compensation);
		logger.info("接收补偿 补偿id["+compensation.getDbId()+"]");
	}

	/**
	 * 删除补偿
	 * @param deleteCompensationRedisMessage
	 */
	public void handleDeleteCompensationRedisMessage(
			DeleteCompensationRedisMessage deleteCompensationRedisMessage) {
		// TODO Auto-generated method stub
		Compensation compensation = Globals.getCompensationService().getCompensationById(deleteCompensationRedisMessage.getId());
		if(compensation == null){
			logger.info("补偿id["+deleteCompensationRedisMessage.getId()+"]已经不存在");
			return;
		}
		Globals.getCompensationService().removeCompensation(compensation);
		logger.info("移除补偿id["+compensation.getDbId()+"]");
	}

}
