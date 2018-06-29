package com.gameserver.common;


import java.util.concurrent.Callable;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.core.msg.MessageQueue;
import com.core.util.Assert;
import com.gameserver.redis.IRedisMessage;
import com.gameserver.timeaxis.TimeAxis;
import com.gameserver.timeaxis.TimeAxisHost;

/**
 * 全局逻辑业务
 * 比如公会战、Boss战、竞技场等全局性业务的刷新和心跳都应该放在这里
 * @author Thinker
 *
 */
public class GlobalLogicRunner implements Callable<Integer>, TimeAxisHost
{
	private final static int maxMsgCountProcess = 1024;
	private final static int maxRedisMsgCountProcess = 1024;
	/** 时间轴 */
	private TimeAxis timeAxis;
	/** 消息队列 */
	private MessageQueue<IMessage> msgQueue;
	/**redis消息队列*/
	private MessageQueue<IRedisMessage> redisMsgQueue;
	/** 运行的线程id * */
	private long threadId;
	/** 全局数据更新调度器 */
	private  GlobalDataUpdater globalUpdater;
	

	public long getThreadId() {
		return threadId;
	}
	
	@Override
	public Integer call() throws Exception {
		try {
			tick();
		} catch (Throwable e) {
		
			Loggers.gameLogger.error("", e);
		}
		return -1;
	}
	
	public void tick(){
		threadId = Thread.currentThread().getId();
		timeAxis.heartBeat();
		this.processMessage();
		this.processRedisMessage();
		this.globalUpdater.update();
	}


	/**
	 * 处理消息
	 */
	private void processMessage() {
		for (int i = 0; i < maxMsgCountProcess; ++i) {
			if (msgQueue.isEmpty()) {
				break;
			}
			IMessage msg = msgQueue.get();
			Assert.notNull(msg);
			long begin = System.nanoTime();
			try{
				msg.execute();
			}catch(Throwable t){
				Loggers.errorLogger.error("Global Logic process message error!", t);
			
			}finally{
				long time = (System.nanoTime() - begin) / (1000 * 1000);
				if (time > 1) {
					Loggers.errorLogger.info("Global Logic process message cost " + time +" ms!");
				}
			}
		}
	}
	
	/**
	 * 处理redis消息
	 */
	private void processRedisMessage() {
		// TODO Auto-generated method stub
		for (int i = 0; i < maxRedisMsgCountProcess; ++i) {
			if (redisMsgQueue.isEmpty()) {
				break;
			}
			IRedisMessage msg = redisMsgQueue.get();
			Assert.notNull(msg);
			long begin = System.nanoTime();
			try{
				msg.execute();
			}catch(Throwable t){
				Loggers.errorLogger.error("Global Logic process redis message error!", t);
			
			}finally{
				long time = (System.nanoTime() - begin) / (1000 * 1000);
				if (time > 1) {
					Loggers.errorLogger.info("Global Logic process redis message cost " + time +" ms!");
				}
			}
		}
	
	}

	@Override
	public Type getTimeAxisHostType() {
		return TimeAxisHost.Type.GLOBAL;
	}

	@Override
	public TimeAxis getTimeAxis() {
		return timeAxis;
	}

	public void init() {
		timeAxis = new TimeAxis( Globals.getTimeService(), this);
		msgQueue = new MessageQueue<IMessage>();
		redisMsgQueue = new MessageQueue<IRedisMessage>();
		globalUpdater = new GlobalDataUpdater();
	}

	public void put(IMessage msg) {
		this.msgQueue.put(msg);
	}
	
	public void putRedisMsg(IRedisMessage msg)
	{
		this.redisMsgQueue.put(msg);
	}
	
	
	public GlobalDataUpdater getGlobalDataUpdater()
	{
		return globalUpdater;
	} 
}
