package com.gameserver.bazoo.schedule;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.common.Globals;
/**
 * 开始到重摇 之间还有一段时间
 * @author JavaServer
 *
 */
public class CowScheduleBeginToSwing implements LLISchedule{
	private Logger logger = Loggers.BAZOO;

	private Room room;
	public CowScheduleBeginToSwing(Room room)
	{
		this.room=room;
	}


	@Override
	public void execute() {
		logger.info("[无双吹牛"+room.getRoomNumber().toString()+"]---["+room.getRoomNumber().getModeName()+"]---[开始到重摇的 开始]---");
		Globals.getBazooCowService().beginToSwing(room);
	}

}
