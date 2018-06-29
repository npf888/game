package com.core.heartbeat;


/**
 * 心跳任务执行器
 * @author Thinker
 */
public interface HeartbeatTaskExecutor extends HeartBeatListener 
{
	/**
	 * 提交一个任务
	 * 
	 * @param task
	 */
	void submit(HeartbeatTask task);

	/**
	 * 清除所有任务
	 */
	void clear();
}
