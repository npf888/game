package com.gameserver.bazoorank.schedule;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.gameserver.common.Globals;

public class ScheduleCleanBazooRank implements LLISchedule{
	private Logger logger = Loggers.BAZOO;

	private int dateType;
	public ScheduleCleanBazooRank(int dateType)
	{
		this.dateType=dateType;
	}


	@Override
	public void execute() {
		logger.info("[无双吹牛-排行榜]---[清理数据-排行榜和个人信息]---[dateType::"+dateType+"]");
		Globals.getHumanBazooPersonalService().cleanBazooPersonal(dateType);
		Globals.getHumanBankService().cleanBazooRank(dateType);
	}

}
