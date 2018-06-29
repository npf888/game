//package com.robot;
//
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.core.config.ConfigUtil;
//import com.core.event.EventListenerAdapter;
//import com.core.helper.ConfigHelper;
//import com.core.orm.DBAccessEvent;
//import com.core.orm.DBAccessEventListener;
//import com.core.orm.DBService;
//import com.core.orm.DBServiceBuilder;
//import com.core.template.TemplateService;
//import com.core.util.JsScriptHelper;
//import com.game.webserver.exception.LocalException;
//import com.game.webserver.login.ILoginServerHandler;
//import com.gameserver.common.config.GameServerConfig;
//import com.robot.common.Globals;
//import com.robot.strategy.impl.SlotOtherStrategy;
//
///**
// * 除了转动老虎机以外 其他的接口
// * 例如活动领取奖励的接口
// * @author JavaServer
// *
// */
//public class RobotSlotOtherMain {
//private static ILoginServerHandler synLoginServerHandler;
//	
//	public final static String GAME_SERVER_CONFIG_PATH_game = "game_server.cfg.js";
//	
//	public static void main(String[] args) throws Exception
//	{
//		
//		
//		int port =  9000;
//		String serverId = "100003";
//		String path = null;
//		//需要跑的次数
//		int runTimes = 10000;
//		long startTime = System.currentTimeMillis();
//		URL url = ClassLoader.getSystemResource(RobotServer.GAME_SERVER_CONFIG_PATH);
//		RobotServerConfig config = ConfigHelper.buildConfig(RobotServerConfig.class, url);
//		//全球配置初始化
//		Globals.init(config);
//		
//		Globals.start();
//	
//	
//		
//		URL urlgameserver = ClassLoader.getSystemResource(GAME_SERVER_CONFIG_PATH_game);
//		GameServerConfig gameconfig = ConfigHelper.buildConfig(GameServerConfig.class, urlgameserver);
//		
//		TemplateService templateService = new TemplateService(gameconfig.getScriptDirFullPath(),false);
//		templateService.init(ConfigUtil.getConfigURL("templates.xml"));
//		
//		/** 资源初始化 */
//		EventListenerAdapter eventAdapter = new EventListenerAdapter();
//		eventAdapter.addListener(DBAccessEvent.class,new DBAccessEventListener(config));
//		ClassLoader _classLoader = Thread.currentThread().getContextClassLoader();
//		int daoType = 0;
//
//		String[] _dbConfig = "game_server_hibernate.cfg.xml,game_server_hibernate_query.xml".split(",");
//		URL _dbUrl = _classLoader.getResource(_dbConfig[0]);
//		String[] _dbResources = new String[_dbConfig.length - 1];
//		if (_dbConfig.length > 1) 
//		{
//			System.arraycopy(_dbConfig, 1, _dbResources, 0,_dbConfig.length - 1);
//		}
//		
//		DBService dbService = DBServiceBuilder.buildDirectDBService(eventAdapter,daoType, _dbUrl, _dbResources);
//		
//		//主体
//		completeTest(port,serverId, path,dbService,templateService);
//				
//		/*try
//		{
//			System.out.println("press any key to continue ...");
//			long endTime = System.currentTimeMillis();
//			System.out.println("30个老虎机-跑 "+runTimes+" 次- 所用时间："+(endTime-startTime)/(1000*60*60)+" 分钟");
//			System.in.read();
//		} catch (Exception ex) 
//		{
//			ex.printStackTrace();
//		}*/
//	}
//
//	/**
//	 * 完整测试, 包括征收、建筑升级、装备强化、武将训练、农田占领、聊天等功能
//	 * 
//	 * @param robotIdStart
//	 * @param robotCount
//	 * @param targetServerIp
//	 * @param port
//	 * @throws LocalException 
//	 * 
//	 */
//	private static void completeTest(int port,String serverId, String path,DBService dbService,TemplateService templateService) throws LocalException 
//	{
//		
////			Robot robot =  createRobot("Robot_"+RandomUtil.nextInt(1, 999999999), serverId,dbService);
//			Robot robot =  createRobot("Robot_300937277", serverId,dbService);
//			SlotOtherStrategy bt = new SlotOtherStrategy(robot);
//		
//			robot.addRobotStrategy(bt);
//			// 加载具体脚本
//			if(path != null)
//			{
//				Map<String, Object> params = new HashMap<String, Object>();
//				params.put("robot", robot);
//				
//				try
//				{
//					JsScriptHelper.executeScriptFile(path, params);
//				} catch (Exception e)
//				{
//					
//				}
//			}
//
//			robot.start();
//
//	}
//
//	/**
//	 * 创建机器人
//	 * 
//	 * @param robotId
//	 * @param targetServerIp
//	 * @param port
//	 * @return
//	 * @throws LocalException 
//	 */
//	private static Robot createRobot(String deviceMac,String serverId,DBService dbService) throws LocalException
//	{
//		return new Robot(deviceMac,serverId,dbService);
//	}
//}
