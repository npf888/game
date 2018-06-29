package com.gameserver.timeaxis;

import com.core.heartbeat.HeartBeatAble;
import com.core.time.TimeService;
import com.gameserver.timeaxis.timeevent.TimeEvent;
import com.gameserver.timeaxis.timeevent.TimeEventType;


/**
 * 时间轴
 * @author baoliang.shen
 *
 */
public class TimeAxis implements HeartBeatAble{
	/** 时间事件队列*/
	private volatile TimeEventQueue	timeEventQueue;
	/** 时间服务*/
	private TimeService		timeService;
	/** 时间轴的宿主*/
	private TimeAxisHost	parent;
	
	public TimeAxis( TimeService timeService, TimeAxisHost parent ) {
		this.timeService = timeService;
		this.parent = parent;
		timeEventQueue = new TimeEventPriorityQueue();
	}

	@Override
	public void heartBeat() {
		if(timeEventQueue.isEmpty())
			return;
		
		long now = timeService.now();
		while(!timeEventQueue.isEmpty()) {
			TimeElement tmpElement = timeEventQueue.peek();
			if(tmpElement==null){
				timeEventQueue.poll();
				continue;
			}
			if(tmpElement.timeEvent==null){
				timeEventQueue.poll();
				continue;
			}
			
			if(tmpElement.nTime <= now){
				timeEventQueue.poll();
				if (tmpElement.period>0) {
					this.addOnThisTime(tmpElement.timeEvent, tmpElement.nTime+tmpElement.period, tmpElement.period);
				}
				tmpElement.timeEvent.doEvent(parent);
			}
			else
				break;
		}
	}
	
	/**
	 * 将指定元素插入此队列
	 * @param tEvent
	 * @param nTime	在未来的这个时间
	 * @return	是否成功
	 */
	public boolean addOnThisTime(TimeEvent tEvent, long nTime )
	{
		return addOnThisTime(tEvent,nTime,-1);
	}
	
	/**
	 * 将指定元素插入此队列
	 * @param tEvent
	 * @param nTime	在未来的这个时间
	 * @param period	循环执行的间隔，不循环请填-1
	 * @return	是否成功
	 */
	public boolean addOnThisTime(TimeEvent tEvent, long nTime, long period )
	{
		if(tEvent==null)
			return false;
		
		TimeElement tElement = new TimeElement();
		tElement.nTime = nTime;
		tElement.period = period;
		tElement.timeEvent = tEvent;

		return timeEventQueue.add(tElement);
	}
	
	/**
	 * 将指定元素插入此队列
	 * @param tEvent
	 * @param nMS	在这么多毫秒之后
	 * @return	是否成功
	 */
	public boolean addAfterMS(TimeEvent tEvent, long nMS )
	{
		return addAfterMS(tEvent,nMS,-1);
	}
	
	/**
	 * 将指定元素插入此队列
	 * @param tEvent
	 * @param nMS	在这么多毫秒之后
	 * @param period	循环执行的间隔，不循环请填-1
	 * @return	是否成功
	 */
	public boolean addAfterMS(TimeEvent tEvent, long nMS, long period )
	{
		if(tEvent==null)
			return false;
		if(nMS<0)
			return false;
		
		long now = timeService.now();
		TimeElement tElement = new TimeElement();
		tElement.nTime = now+nMS;
		tElement.period = period;
		tElement.timeEvent = tEvent;
		
		return timeEventQueue.add(tElement);
	}

	/**
	 * 根据事件类型和ID删除事件，这里的ID是用户生成事件时自己设定的
	 * 原因：采用这种方式，是因为如果时间轴自己产生和维护ID的话，
	 * 		虽然能保证现存的事件ID不重复，但不能保证已经删除的事件用过的ID不被重用，而外部如果有地方记住了这个ID的话，
	 * 		用这个ID删除事件，会导致误删除
	 * @param timeEventType
	 * @param id
	 * @return
	 */
	public void del(TimeEventType timeEventType,int id){
		timeEventQueue.del(timeEventType,id);
	}
	
	/**
	 * 根据事件类型删除事件
	 * @param timeEventType
	 * @return
	 */
	public void del(TimeEventType timeEventType){
		timeEventQueue.del(timeEventType);
	}
}
