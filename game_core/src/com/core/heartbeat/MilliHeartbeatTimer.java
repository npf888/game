package com.core.heartbeat;

import com.core.time.TimeService;

/**
 * 精确到毫秒的心跳计时器
 * @author Thinker
 */
public class MilliHeartbeatTimer implements HeartbeatTimer
{
	/** 计时时间 */
	private final long milliTimeSpan;
	/** 到时时间 */
	private long timeUp;
	private static TimeService timeService;

	public static void setTimeService(TimeService timeService)
	{
		MilliHeartbeatTimer.timeService = timeService;
	}

	MilliHeartbeatTimer(long milliTimeSpan) 
	{
		this.milliTimeSpan = milliTimeSpan;
		this.timeUp = timeService.now();
	}

	@Override
	public boolean isTimeUp() 
	{
		return timeService.timeUp(timeUp);
	}

	@Override
	public void nextRound() 
	{
		this.timeUp = timeService.now() + milliTimeSpan;
	}
}
