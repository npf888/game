package com.core.schedule;

import java.util.Date;



/**
 * 定时任务管理器
 * @author Thinker
 */
public interface LLScheduleService
{
	/**
	 * 定时任务心跳
	 */
	public abstract void heartBeat();
	/**
	 * 
	 * @param schedule
	 * @param scheduleEnum
	 * @param delay 延迟时间(单位:豪秒)
	 */
	public abstract void scheduleOnce(LLISchedule schedule,LLScheduleEnum scheduleEnum,long delay);

	/**
	 * 
	 * @param schedule
	 * @param scheduleEnum
	 * @param d 指定日期
	 */
	public abstract void scheduleOnce(LLISchedule schedule,LLScheduleEnum scheduleEnum,Date d);

	/**
	 * 
	 * @param schedule
	 * @param scheduleEnum
	 * @param delay 延迟时间
	 * @param period 周期时间(单位:豪秒)
	 */
	public abstract void scheduleWithFixedDelay(LLISchedule schedule,LLScheduleEnum scheduleEnum,long delay,long period);
	
	/**
	 * 输出定时任务信息
	 */
	public abstract void printScheduleInfo();
}