package com.gameserver.bazoo.schedule;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.common.Globals;
/**
 * 重摇结束到结算之前  
 * @author JavaServer
 *
 */
public class ShowHandScheduleWhoTurnSwing implements LLISchedule{
	private Logger logger = Loggers.BAZOO;

	private Room room;
	public ShowHandScheduleWhoTurnSwing(Room room)
	{
		this.room=room;
	}


	@Override
	public void execute() {
		logger.info("[无双吹牛"+room.getRoomNumber().toString()+"]---["+room.getRoomNumber().getModeName()+"]---[开始到重摇的 开始]---");
		Globals.getBazooShowHandService().whoTurnSwing(room);
	}

}
