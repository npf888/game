//package com.robot;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
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
//import com.core.util.RandomUtil;
//import com.db.model.SlotListEntity;
//import com.game.webserver.exception.LocalException;
//import com.game.webserver.login.ILoginServerHandler;
//import com.gameserver.common.config.GameServerConfig;
//import com.robot.common.Globals;
//import com.robot.slot.slotname.SlotNameManager;
//import com.robot.strategy.impl.SlotStrategy;
//
//public class RobotSlotMain {
//
//	private static ILoginServerHandler synLoginServerHandler;
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
//		int runTimes = 5000;
// 		//要跑的第几种 类型的 老虎机
//		/**
//		 * 海盗       31
//		 * 斯巴达   32 跑所有的
//		 * 水果       3
//		 * 
//		 * 12 维密 - 4 沙滩  现在用的一个
//		 */
//		int start =32;
//		int end = 32;
//		
////		int bet = 75;
//		int free = 0;
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
//		//创建多个机器人去跑 老虎机
//		for(int i =0;i<1;i++){//20*30  六百个人同时跑
//			for(int x=start;x<=end;x++){
//				int ss = x;
//				Thread.sleep(5000);
////			 	完整测试
//				Thread t = new Thread(new Runnable(){  
//					public void run(){  
//						try {
//							completeTest(ss,free,runTimes,port,serverId, path,dbService,templateService);
//						} catch (LocalException e) {
//							e.printStackTrace();
//						}
//					}
//				});  
//				t.start();
//				
//			}
//		}
//		try
//		{
//			System.out.println("press any key to continue ...");
//			long endTime = System.currentTimeMillis();
//			System.out.println("30个老虎机-跑 "+runTimes+" 次- 所用时间："+(endTime-startTime)/(1000*60*60)+" 分钟");
//			System.in.read();
//		} catch (Exception ex) 
//		{
//			ex.printStackTrace();
//		}
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
//	private static void completeTest(int start,int free,int runTimes,int port,String serverId, String path,DBService dbService,TemplateService templateService) throws LocalException 
//	{
//		
//			Robot robot =  createRobot("Robot_"+RandomUtil.nextInt(1, 999999999), serverId,dbService);
//			List<SlotListEntity> slotLists = robot.getSlotListDao().getEverySlot();
//			SlotListEntity  entity = null; 
//			for(SlotListEntity en:slotLists){
//				if(en.getType() == start){
//					if(entity == null){
//						entity=en;
//					}else{
//						if(en.getId()<entity.getId()){
//							entity=en;
//						}
//					}
//				}
//			}
//			if(entity == null){
//				return;
//			}
//			robot.getRobotSlotCacheData().setBet(entity.getBet1());
//			robot.getRobotSlotCacheData().setFree(free);
//			robot.getRobotSlotCacheData().setMaxNum(runTimes);
//			robot.getRobotSlotCacheData().setSlotName(entity.getLangDesc());
//			robot.getRobotSlotCacheData().setSlotId(entity.getId().intValue());
//			robot.getRobotSlotCacheData().setSlotType(entity.getType());
//			robot.setTemplateService(templateService);
//			robot.getTimeTask().timeSeting(robot);
//			SlotStrategy bt = new SlotStrategy(robot);
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
//
//
//}
