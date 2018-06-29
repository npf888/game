package com.gameserver.bazoo.schedule;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

/**
 * 无双吹牛 
 * 最终结算
 * @author JavaServer
 *
 */
public class ClassicalScheduleEndCountOnce implements LLISchedule{
	private Logger logger = Loggers.BAZOO;

	private RoomNumber roomNumber;
	public ClassicalScheduleEndCountOnce(RoomNumber roomNumber)
	{
		this.roomNumber=roomNumber;
	}


	@Override
	public void execute() {
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[结算等待Schedule]-------------------------]");
		Globals.getBazooMService().endCount(roomNumber);
	}

}
