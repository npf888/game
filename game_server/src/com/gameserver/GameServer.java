package com.gameserver;


import java.io.IOException;
import java.net.URL;

import org.apache.log4j.PropertyConfigurator;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.SimpleByteBufferAllocator;
import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.helper.ConfigHelper;
import com.core.helper.MemHelper;
import com.core.msg.recognizer.IMessageRecognizer;
import com.core.schedule.LLScheduleEnum;
import com.core.server.AbstractIoHandler;
import com.core.server.IMessageProcessor;
import com.core.server.ServerProcess;
import com.core.server.ServerStatusLog;
import com.core.util.TimeUtils;
import com.gameserver.baccart.ScheduleBaccartRoom;
import com.gameserver.common.Globals;
import com.gameserver.common.config.GameServerConfig;
import com.gameserver.common.schedule.RoomHeartBeatServer;
import com.gameserver.common.schedule.ScheduleHeartBeatServer;
import com.gameserver.notice.schedule.ScheduleNotice;
import com.gameserver.player.OnlinePlayerLog;
import com.gameserver.player.PlayerExitReason;
import com.gameserver.player.ScheduleLastNetTime;
import com.gameserver.ranknew.ScheduleRandNew;
import com.gameserver.startup.ClientMessageRecognizer;
import com.gameserver.startup.GameServerIoHandler;
import com.gameserver.startup.GameServerRuntime;
import com.gameserver.startup.GameServerVersionCheck;
import com.gameserver.startup.MinaGameClientSession;
import com.gameserver.startup.ServerShutdownService;

/**
 * 负责游戏服务器的初始化，基础资源的加载，服务器进程的启动
 * 
 * @author Thinker
 */
public class GameServer {

	/** 日志 */
	private static final Logger logger = Loggers.gameLogger;

	public static GameServer SELF;

	/** 服务器配置信息  game_server.cfg.js */
	private GameServerConfig config;

	/** 服务器进程 */
	private ServerProcess serverProcess;
	

	private GameServer(String cfgPath)  {
	
		URL url = ClassLoader.getSystemResource(cfgPath);
		config = ConfigHelper.buildConfig(GameServerConfig.class, url);
	}

	/**
	 * 初始化各种资源和服务
	 * 
	 * @throws Exception
	 */
	private void init() throws Exception {
		
		logger.info("Begin to initialize Globals");
		Globals.init(config);
		logger.info("Globals initialized");

		// 版本检测 ，还没验证干嘛用
		if (!new GameServerVersionCheck(config).check()) {
			throw new RuntimeException("Check version fail");
		}

		
		IMessageRecognizer msgRecognizer = new ClientMessageRecognizer();
		
		AbstractIoHandler<MinaGameClientSession> ioHandler = new GameServerIoHandler(
				config.getFlashSocketPolicy(), 
				Globals.getExecutorService(),
				Globals.getSessionService());

		//GameMessageProcessor
		IMessageProcessor msgProcessor = Globals.getMessageProcessor();
		
		serverProcess = new ServerProcess(
				config.getBindIp(),
				config.getServerName(), 
				config.getPorts(), 
				msgRecognizer,
				ioHandler,
				msgProcessor, 
				config.getIoProcessor(),
				config.getMisIps());
		
	}

	/**
	 * 启动服务器
	 * 
	 * @throws IOException
	 */
	private void start() throws Exception {
	

		logger.info("Begin to start Server Process");
		serverProcess.start();
		logger.info("Server Process started");

		logger.info("Begin to start Globals");
		Globals.start();
		logger.info("Globals started");

		logger.info("Begin to schedule fixed rate task");
		
		//当前时间
		long now = Globals.getTimeService().now();
		//今天0点时间
		long time0 = TimeUtils.getBeginOfDay(now);
		
		//1494172800000  1494406735519
		long currMonday = TimeUtils.DAY*7 - (now - TimeUtils.getBeginOfWeek(now));
		
		//距离当天24点的毫秒数
		long delay= 24*60*60*1000-(now-time0);
		
		//
		int dian = TimeUtils.getHourTime(now)+1;
		//
		long delay2 = (time0+dian*60*60*1000) - now;
		
		
		int durationTime = Globals.getConfigTempl().getDurationTime();
		
		
		//启动服务器心跳
		Globals.getScheduleService().scheduleWithFixedDelay(new RoomHeartBeatServer(), LLScheduleEnum.SCHEDULE_HEART_BEAT_SERVER, 0, 1*TimeUtils.SECOND);
		Globals.getScheduleService().scheduleWithFixedDelay(new ScheduleHeartBeatServer(), LLScheduleEnum.SCHEDULE_HEART_BEAT_SERVER, 0, 1*TimeUtils.MIN);
		Globals.getScheduleService().scheduleWithFixedDelay(new ScheduleLastNetTime(), LLScheduleEnum.SCHEDULE_LAST_NET_TIME, 0, 1*TimeUtils.MIN);
		Globals.getScheduleService().scheduleWithFixedDelay(new ScheduleNotice(), LLScheduleEnum.SCHEDULE_NOTICE, 0, 1*TimeUtils.MIN);
//		Globals.getScheduleService().scheduleWithFixedDelay(new ScheduleBaccartRoom(), LLScheduleEnum.SCHEDULE_BACCART_ROOM, 0, 1*TimeUtils.SECOND);
		
//		Globals.getScheduleService().scheduleWithFixedDelay(new ScheduleRandNew(), LLScheduleEnum.RANK_NEW, currMonday, TimeUtils.DAY*7);
		
		
		//在线用户统计
		Globals.getScheduleService().scheduleWithFixedDelay(new OnlinePlayerLog(), LLScheduleEnum.ONLINEPLAYERLOG, delay2, TimeUtils.HOUR);
	
		// 停机时停止主线程的处理
		Globals.getShutdownHooker().register(new Runnable() {
			@Override
			public void run() {
				logger.info("Begin to shutdown Game Server ");
	
				// 设置为STOPPING状态
				Globals.getServerStatusService().destroy();
				
				// 设置GameServer关闭状态
				GameServerRuntime.setShutdowning();
		
				//百家乐
				Globals.getBaccartService().destroy();
				
				Globals.getSceneService().stop();

				// 踢掉所有在线玩家，这个操作在shutdowning状态下不做正常下线的同步存库操作
				Globals.getOnlinePlayerService().offlineAllPlayers(PlayerExitReason.SERVER_SHUTDOWN);
				// 关闭服务器消息接受
				serverProcess.stop();

				// 关闭异步操作服务，这个操作会等5分钟，尽量确保各异步存库任务执行完再关闭线程池
				Globals.getAsyncService().stop();
				logger.info("Begin to syn save all online player data.");
				// 最后做一遍全部数据的同步存库，将PlayerDataUpdater的尚未更新的数据同步到dbs
				new ServerShutdownService().synSaveAllPlayerOnShutdown();
				
				logger.info("syn save complete.");
				// 关闭系统维护线程池服务
				Globals.getExecutorService().stop();

				//关闭心跳线程
				Globals.stop();
				//删除redis缓存serverId数据
				Globals.getServerComm().removeId();
				
				logger.info("Game Server shutdowned");
			}
		});

		// 增加JVM停机时的处理,注册停服监听器,用于执行资源的销毁等停服时的处理工作,并设置服务器状态
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				Globals.getShutdownHooker().run();
				ServerStatusLog.getDefaultLog().logStopped();
			}
		});

		GameServerRuntime.setOpenOn();
		
	}

	// 配置路径
	private final static String LOG_CONFIG_PATH = "config/log4j.properties";
	
	private final static String GAME_SERVER_CONFIG_PATH = "game_server.cfg.js";

	
	public static void main(String[] args) {
		// 设置 log4j 配置文件
		PropertyConfigurator.configure(LOG_CONFIG_PATH);

		logger.info("Starting Game Server");
		logger.info(MemHelper.buildMemoryInfo());
		// 获取当前时间
		long startTime = System.currentTimeMillis();

		try {
			//记录服务器启动时间 logs/server_status
			ServerStatusLog.getDefaultLog().logStarting();

			// mina setting
			ByteBuffer.setUseDirectBuffers(false);
			ByteBuffer.setAllocator(new SimpleByteBufferAllocator());

			SELF = new GameServer(GAME_SERVER_CONFIG_PATH);

			SELF.init();
			SELF.start();

			ServerStatusLog.getDefaultLog().logRunning();

		} catch (Exception e) {
			logger.error("Failed to start server", e);
			ServerStatusLog.getDefaultLog().logStartFail();
			System.exit(1);
			return;
		}

		// 强制 GC 一次
		System.gc();

		logger.info(MemHelper.buildMemoryInfo());
		logger.info("<<-------------------------------- Game Server started version:"
				+ SELF.config.getVersion()
				+ "----------------------------------->>");

		long endTime = System.currentTimeMillis();
		logger.info("服务器启动时间 : " + (endTime - startTime) + "毫秒");
		
		
//		//for eclipse
//		logger.info("press ENTER to call System.exit() and run the shutdown routine.");  
//        try {  
//            System.in.read();  
//        } catch (IOException e) {  
//        	logger.error("", e);
//        }  
//        System.exit(0);  
	}

}
