package com.robot;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.common.config.GameServerConfig;
import com.gameserver.common.msg.CGPing;
import com.robot.common.Globals;
import com.robot.strategy.impl.BazooClassicalStrategy;
import com.robot.strategy.impl.BazooCowStrategy;
import com.robot.strategy.impl.BazooShowHandStrategy;

public class AllClassicalCowShowHandMain {

	
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
	public final static String GAME_SERVER_CONFIG_PATH_game = "config/game_server.cfg.js";
//	public final static String GAME_SERVER_CONFIG_PATH_game = "game_server.cfg.js";
	
	public static void main(String[] args) throws Exception
	{
		
		logger.info("----------------------[all  robot]----------------all-----------------");
		logger.info("----------------------[all  robot]----------------all-----------------");
		logger.info("----------------------[all  robot]----------------all-----------------");
		logger.info("----------------------[all  robot]----------------all-----------------");
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
			List<String> robNames = new ArrayList<String>();
			
			robNames.add("Robot_289635521");
			robNames.add("Robot_72574691");
			robNames.add("Robot_65343905");
			robNames.add("Robot_852249218");
			
			robNames.add("Robot_849512650");
			robNames.add("Robot_557901668");
			robNames.add("Robot_334248215");
			robNames.add("Robot_504599183");
			
			robNames.add("Robot_840628281");
			robNames.add("Robot_173800878");
			robNames.add("Robot_173800899");
			robNames.add("Robot_173800888");
			
			logger.info("----------------------[robot]----------------sss4-----------------");
			for(int i=0;i<robNames.size();i++){
				String name = robNames.get(i);
				Integer x = i;
				try{
					Thread.sleep(10000);
				}catch(Exception e){
					e.printStackTrace();
				}
				
				Thread thread = new Thread(new Runnable(){

					@Override
					public void run() {
						
						Robot Robot;
						try {
							Robot = completeTest(x,name,port,serverId, path,dbService,templateService);
							logger.info("----------------------[robot]----------------x:"+x+"-----------------");
							Robot.getRobotBazooCacheData().setDiceNum(3);
							Robot.getRobotBazooCacheData().setDiceValue(3);
						} catch (LocalException e) {
							e.printStackTrace();
						}
					}
					
				});
				
			thread.start();
			
			};
			
			
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
	private static Robot completeTest(Integer i,String robName,int port,String serverId, String path,DBService dbService,TemplateService templateService) throws LocalException 
	{
		
		
			int modeType = RoomNumber.MODE_TYPE_CLASSICAL;
			int bet = 50;
			if(i>=8){
				
				if(i == 8){
					bet=5000;
				}else if(i == 9){
					bet=50000;
				}else if(i == 10){
					bet=500000;
				}else if(i == 11){
					bet=5000000;
				}
				modeType=RoomNumber.MODE_TYPE_SHOWHAND;
			}else if(i>=4){
				
				if(i == 4){
					bet=500;
				}else if(i == 5){
					bet=5000;
				}else if(i == 6){
					bet=50000;
				}else if(i == 7){
					bet=500000;
				}
				modeType=RoomNumber.MODE_TYPE_COW;
			}else{
				if(i == 1){
					bet=500;
				}else if(i == 2){
					bet=5000;
				}else if(i == 3){
					bet=50000;
				}
			}
		
		
		
			Robot robot =  createRobot(robName, serverId,dbService);
			robot.setTemplateService(templateService);
		
			robot.getTimeTask().timeSeting(robot);
			//设置bet
			robot.getBazooTemp().setBet(bet);
			robot.getBazooTemp().setWayOfType("normal");
			//经典模式
			if(modeType == RoomNumber.MODE_TYPE_CLASSICAL){
				BazooClassicalStrategy bt = new BazooClassicalStrategy(robot);
				robot.addRobotStrategy(bt);
			//牛牛模式
			}else if(modeType == RoomNumber.MODE_TYPE_COW){
				BazooCowStrategy bt = new BazooCowStrategy(robot);
				robot.addRobotStrategy(bt);
				
			//梭哈模式
			}else if(modeType == RoomNumber.MODE_TYPE_SHOWHAND){
				BazooShowHandStrategy bt = new BazooShowHandStrategy(robot);
				robot.addRobotStrategy(bt);
			}
			
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
			//发送心跳
			robot.sendCGPing();
			
			/*new Thread(new Runnable() {
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
			}).start();*/
			
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
