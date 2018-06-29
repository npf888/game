package com.core.heartbeat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.common.constants.SharedConstants;
import com.core.util.ExecutorUtil;

/**
 * 游戏心跳线程：后台守护进程
 * 心跳任务可以注册到心跳线程中
 * 其中任务是依次完成checkUndoneTasks存在阻塞
 * @author Thinker
 *
 */
public class LLHeartbeatThread extends Thread
{
	private static final Logger logger = Loggers.gameLogger;
	/** 线程池 */
	private final ExecutorService pool;
	/** 是否繁忙 */
	private volatile boolean isBusy;
	
	/** 心跳任务列表 */
	private List<Future<Integer>> heartTasks;
	
	/** 心跳周期内还在执行中没有完成的任务ID */
	private Set<Integer> undoneTasks;
	
	/** 是否处于活动状态,默认为true，shutdown后变为false */
	private volatile boolean isLive = true;
	/**心跳List*/
	private List<LLAbstractRunner> heartLists;
	
	/**
	 * 构建心跳线程
	 * 
	 */
	public LLHeartbeatThread()
	{
		pool = Executors.newSingleThreadExecutor();
		heartTasks = new ArrayList<Future<Integer>>(20);
		undoneTasks = new HashSet<Integer>();
		heartLists =new ArrayList<LLAbstractRunner>();
	}
	/**
	 * 增加心跳运行器
	 * @param heartRunner
	 */
	public boolean AddHeartbeatRunner(LLAbstractRunner heartRunner)
	{
		if(heartRunner==null) return false;
		//判断类型是否存在
		LLHeartbeatEnum heartbeatEnum=LLHeartbeatEnum.valueOf(heartRunner.getId());
		if(heartbeatEnum==null) return false;		
		if(heartLists.contains(heartRunner)) return false;
		heartLists.add(heartRunner);
		if(logger.isInfoEnabled())
			logger.info("heartbeatEnum:" + heartbeatEnum.getNameKey()+" heartRunner:"+heartRunner);
		return true;
	}
	@Override
	public void run()
	{	
		LLAbstractRunner heartRunner =null;
		try 
		{
			while (isLive)
			{
				heartTasks.clear();
				for (int index=0; index < heartLists.size(); index++)
				{
					heartRunner=heartLists.get(index);
					if(heartRunner==null)continue;
					if(!undoneTasks.contains(heartRunner.getId()))
					{
						heartTasks.add(pool.submit(heartRunner));
					}
				}
				//心跳睡眠
				sleep(SharedConstants.TASK_HEART_BEAT_INTERVAL);
				checkUndoneTasks();
				if (undoneTasks.isEmpty())
				{
					isBusy = false;					
				} else
				{
					isBusy = true;
					if (undoneTasks != null)
					{
						for (int taskId : undoneTasks)
						{
							logger.error("Heartbeat:" + LLHeartbeatEnum.valueOf(taskId).getNameKey() + " is busy");
						}
					}
				}
			}
		} catch (Exception e)
		{
			logger.error("", e);
			shutdown();
		}
	}
	
	/**
	 * 检查每个任务的状态，重新构造未完成的任务列表
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private void checkUndoneTasks() throws InterruptedException,
			ExecutionException
	{
		undoneTasks.clear();
		for (int i = 0; i < heartTasks.size(); i++)
		{
			Future<Integer> future = heartTasks.get(i);
			if (!future.isDone())
			{
				undoneTasks.add(future.get());
			}
		}
	}

	/**
	 * 调度器是否繁忙
	 * 
	 * @return
	 */
	public boolean isBusy()
	{
		return isBusy;
	}
	
	/**
	 * 关闭心跳线程
	 */
	public void shutdown()
	{
		//关闭心跳线程，不再向线程池中提交新的任务
		this.isLive = false;
		//等待5分钟，尽量保证已提交的任务都tick完，再关闭线程池
		ExecutorUtil.shutdownAndAwaitTermination(this.pool);
	}
}
