package com.gameserver.worldboss.schedule;

import java.util.Calendar;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.core.schedule.LLScheduleEnum;
import com.core.util.TimeUtils;
import com.gameserver.common.Globals;

public class ScheduleBossNoticeBefore implements LLISchedule {
	private Logger logger = Loggers.WORLDBOSS;
	Calendar calendarRemind = Calendar.getInstance();
	String hour = null;
	String min = null;
	public ScheduleBossNoticeBefore(Calendar calendarRemind,String hour,String min){
		this.calendarRemind.setTime(calendarRemind.getTime());
		this.hour=hour;
		this.min=min;
	}
	@Override
	public void execute() {
		
		
		Globals.getWorldBossNewService().bossBeforeStart();
		//设置 下一次 提醒个定时器
		calendarRemind.add(Calendar.DATE, 1);
		calendarRemind.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
		calendarRemind.set(Calendar.MINUTE,Integer.valueOf(min).intValue());
		calendarRemind.set(Calendar.SECOND, 0);
		
		logger.info("++++++比赛前 30 分钟 - 下一次 - 通知时间 设置："+TimeUtils.dateToTimeString(calendarRemind.getTime()));
		//跑马灯
		Globals.getScheduleService().scheduleOnce(new ScheduleBossNoticeBefore(calendarRemind,hour,min), LLScheduleEnum.WORLD_BOSS_NOTICE_BEFORE,calendarRemind.getTime());
		
	}
}
