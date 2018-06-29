package com.gameserver.worldboss.schedule;

import java.util.Calendar;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.core.schedule.LLScheduleEnum;
import com.core.util.TimeUtils;
import com.gameserver.common.Globals;

/**
 * 每隔多长时间  触发一次技能
 * @author JavaServer
 *
 */
public class ScheduleBossSkillstart implements LLISchedule {
	private Logger logger = Loggers.WORLDBOSS;
	Calendar calendarRollSkill = null;
	int second = 0;
	int times = 0;
	int howManyTimes = 0;
	public ScheduleBossSkillstart(Calendar calendarRollSkill, int second, int times, int howManyTimes){
		this.calendarRollSkill=calendarRollSkill;
		this.second=second;
		this.times=times;
		this.howManyTimes=howManyTimes;
	}
	
	@Override
	public void execute() {
		/**
		 * 主要处理 boss 回血的 功能
		 */
		Loggers.WORLDBOSS.info("开始 回血------------------execute");
		Globals.getWorldBossNewService().bossBloodReturning(calendarRollSkill);
        
		/**
		 * 下一次触发
		 */
		calendarRollSkill.add(Calendar.SECOND, second);
		howManyTimes+=1;
		if(howManyTimes > times){
			return;
		}
		logger.info("++++++游戏-技能 --- 下一次 --- 时间设置通知时间 设置："+TimeUtils.dateToTimeString(calendarRollSkill.getTime()));
		Globals.getScheduleService().scheduleOnce(new ScheduleBossSkillstart(calendarRollSkill,second,times,howManyTimes), LLScheduleEnum.WORLD_BOSS_SKILL_START ,calendarRollSkill.getTime());
	}
}
