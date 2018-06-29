package com.core.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建线程池
 * @author Thinker
 * 
 */
public class ThreadPool
{
	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(ThreadPool.class);
	/** 核心线程个数 */
	private int coreSize;
	/** 最多线程个数 */
	private int maxSize;
	/** 空闲线程保留时间 */
	private int keepAliveTime;
	/** 堵塞队列 */
	private BlockingQueue<Runnable> workQueue;
	/** 被拒绝任务处理策略 */
	private RejectedExecutionHandler handler;
	/** 线程池 */
	private ThreadPoolExecutor threadPool;

	/**
	 * 初始化
	 * 
	 * @param coreSize
	 * @param maxSize
	 * @param keepAliveTime
	 * @param workQueue
	 * @param workQueueCapacity
	 * @param handler
	 */
	public ThreadPool(int coreSize, int maxSize, int keepAliveTime, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		if (coreSize < 0 || keepAliveTime < 0 || coreSize > maxSize || maxSize <= 0 || workQueue == null || handler == null)
		{
			throw new IllegalArgumentException("All parameters must accurate.");
		}
		this.coreSize = coreSize;
		this.maxSize = maxSize;
		this.keepAliveTime = keepAliveTime;
		this.workQueue = workQueue;
		this.handler = handler;
	}

	/**
	 * 创建线程池
	 * 
	 * @return
	 */
	public boolean start()
	{
		// 创建线程池
		try
		{
			if (logger.isInfoEnabled()) 
			{
				logger.info("Start create a threedPool with parameters like [ coreSize : " + this.coreSize + " maxSize : " + this.maxSize + " keepAliveTime : " + this.keepAliveTime);
			}
			threadPool = new ThreadPoolExecutor(coreSize, maxSize, keepAliveTime, TimeUnit.SECONDS, workQueue, handler);
			if (logger.isInfoEnabled())
			{
				logger.info("Create success.");
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 关闭线程池
	 * 
	 */
	public void stop() 
	{
		if (isStart()) 
		{
			ExecutorUtil.shutdownAndAwaitTermination(threadPool);
		}
		threadPool = null;
	}

	/**
	 * 判断是否打开线程池
	 * 
	 * @return
	 */
	public boolean isStart()
	{
		if (threadPool != null && !threadPool.isShutdown())
		{
			return true;
		}
		return false;
	}

	public int getCoreSize()
	{
		return coreSize;
	}

	public int getMaxSize() 
	{
		return maxSize;
	}

	public int getKeepAliveTime()
	{
		return keepAliveTime;
	}

	/**
	 * 添加任务
	 * 
	 * @param runnable
	 */
	public void addTask(Runnable task)
	{
		this.threadPool.execute(task);
	}

}
