package com.gameserver.worldboss.schedule;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.gameserver.common.Globals;
/**
 * 世界boss 结束
 * @author JavaServer
 *
 */
public class ScheduleBossEnd implements LLISchedule {
	private Logger logger = Loggers.WORLDBOSS;
	@Override
	public void execute() {
		logger.info("++++++游戏-结束  啦啦啦。。。 。。。");
		/**
		 * boss时间到 结束
		 */
        Globals.getWorldBossNewService().endBoss();
	}

}
