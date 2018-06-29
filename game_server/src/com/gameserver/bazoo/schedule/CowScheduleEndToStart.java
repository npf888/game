package com.gameserver.bazoo.schedule;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.common.Globals;

public class CowScheduleEndToStart implements LLISchedule{
	private Logger logger = Loggers.BAZOO;

	private RoomNumber roomNumber;
	public CowScheduleEndToStart(RoomNumber roomNumber)
	{
		this.roomNumber=roomNumber;
	}


	@Override
	public void execute() {
		logger.info("[无双吹牛"+roomNumber.toString()+"]---["+roomNumber.getModeName()+"]---[结尾 到 开始-执行-新一轮]---");
		Globals.getBazooPubService().endToStart(roomNumber,roomNumber.getModeName());
	}

}
