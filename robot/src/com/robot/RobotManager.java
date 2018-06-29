package com.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.core.session.MinaSession;
import com.core.util.RandomUtil;
import com.google.common.collect.Maps;
import com.robot.common.Globals;

/**
 * 机器人管理类
 * @author Thinker
 *
 */
public class RobotManager
{
	
	private static RobotManager _instance = null;
	
	static
	{
		_instance = new RobotManager();
	}
		
	//private final Map<String, Robot> passport2RobotMap;
	
	private final Map<String,Robot> mac2RobotMap;
	
	private final Map<MinaSession, Robot> session2RobotMap;	
	
	private final ScheduledExecutorService scheduler;	
	
	private String robotPrefix;
	private long startRobotId;
	private long robotNum;
	
	private RobotManager()
	{
	//	passport2RobotMap = Maps.newConcurrentHashMap();
		mac2RobotMap= Maps.newConcurrentHashMap();
		session2RobotMap = Maps.newConcurrentHashMap();
		scheduler = Executors.newScheduledThreadPool(10);
	}

	
	public static RobotManager getInstance()
	{
		return _instance;
	}

	public void init(){
		robotPrefix = Globals.getRobotServerConfig().getRobotPrefix();
		startRobotId = Globals.getRobotServerConfig().getRobotStartId();
		robotNum = Globals.getRobotServerConfig().getRobotNum();
	}
	
	public  Robot createRobot(String serverId){
		String deviceMac = getAvailableMac();
		if(deviceMac == null)
			return null;
		
		Robot robot = null;
		try {
			//TODO 机器人稍后修改
			//robot = new Robot(deviceMac,serverId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return robot;
	}
	
	/**
	 * 获得可达到mac
	 * @return
	 */
	public String getAvailableMac(){
		List<Long> tempIdList= new ArrayList<Long>();
		for(long i=startRobotId;i<startRobotId+robotNum;i++){
			String deviceMac= robotPrefix+i; 
			Robot robot = mac2RobotMap.get(deviceMac);
			if(robot == null)
			{
				tempIdList.add(i);
			}
			
		}
		if(tempIdList.size()==0)
			return null;
		int randomNum = RandomUtil.nextInt(0, tempIdList.size());
		return robotPrefix+tempIdList.get(randomNum);
	}
	
	public void shutdown()
	{
		this.scheduler.shutdownNow();
	}
	

		

	public void addRobot(MinaSession clientSession,Robot robot)
	{
		session2RobotMap.put(clientSession, robot);
		mac2RobotMap.put(robot.getDeviceMac(), robot);
	}
	
	public Robot getRobot(MinaSession clientSession)
	{
		return session2RobotMap.get(clientSession);
	}
	
	public Robot removeRobot(MinaSession ioSession)
	{
		Robot robot = session2RobotMap.get(ioSession);
		mac2RobotMap.remove(robot.getDeviceMac());
		return session2RobotMap.remove(ioSession);
	}
	
	public Robot getRobot(String deviceMac)
	{
		return mac2RobotMap.get(deviceMac);
	}

	public void scheduleOnce(RobotAction action, long delay) 
	{
		ScheduledFuture<?> future = scheduler.schedule(action, delay, TimeUnit.MILLISECONDS);
		action.setFuture(future);
	}
	
	public void scheduleWithFixedDelay(RobotAction action, long delay, long period) 
	{
		ScheduledFuture<?> _scheduledFuture = scheduler.scheduleAtFixedRate(action, delay, period, TimeUnit.MILLISECONDS);
		action.setFuture(_scheduledFuture);
	}
}
