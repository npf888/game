package com.gameserver.bazoo.util;

import com.gameserver.player.Player;
import com.gameserver.player.enums.PlayerRoleEnum;

/**
 * 判断 用户是否是机器人
 * @author JavaServer
 *
 */
public class RobotJudgeUtil {

	
	public static boolean judgeRobot(Player player){
		//判断是否是机器人
		if(player.getPlayerRoleEnum() == PlayerRoleEnum.ROBOT){
			return true;
		}
		return false;
	}
}
