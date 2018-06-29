package com.robot;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import com.gameserver.bazoorpc.remoteData.TriggerRobotData;
import com.gameserver.common.config.GameServerConfig;
import com.robot.common.Globals;

/**
 * 无双吹牛
 * @author JavaServer
 *
 */
public class RobotBazooClassicalRemote {

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
	
	public static void main(String[] args) throws Exception
	{
		startClassicalRobot();
	}
	public static void startClassicalRobot() throws Exception
	{
		
		logger.info("----------------------[robotRPC]----------------sss1-----------------");
		int port =  9000;
		String serverId = "100003";
		String path = null;
		long startTime = System.currentTimeMillis();
		URL url = ClassLoader.getSystemResource(RobotServer.GAME_SERVER_CONFIG_PATH);
		RobotServerConfig config = ConfigHelper.buildConfig(RobotServerConfig.class, url);
		logger.info("----------------------[robotRPC]----------------sss3-----------------");
		//全球配置初始化
		Globals.init(config);
		
		Globals.start();
		logger.info("----------------------[robotRPC]----------------sss3-----------------");
	
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
		List<String> robNames = new ArrayList<String>();
		for(int i=0;i<30;i++){
			robNames.add("Robot_100000"+i);
		}
		
		Globals.getBazooRobotService().setRobNames(robNames);
		Globals.getBazooRobotService().setParam(port, serverId, path, dbService, templateService);
		
		
		//启动之后开启多少个机器人
		TriggerRobotData data = new TriggerRobotData();
		data.setNumber(4);
		Globals.getBazooRobotService().openRobot(data);
		logger.info("----------------------[robotRPC]----------------end-----------------");
	}
	
	

}
