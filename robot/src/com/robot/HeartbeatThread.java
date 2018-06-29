package com.robot;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.core.msg.MessageQueue;
import com.core.session.MinaSession;
import com.core.util.Assert;
import com.core.util.ExecutorUtil;
import com.gameserver.bazoo.msg.GCRobotWhichRoomToGoin;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.common.msg.GCHandshake;
import com.gameserver.common.msg.GCMessage;
import com.gameserver.common.msg.GCSystemMessage;
import com.gameserver.player.msg.CGPlayerEnter;
import com.gameserver.player.msg.GCCheckPlayerLogin;
import com.gameserver.player.msg.GCEnterScene;
import com.robot.strategy.IRobotStrategy;
import com.robot.strategy.impl.BazooBlackWhiteStrategy;
import com.robot.strategy.impl.BazooClassicalStrategy;
import com.robot.strategy.impl.BazooCowStrategy;
import com.robot.strategy.impl.BazooShowHandStrategy;


public class HeartbeatThread extends Thread{
	
	private static final Logger logger = Loggers.gameLogger;
	private final static int maxMsgCountProcess = 1024;
	/** 线程池 */
	private final ExecutorService pool;
	
	/** 是否繁忙 */
	private volatile boolean isBusy;
	/**德州房间*/
	private ClientTexasRoomManager texasRoomManager = new ClientTexasRoomManager();
	/** 消息队列 */
	private MessageQueue<IMessage> msgQueue = new MessageQueue<IMessage>();
	
	
	/** 是否处于活动状态,默认为true，shutdown后变为false */
	private volatile boolean isLive = true;
	

	
	/**
	 * 构建心跳线程
	 * 
	 */
	public HeartbeatThread()
	{
		pool = Executors.newSingleThreadExecutor();

	}
	
	public ClientTexasRoomManager getTexasRoomManager(){
		return this.texasRoomManager;
	}

	
	@Override
	public void run()
	{
		try
		{
	
			while (isLive)
			{
				processMessage();
				long beginTime=System.currentTimeMillis();
				pool.submit(texasRoomManager);
				long runTime=System.currentTimeMillis()-beginTime;
				if(runTime>50*1000)
					logger.info("HeartbeatThread Run Time:"+runTime);
				
				sleep(200);
					
			}
		} catch (Exception e)
		{
			Loggers.gameLogger.error("", e);
			shutdown();
		}
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
				Robot robot = getRobot(msg);
				if(robot == null)
					return;
				if(msg instanceof GCHandshake)
				{
					robot.doLogin();
				}
				
				else if(msg instanceof GCCheckPlayerLogin){
					robot.sendMessage(new CGPlayerEnter());
				}
				else if(msg instanceof GCEnterScene)
				{
					robot.doEnterScene();
				}
				
				else if(msg instanceof GCSystemMessage)
				{
					System.out.println(((GCSystemMessage)msg).getCode());
				}
				else
				{
					//如果是告诉 机器人进入哪个 房间，那么就得用相应的strategy
					if(msg instanceof GCRobotWhichRoomToGoin){
						GCRobotWhichRoomToGoin GCRobotWhichRoomToGoin = (GCRobotWhichRoomToGoin)msg;
						String roomNumber = GCRobotWhichRoomToGoin.getRoomNumber();
						robot.getBazooTemp().setRoomNumber(RoomNumber.toRoomNumber(roomNumber));
					}
					for(IRobotStrategy strategy : robot.getStrategyList())
					{
						if(robot.getBazooTemp().getRoomNumber() == null){
							strategy.onResponse(msg);	
						}else{
							
							if(robot.getBazooTemp().getRoomNumber().getModeType() == RoomNumber.MODE_TYPE_CLASSICAL){
								if(strategy instanceof BazooClassicalStrategy){
									strategy.onResponse(msg);	
								}
								
							}else if(robot.getBazooTemp().getRoomNumber().getModeType() == RoomNumber.MODE_TYPE_COW){
								if(strategy instanceof BazooCowStrategy){
									strategy.onResponse(msg);	
								}
								
							}else if(robot.getBazooTemp().getRoomNumber().getModeType() == RoomNumber.MODE_TYPE_SHOWHAND){
								if(strategy instanceof BazooShowHandStrategy){
									strategy.onResponse(msg);	
								}
								
							}else if(robot.getBazooTemp().getRoomNumber().getModeType() == RoomNumber.MODE_TYPE_BLACK_WHITE){
								if(strategy instanceof BazooBlackWhiteStrategy){
									strategy.onResponse(msg);	
								}
							}
						}
					}
					
					
					
				}
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
	
	public void put(IMessage msg) {
		this.msgQueue.put(msg);
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
	
	private Robot getRobot(IMessage message)
	{
		MinaSession minaSession = (MinaSession)((GCMessage)message).getSession();
		return RobotManager.getInstance().getRobot(minaSession);		
	}
}
