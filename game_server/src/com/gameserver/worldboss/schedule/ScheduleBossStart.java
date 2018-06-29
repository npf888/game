package com.gameserver.worldboss.schedule;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.core.schedule.LLScheduleEnum;
import com.core.util.TimeUtils;
import com.gameserver.common.Globals;
import com.gameserver.worldboss.template.BossTemplate;

/**
 * 开启世界boss
 * @author JavaServer
 *
 */
public class ScheduleBossStart implements LLISchedule {
	private Logger logger = Loggers.WORLDBOSS;
	private BossTemplate bossTemplate;
	Calendar calendar = null;
	String hour = null;
	String min = null;
	
	public ScheduleBossStart(BossTemplate bossTemplate,Calendar calendar,String hour,String min){
		this.bossTemplate=bossTemplate;
		this.calendar=calendar;
		this.hour=hour;
		this.min=min;
	}

	@Override
	public void execute() {
		logger.info("++++++游戏-开始  啦啦啦... ...");
		/**
		 * 下一次 定时任务
		 */
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
		calendar.set(Calendar.MINUTE,Integer.valueOf(min));
		calendar.set(Calendar.SECOND, 0);
		logger.info("++++++下一次 - 游戏开始时间设置-："+TimeUtils.dateToTimeString(calendar.getTime()));
		//开始游戏
		Globals.getScheduleService().scheduleOnce(new ScheduleBossStart(bossTemplate,calendar,hour,min), LLScheduleEnum.WORLD_BOSS_START,calendar.getTime());
		
		
		
		/**
		 * 开启
		 */
		Globals.getWorldBossNewService().startWorldBoss(bossTemplate);
		//技能触发时间  在boss 活动期间 每隔多长时间 触发一次技能
		int second = bossTemplate.getSkillstart();
		//boss持续时间
		int min = bossTemplate.getContinuetime();
		//在boss运行时间 内 每个多少时间 触发一次技能
		//在min 这段时间内 要每隔 second 秒 触发 技能
		Calendar calendarRollSkill = Calendar.getInstance();
		calendarRollSkill.setTime(new Date());
		calendarRollSkill.add(Calendar.SECOND, second);
		/**
		 * 在这个 skill时间段内执行 多少次 技能,到达次数后就结束
		 */
		int times = min*60/second;
		int howManyTimes = 1;
		logger.info("++++++游戏-第一次 ---技能--- 开始时间  ："+TimeUtils.dateToTimeString(calendarRollSkill.getTime()));
		Globals.getScheduleService().scheduleOnce(new ScheduleBossSkillstart(calendarRollSkill,second,times,howManyTimes), LLScheduleEnum.WORLD_BOSS_SKILL_START,calendarRollSkill.getTime());
		
		//结束世界boss
		logger.info("++++++游戏-结束  时间："+min*60*1000);
		Globals.getScheduleService().scheduleOnce(new ScheduleBossEnd(), LLScheduleEnum.WORLD_BOSS_END, min*60*1000);
        
	}
}
