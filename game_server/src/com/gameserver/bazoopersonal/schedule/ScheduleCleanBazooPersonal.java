package com.gameserver.bazoopersonal.schedule;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.gameserver.common.Globals;

public class ScheduleCleanBazooPersonal implements LLISchedule{
	private Logger logger = Loggers.BAZOO;

	private int dateType;
	public ScheduleCleanBazooPersonal(int dateType)
	{
		this.dateType=dateType;
	}


	@Override
	public void execute() {
		logger.info("[无双吹牛-个人信息]---[清理数据]---[dateType::"+dateType+"]");
		Globals.getHumanBankService().cleanBazooRank(dateType);
	}
}
