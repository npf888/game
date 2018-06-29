package com.robot;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import com.common.constants.Loggers;
import com.core.helper.ConfigHelper;
import com.core.helper.MemHelper;
import com.core.server.ServerStatusLog;
import com.robot.common.Globals;


/**
 * 机器人服务器
 * @author wayne
 *
 */
public class RobotServer {
	/** 日志 */
	private static final Logger logger = Loggers.gameLogger;
	
	public static RobotServer SELF;
	/** 服务器配置信息 */
	private RobotServerConfig config;


	private RobotServer(String cfgPath)  {
	
		URL url = ClassLoader.getSystemResource(cfgPath);
		config = ConfigHelper.buildConfig(RobotServerConfig.class, url);
	}
	
	private void init() throws Exception {
		logger.info("Begin to initialize Globals");
		Globals.init(config);
		logger.info("Globals initialized");


	}
	
	/**
	 * 启动服务器
	 * 
	 * @throws IOException
	 */
	private void start() throws Exception {

		logger.info("Begin to start Globals");
		Globals.start();
		logger.info("Globals started");

	
		
		// 停机时停止主线程的处理
		Globals.getShutdownHooker().register(new Runnable() {
			@Override
			public void run() {
				logger.info("Begin to shutdown Game Server ");
			
				logger.info("Game Server shutdowned");
			}
		});

		// 增加JVM停机时的处理,注册停服监听器,用于执行资源的销毁等停服时的处理工作,并设置服务器状态
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				Globals.getShutdownHooker().run();
			}
		});

		
	}
	
	
	// 配置路径
	private final static String LOG_CONFIG_PATH = "config/log4j.properties";
	/**
	 * 
	 * 打包机器人要 修改 这个配置文件
	 * 
	 * 
	 * 
	 */
//	public final static String GAME_SERVER_CONFIG_PATH = "config/robot_server.cfg.js";
	public final static String GAME_SERVER_CONFIG_PATH = "robot_server.cfg.js";

	public static void main(String[] args) {
		// 设置 log4j 配置文件
		PropertyConfigurator.configure(LOG_CONFIG_PATH);

		logger.info("Starting Robot Server");
		logger.info(MemHelper.buildMemoryInfo());
		// 获取当前时间
		long startTime = System.currentTimeMillis();

		try {

			SELF = new RobotServer(GAME_SERVER_CONFIG_PATH);

			SELF.init();
			
			SELF.start();


		} catch (Exception e) {  
			e.printStackTrace();
			logger.error("Failed to start server", e);
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
	}
}

