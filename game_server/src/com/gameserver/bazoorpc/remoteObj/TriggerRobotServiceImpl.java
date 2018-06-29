package com.gameserver.bazoorpc.remoteObj;

import org.slf4j.Logger;

import com.common.constants.Loggers;

public class TriggerRobotServiceImpl implements TriggerRobotService{

	private static final Logger logger = Loggers.gameLogger;
	@Override
	public void startRobot(String roomNumber, int number) {
		logger.info("[动态代理]---[开启机器人]---[房间："+roomNumber+"][人数:"+number+"]");
	}
	
	
	
}
