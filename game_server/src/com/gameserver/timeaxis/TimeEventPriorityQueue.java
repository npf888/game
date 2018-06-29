package com.gameserver.timeaxis;

import java.util.Iterator;
import java.util.PriorityQueue;

import com.gameserver.timeaxis.timeevent.TimeEventType;

/**
 * 用优先队列实现的事件时间队列
 * @author baoliang.shen
 *
 */
public class TimeEventPriorityQueue extends PriorityQueue<TimeElement> implements TimeEventQueue{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2370914880037685345L;

	@Override
	public boolean isEmpty() {
		return super.isEmpty();
	}

	@Override
	public TimeElement peek() {
		return super.peek();
	}

	@Override
	public TimeElement poll() {
		return super.poll();
	}

	@Override
	public boolean add(TimeElement e) {
		return super.add(e);
	}

	@Override
	public void del(TimeEventType timeEventType) {
		for (Iterator<TimeElement> iterator = this.iterator(); iterator.hasNext();) {
			TimeElement timeElement = iterator.next();
			if (timeElement!=null 
					&& timeElement.timeEvent!=null
					&& timeElement.timeEvent.getType()==timeEventType) {
				timeElement.cancelEvent();
			}
		}
	}

	@Override
	public void del(TimeEventType timeEventType, int id) {
		for (Iterator<TimeElement> iterator = this.iterator(); iterator.hasNext();) {
			TimeElement timeElement = iterator.next();
			if (timeElement!=null 
					&& timeElement.timeEvent!=null
					&& timeElement.timeEvent.getType()==timeEventType
					&& timeElement.timeEvent.getId()==id) {
				timeElement.cancelEvent();
			}
		}
	}
}
