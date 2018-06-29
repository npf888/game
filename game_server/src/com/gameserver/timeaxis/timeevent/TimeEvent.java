package com.gameserver.timeaxis.timeevent;

import com.gameserver.timeaxis.TimeAxisHost;

/**
 * 事件时间接口
 * @author baoliang.shen
 *
 */
public interface TimeEvent {
	
	/**
	 * 事件被取消时触发
	 */
	public void cancel();

	/**
	 * 执行事件
	 * @param parent	宿主
	 */
	public void doEvent(TimeAxisHost parent);
	
	/**
	 * 一个事件类对应一个事件类型，用于事件删除，如果该事件不需要删除，请返回null
	 * @return	事件类型
	 */
	public TimeEventType getType();
	
	/**
	 * 取由用户定义的事件ID，用于事件删除
	 * 对于不同的事件，ID含义不一样，多数事件不需要设置ID
	 * @return 用不上的时候返回0即可
	 */
	public int getId();
}
