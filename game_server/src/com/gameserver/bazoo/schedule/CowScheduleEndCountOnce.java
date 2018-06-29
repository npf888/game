package com.gameserver.bazoo.schedule;

import com.core.schedule.LLISchedule;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.common.Globals;

public class CowScheduleEndCountOnce implements LLISchedule{


	private Room room;
	public CowScheduleEndCountOnce(Room room)
	{
		this.room=room;
	}


	@Override
	public void execute() {
		Globals.getBazooCowService().endCount(room);
	}

}
