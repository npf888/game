package com.core.heartbeat;


import com.common.constants.Loggers;
import com.core.time.TimeService;

/**
 * 时间同步运行器：使用必须注册到心跳线程中
 * @author Thinker
 *
 */
public class LLTimeRunner extends LLAbstractRunner
{
	/** 时间服务 */
	private final TimeService timeService;
	public LLTimeRunner(TimeService timeService)
	{
		this.timeService=timeService;
	}
	@Override
	public Integer call() throws Exception
	{
		try
		{
			if(timeService!=null)
				timeService.heartBeat();
		} catch (Throwable e)
		{
			Loggers.gameLogger.error("时间同步运行器异常", e);
		}
		return LLHeartbeatEnum.TIMEASYNC.getIndex();
	}
	@Override
	public int getId()
	{
		return LLHeartbeatEnum.TIMEASYNC.getIndex();
	}
}
