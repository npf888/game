package com.robot.bazooRPC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.orm.DBService;
import com.core.template.TemplateService;
import com.core.util.JsScriptHelper;
import com.core.util.StringUtils;
import com.game.webserver.exception.LocalException;
import com.gameserver.bazoorpc.remoteData.TriggerRobotData;
import com.robot.Robot;
import com.robot.common.Globals;
import com.robot.strategy.impl.BazooBlackWhiteStrategy;
import com.robot.strategy.impl.BazooClassicalStrategy;
import com.robot.strategy.impl.BazooCowStrategy;
import com.robot.strategy.impl.BazooShowHandStrategy;

public class BazooRobotService implements InitializeRequired{
	private static final Logger logger = Loggers.gameLogger;
	private List<String> fixedNames = new ArrayList<String>();
	private List<String> notUseNames = new ArrayList<String>();
	
	private int port;
	private String serverId;
	private String path;
	private DBService dbService;
	private TemplateService templateService;
	
	@Override
	public void init() {
		
	}

	public void setRobNames(List<String> robNames) {
		fixedNames.addAll(robNames);
		notUseNames.addAll(robNames);
	}
	
	public String getRobName(){
		String name = "";
		if(notUseNames != null && notUseNames.size()>0){
			name = notUseNames.get(0);
			notUseNames.remove(name);
		}
		return name;
	}

	
	public void setParam(int port,String serverId,String path,DBService dbService,TemplateService templateService){
		this.port=port;
		this.serverId=serverId;
		this.dbService=dbService;
		this.templateService=templateService;
	}
	
	
	/**
	 * 主动开启机器人
	 */
	
	public  void openRobot(TriggerRobotData data){
		int port = Globals.getBazooRobotService().getPort();
		String serverId = Globals.getBazooRobotService().getServerId();
		String path = Globals.getBazooRobotService().getPath();
		DBService dbService = Globals.getBazooRobotService().getDbService();
		TemplateService templateService = Globals.getBazooRobotService().getTemplateService();
		int number = data.getNumber();
			logger.info("----------------------[robot]----------------sss4-----------------");
			for(int i=0;i<number;i++){
				String name = Globals.getBazooRobotService().getRobName();
				if(StringUtils.isEmpty(name)){
					return;
				}
				try{
					Thread.sleep(1000);
				}catch(Exception e){
					logger.info("自动开启机器人-打断睡眠",e);
				}
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
							Robot = completeTest(data,name,port,serverId, path,dbService,templateService);
							logger.info("----------------------[robot]----------------sss5-----------------");
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
	 * @param data 
	 * 
	 * @param robotIdStart
	 * @param robotCount
	 * @param targetServerIp
	 * @param port
	 * @throws LocalException 
	 * 
	 */
	private static Robot completeTest(TriggerRobotData data, String robName,int port,String serverId, String path,DBService dbService,TemplateService templateService) throws LocalException 
	{
		logger.info("----------------------[robot]----------------1111-----------------");
//			Robot robot =  createRobot("Robot_"+RandomUtil.nextInt(1, 999999999), serverId,dbService);
			Robot robot =  createRobot(robName, serverId,dbService);
			robot.setTemplateService(templateService);
			robot.getClassicalPropertyService().loadProperty(templateService);
			robot.getTimeTask().timeSeting(robot);
			robot.getBazooTemp().setData(data);
			robot.getBazooTemp().setBet(50);
			robot.getBazooTemp().setWayOfType("remote");
			
			/**
			 * 必须把 三种策略一次性全部 添加上，这样 机器人 进入某个模式，然后退出 在进入 另一个模式的时候 才可以处理 其他模式的消息
			 */
//			int modeType = Integer.valueOf(data.getRoomNumber().split("_")[0]);
			//经典模式
//			if(modeType == RoomNumber.MODE_TYPE_CLASSICAL){
				BazooClassicalStrategy ClassicalBt = new BazooClassicalStrategy(robot);
				robot.addRobotStrategy(ClassicalBt);
			//牛牛模式
//			}else if(modeType == RoomNumber.MODE_TYPE_COW){
				BazooCowStrategy CowBt = new BazooCowStrategy(robot);
				robot.addRobotStrategy(CowBt);
				
			//梭哈模式
//			}else if(modeType == RoomNumber.MODE_TYPE_SHOWHAND){
				BazooShowHandStrategy ShowHandBt = new BazooShowHandStrategy(robot);
				robot.addRobotStrategy(ShowHandBt);
//			}
				BazooBlackWhiteStrategy bwBt = new BazooBlackWhiteStrategy(robot);
				robot.addRobotStrategy(bwBt);
		
			logger.info("----------------------[robot]----------------222-----------------");
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}


	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public DBService getDbService() {
		return dbService;
	}

	public void setDbService(DBService dbService) {
		this.dbService = dbService;
	}

	public TemplateService getTemplateService() {
		return templateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
	
	
	
	
}
