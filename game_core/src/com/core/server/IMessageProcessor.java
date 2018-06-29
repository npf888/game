package com.core.server;

import com.core.msg.IMessage;

/**
 * 消息处理器
 * @author Thinker
 *
 */
public interface IMessageProcessor 
{
	/**
	 * 启动消息处理器
	 * 
	 */
	public void start();

	/**
	 * 停止消息处理器
	 * 
	 */
	public void stop();


	/**
	 * 向消息队列投递消息
	 * 
	 * @param msg
	 */
	public void put(IMessage msg);

	/**
	 * 判断队列是否已经达到上限了
	 * 
	 * @return
	 * 
	 */
	public boolean isFull();
}