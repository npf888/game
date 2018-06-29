package com.gameserver.robot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.gameserver.player.Player;

/**
 * 机器人管理器
 * @author wayne
 *
 */
public class RobotManager {
	private static final int MAX_ROBOT_NUM = 100;
	private static RobotManager instance = new RobotManager();
	public static RobotManager getInstance(){
		return instance;
	}
	
	private Map<Long,Player> robotMap = new ConcurrentHashMap<Long, Player>(MAX_ROBOT_NUM);
	
	
	public void addRobot(Player player){
		robotMap.put(player.getPassportId(), player);
	}
	
	public void removeRobot(Player player){
		robotMap.remove(player.getPassportId());
	}
}
