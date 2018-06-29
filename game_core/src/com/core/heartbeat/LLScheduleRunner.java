package com.core.heartbeat;


import com.common.constants.Loggers;
import com.core.schedule.LLScheduleService;

/**
 * 定时任务运行器：使用必须注册到心跳线程中
 * @author Thinker
 *
 */
public class LLScheduleRunner extends LLAbstractRunner
{
	/** 定时任务管理器 */
	private final LLScheduleService scheduleService;
	public LLScheduleRunner(LLScheduleService scheduleService)
	{
		this.scheduleService=scheduleService;
	}
	
	@Override
	public Integer call() throws Exception
	{
		try
		{
			if(scheduleService!=null)
				scheduleService.heartBeat();
		} catch (Throwable e)
		{
			Loggers.gameLogger.error("定时任务运行器异常", e);
		}
		return LLHeartbeatEnum.SCHEDULE.getIndex();
	}
	@Override
	public int getId()
	{
		return LLHeartbeatEnum.SCHEDULE.getIndex();
	}
}
