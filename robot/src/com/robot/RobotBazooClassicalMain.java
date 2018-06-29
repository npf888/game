package com.robot;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.config.ConfigUtil;
import com.core.event.EventListenerAdapter;
import com.core.helper.ConfigHelper;
import com.core.orm.DBAccessEvent;
import com.core.orm.DBAccessEventListener;
import com.core.orm.DBService;
import com.core.orm.DBServiceBuilder;
import com.core.template.TemplateService;
import com.core.util.JsScriptHelper;
import com.game.webserver.exception.LocalException;
import com.gameserver.common.config.GameServerConfig;
import com.robot.common.Globals;
import com.robot.strategy.impl.BazooClassicalStrategy;

/**
 * 无双吹牛
 * @author JavaServer
 *
 */
public class RobotBazooClassicalMain {

	private static final Logger logger = Loggers.gameLogger;
	/**
	 * 
	 * 
	 * 
	 * 打包机器人 要修改 这个配置文件
	 * 
	 * 
	 * 
	 */
//	public final static String GAME_SERVER_CONFIG_PATH_game = "config/game_server.cfg.js";
	public final static String GAME_SERVER_CONFIG_PATH_game = "game_server.cfg.js";
	static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	public static void main(String[] args) throws Exception
	{
		
		logger.info("----------------------[robot]----------------sss1-----------------");
		int port =  9000;
		String serverId = "100003";
		String path = null;
		long startTime = System.currentTimeMillis();
		URL url = ClassLoader.getSystemResource(RobotServer.GAME_SERVER_CONFIG_PATH);
		RobotServerConfig config = ConfigHelper.buildConfig(RobotServerConfig.class, url);
		logger.info("----------------------[robot]----------------sss3-----------------");
		//全球配置初始化
		Globals.init(config);
		
		Globals.start();
		logger.info("----------------------[robot]----------------sss3-----------------");
	
		URL urlgameserver = ClassLoader.getSystemResource(GAME_SERVER_CONFIG_PATH_game);
		GameServerConfig gameconfig = ConfigHelper.buildConfig(GameServerConfig.class, urlgameserver);
		
		TemplateService templateService = new TemplateService(gameconfig.getScriptDirFullPath(),false);
		templateService.init(ConfigUtil.getConfigURL("templates.xml"));
		
		/** 资源初始化 */
		EventListenerAdapter eventAdapter = new EventListenerAdapter();
		eventAdapter.addListener(DBAccessEvent.class,new DBAccessEventListener(config));
		ClassLoader _classLoader = Thread.currentThread().getContextClassLoader();
		int daoType = 0;

		String[] _dbConfig = "game_server_hibernate.cfg.xml,game_server_hibernate_query.xml".split(",");
		URL _dbUrl = _classLoader.getResource(_dbConfig[0]);
		String[] _dbResources = new String[_dbConfig.length - 1];
		if (_dbConfig.length > 1) 
		{
			System.arraycopy(_dbConfig, 1, _dbResources, 0,_dbConfig.length - 1);
		}
		
		DBService dbService = DBServiceBuilder.buildDirectDBService(eventAdapter,daoType, _dbUrl, _dbResources);
		
		/**
		 * 创建 6 个机器人  进入房间 
		 */
//		for(int i=0;i<6;i++){
			List<String> robNames = new ArrayList<String>();
//			robNames.add("Robot_72574691");
			robNames.add("Robot_65343905");
			
			
			
			robNames.add("Robot_852249218");
			robNames.add("Robot_849512650");
			robNames.add("Robot_557901668");
//			robNames.add("Robot_334248215");
//			robNames.add("Robot_504599183");
//			robNames.add("Robot_840628281");
//			robNames.add("Robot_173800878");
//			robNames.add("Robot_173800899");
			
			logger.info("----------------------[robot]----------------sss4-----------------");
			
			
			for(String name:robNames){
				
				try{
					Thread.sleep(1000);
				}catch(Exception e){
					e.printStackTrace();
				}
				cachedThreadPool.execute(new Runnable(){

					@Override
					public void run() {
						
						Robot Robot;
						try {
							Robot = completeTest(name,port,serverId, path,dbService,templateService);
							logger.info("----------------------[robot]----------------sss5-----------------");
							Robot.getRobotBazooCacheData().setDiceNum(3);
							Robot.getRobotBazooCacheData().setDiceValue(3);
						} catch (LocalException e) {
							e.printStackTrace();
						}
					}
					
				});
				
			
			};
			
			
//		}
		
	    }

	/**
	 * 完整测试, 包括征收、建筑升级、装备强化、武将训练、农田占领、聊天等功能
	 * 
	 * @param robotIdStart
	 * @param robotCount
	 * @param targetServerIp
	 * @param port
	 * @throws LocalException 
	 * 
	 */
	private static Robot completeTest(String robName,int port,String serverId, String path,DBService dbService,TemplateService templateService) throws LocalException 
	{
		logger.info("----------------------[robot]----------------1111-----------------");
//			Robot robot =  createRobot("Robot_"+RandomUtil.nextInt(1, 999999999), serverId,dbService);
			Robot robot =  createRobot(robName, serverId,dbService);
			robot.setTemplateService(templateService);
			robot.getClassicalPropertyService().loadProperty(templateService);
			robot.getTimeTask().timeSeting(robot);
			
			robot.getBazooTemp().setBet(50);
			robot.getBazooTemp().setWayOfType("normal");
			BazooClassicalStrategy bt = new BazooClassicalStrategy(robot);
		
			logger.info("----------------------[robot]----------------222-----------------");
			robot.addRobotStrategy(bt);
			// 加载具体脚本
			if(path != null)
			{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("robot", robot);
				
				try
				{
					JsScriptHelper.executeScriptFile(path, params);
				} catch (Exception e)
				{
					
				}
			}

			robot.start();
			robot.sendCGPing();
			logger.info("----------------------[robot]----------------333-----------------");
			return robot;

	}

	/**
	 * 创建机器人
	 * 
	 * @param robotId
	 * @param targetServerIp
	 * @param port
	 * @return
	 * @throws LocalException 
	 */
	private static Robot createRobot(String deviceMac,String serverId,DBService dbService) throws LocalException
	{
		return new Robot(deviceMac,serverId,dbService);
	}

}
