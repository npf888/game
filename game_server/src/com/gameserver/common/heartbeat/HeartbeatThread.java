package com.gameserver.common.heartbeat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.common.constants.SharedConstants;
import com.core.heartbeat.LLAbstractRunner;
import com.core.heartbeat.LLHeartbeatEnum;
import com.core.util.ExecutorUtil;
import com.gameserver.common.Globals;
import com.gameserver.scene.SceneRunner;

/**
 * 自定义心跳线程
 * @author Thinker
 * 
 */
public final class HeartbeatThread extends Thread 
{
	private static final Logger logger = Loggers.gameLogger;
	private final static int maxRpcTask = 10;
	
	/** 线程池 */
	private final ExecutorService pool;
	
	/** 是否繁忙 */
	private volatile boolean isBusy;
	
	/** 地图id */
	private List<Future<Integer>> futures;
	
	/** 还在执行中没有完成的任务对应场景id的集合 */
	private Set<Integer> undoneTasks;
	
	/** 是否处于活动状态,默认为true，shutdown后变为false */
	private volatile boolean isLive = true;
	
	/**心跳List*/
	private List<LLAbstractRunner> heartLists;
	
	/**
	 * 构建心跳线程
	 * 
	 */
	public HeartbeatThread()
	{
		pool = Executors.newSingleThreadExecutor();
		futures = new ArrayList<Future<Integer>>(20);
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
			long beginTime=0;
			while (isLive)
			{
				Globals.getTimeService().heartBeat();// 更新缓存的时间为当前系统时间
				beginTime=Globals.getTimeService().now();
				
				//rpc task
				BlockingQueue<FutureTask<?>>  rpcTaskList= Globals.getRpcService().getRpcTaskList();
				for (int i = 0; i < maxRpcTask; ++i) {
					if (rpcTaskList.isEmpty()) {
						break;
					}
					try{
						FutureTask<?> rpcTask = rpcTaskList.take();
						pool.submit(rpcTask);
					}catch(InterruptedException e)
					{
						Loggers.gameLogger.error("rpc task:error");
					}
				}
				
				futures.clear();
				for (int index=0; index < heartLists.size(); index++)
				{
					heartRunner=heartLists.get(index);
					if(heartRunner==null)continue;
					if(!undoneTasks.contains(heartRunner.getId()))
					{
						futures.add(pool.submit(heartRunner));
					}
				}
				
				futures.add(pool.submit(Globals.getGlobalLogicRunner()));
				
				List<SceneRunner> sceneRunners = Globals.getSceneService().getAllSceneRunners();
				for (int i = 0; i < sceneRunners.size(); i++)
				{
					SceneRunner runner = sceneRunners.get(i);
					if (!undoneTasks.contains(runner.getSceneId()))
					{
						futures.add(pool.submit(runner));
					}
				}
				
				if(Globals.getTimeService().now()-beginTime>50)
					logger.info("HeartbeatThread Run Time:"+(Globals.getTimeService().now()-beginTime));
				
				sleep(SharedConstants.GS_HEART_BEAT_INTERVAL);
				checkUndoneTasks();
				if (undoneTasks.isEmpty())
				{
					isBusy = false;					
				} else 
				{
					isBusy = true;
					if (undoneTasks != null) 
					{
						for (int runnerId : undoneTasks)
						{
							logger.error("runnerId:" + runnerId + " is busy");
						}
					}
				}				
			}
		} catch (Exception e)
		{
			Loggers.gameLogger.error("", e);
			shutdown();
		}
	}

	/**
	 * 检查每个场景任务的状态，重新构造未完成的任务列表
	 * 
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private void checkUndoneTasks() throws InterruptedException,ExecutionException
	{
		undoneTasks.clear();
		for (int i = 0; i < futures.size(); i++)
		{
			Future<Integer> future = futures.get(i);
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
	 * 关闭SceneTaskScheduler
	 */
	public void shutdown()
	{
		//关闭SceneTaskScheduler，不再向线程池中提交新的任务
		this.isLive = false;
		//等待5分钟，尽量保证已提交的任务都tick完，再关闭线程池
		ExecutorUtil.shutdownAndAwaitTermination(this.pool);
	}
	
	
}
