package com.gameserver.timeaxis;

import com.gameserver.timeaxis.timeevent.TimeEventType;

/**
 * 时间事件队列，需保证队列头为时间最靠前的，即最小的
 * @author baoliang.shen
 *
 */
public interface TimeEventQueue {

	/**
	 * 容器是否为空
	 * @return
	 */
	public boolean isEmpty();
	
	/**
	 * 获取但不移除队列的头；如果此队列为空，则返回null
	 * @return
	 */
	public TimeElement peek();
	
	/**
	 * 获取并移除此队列的头，如果此队列为空，则返回null
	 * @return
	 */
	public TimeElement poll();
	
	/**
	 * 插入到队列中合适的位置
	 * @param tElement
	 * @return 是否插入成功
	 */
	public boolean add(TimeElement tElement);

	/**
	 * 删除事件
	 * @param timeEventType 事件类型
	 * @return
	 */
	public void del(TimeEventType timeEventType);

	/**
	 * 删除事件
	 * @param timeEventType 事件类型
	 * @param id 事件ID
	 * @return
	 */
	public void del(TimeEventType timeEventType, int id);
}
