package com.gameserver.timeaxis;

import com.gameserver.timeaxis.timeevent.TimeEvent;

/**
 * 事件时间的包装类
 * @author baoliang.shen
 *
 */
public class TimeElement implements Comparable<TimeElement>{
	/** 何时触发*/
	long nTime;
	/** 循环执行的间隔，-1表示不循环 */
	long period = -1;
	/** 事件*/
	TimeEvent timeEvent;
	
	public void cancelEvent()
	{
		timeEvent.cancel();
		timeEvent = null;
	}

	@Override
	public int compareTo(TimeElement o) {
		if(nTime < o.nTime)
			return -1;
		else if(nTime > o.nTime)
			return 1;
		else
			return 0;
	}
}
