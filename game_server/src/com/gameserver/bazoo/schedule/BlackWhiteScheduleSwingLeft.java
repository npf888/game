package com.gameserver.bazoo.schedule;

import com.core.schedule.LLISchedule;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.common.Globals;

public class BlackWhiteScheduleSwingLeft implements LLISchedule{


	private Room room;
	public BlackWhiteScheduleSwingLeft(Room room)
	{
		this.room=room;
	}


	@Override
	public void execute() {
		Globals.getBlackWhiteService().swingLeft(room);
	}

}
