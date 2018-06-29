package com.robot;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.core.config.ConfigUtil;
import com.core.event.EventListenerAdapter;
import com.core.helper.ConfigHelper;
import com.core.orm.DBAccessEvent;
import com.core.orm.DBAccessEventListener;
import com.core.orm.DBService;
import com.core.orm.DBServiceBuilder;
import com.core.template.TemplateService;
import com.core.util.JsScriptHelper;
import com.core.util.RandomUtil;
import com.game.webserver.exception.LocalException;
import com.gameserver.common.config.GameServerConfig;
import com.gameserver.common.msg.CGPing;
import com.robot.common.Globals;
import com.robot.strategy.impl.BazooShowHandStrategy;

/**
 * 无双吹牛
 * @author JavaServer
 *
 */
public class RobotBazooShowHandMain {

	
	public final static String GAME_SERVER_CONFIG_PATH_game = "game_server.cfg.js";
	
	public static void main(String[] args) throws Exception
	{
		
		
		int port =  9000;
		String serverId = "100003";
		String path = null;
		long startTime = System.currentTimeMillis();
		URL url = ClassLoader.getSystemResource(RobotServer.GAME_SERVER_CONFIG_PATH);
		RobotServerConfig config = ConfigHelper.buildConfig(RobotServerConfig.class, url);
		//全球配置初始化
		Globals.init(config);
		
		Globals.start();
	
	
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
//			robNames.add("Robot_289635521");
			robNames.add("Robot_72574691");
//			robNames.add("Robot_65343905");
			
			
//			robNames.add("Robot3_852249218");
//			robNames.add("Robot3_849512650");
//			robNames.add("Robot3_557901668");
//			robNames.add("Robot3_334248215");
//			robNames.add("Robot3_504599183");
//			robNames.add("Robot3_840628281");
//			robNames.add("Robot3_173800878");
			for(String name:robNames){
//			for(int i=0;i<100;i++){
				Thread thread = new Thread(new Runnable(){

					@Override
					public void run() {
						
						/*try{
							Thread.sleep(1000);
						}catch(Exception e){
							e.printStackTrace();
						}*/
						Robot Robot;
						try {
							Robot = completeTest(name,port,serverId, path,dbService,templateService);
							Robot.getRobotBazooCacheData().setDiceNum(3);
							Robot.getRobotBazooCacheData().setDiceValue(3);
							
							
						} catch (LocalException e) {
							e.printStackTrace();
						}
					}
					
				});
				
			thread.start();
			
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
		
//			Robot robot =  createRobot("Robot_"+RandomUtil.nextInt(1, 999999999), serverId,dbService);
			Robot robot =  createRobot(robName, serverId,dbService);
			robot.setTemplateService(templateService);
			robot.getTimeTask().timeSeting(robot);
			
			robot.getBazooTemp().setBet(5000);
			robot.getBazooTemp().setWayOfType("normal");
			BazooShowHandStrategy bt = new BazooShowHandStrategy(robot);
		
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
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						try{
							Thread.sleep(1000);
						}catch(Exception e){
						}
						robot.sendMessage(new CGPing());
					}
				}
			}).start();
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
