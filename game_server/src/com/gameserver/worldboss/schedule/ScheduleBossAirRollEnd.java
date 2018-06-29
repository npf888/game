package com.gameserver.worldboss.schedule;

import com.core.schedule.LLISchedule;
import com.gameserver.common.Globals;

public class ScheduleBossAirRollEnd implements LLISchedule {

	@Override
	public void execute() {
		//开启
		Globals.getWorldBossNewService().bossBloodReturningWithAirtimeEND();
		
	}
}
