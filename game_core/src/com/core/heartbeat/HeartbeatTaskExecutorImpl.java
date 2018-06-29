package com.core.heartbeat;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * HeartbeatTaskExecutor默认实现
 * @author Thinker
 */
public class HeartbeatTaskExecutorImpl implements HeartbeatTaskExecutor 
{

	private LinkedBlockingQueue<TaskTimerPair> taskPairs;

	public HeartbeatTaskExecutorImpl()
	{
		taskPairs = new LinkedBlockingQueue<TaskTimerPair>();
	}

	@Override
	public void onHeartBeat()
	{
		for (TaskTimerPair taskTimerPair : taskPairs)
		{
			if (taskTimerPair.timer.isTimeUp())
			{
				taskTimerPair.task.run();
				taskTimerPair.timer.nextRound();
			}
		}
	}

	@Override
	public void submit(HeartbeatTask task)
	{
		TaskTimerPair taskTimerPair = new TaskTimerPair(task,new MilliHeartbeatTimer(task.getRunTimeSpan()));
		taskPairs.add(taskTimerPair);
	}

	@Override
	public void clear()
	{
		taskPairs.clear();
	}

	private static class TaskTimerPair 
	{
		public HeartbeatTask task;
		public HeartbeatTimer timer;

		private TaskTimerPair(HeartbeatTask task, HeartbeatTimer timer) 
		{
			super();
			this.task = task;
			this.timer = timer;
		}
	}
}
