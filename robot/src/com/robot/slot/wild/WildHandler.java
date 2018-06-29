package com.robot.slot.wild;

import com.robot.Robot;
/**
 * 处理wild用的
 * @author JavaServer
 *
 */
public class WildHandler {

	public void countWild(Robot robot,int freeNum){
		
	}

	public void triggerNum(Robot robot) {
		if(robot.getRobotSlotCacheData().getFree() == 0){//付费
			long originTimes = robot.getRobotSlotCacheData().getBonusTriggerCount();
			robot.getRobotSlotCacheData().setWildTriggerCount(1+originTimes);
			robot.getRobotSlotCacheData().setWildFree(true);
		}else{//免费 转动次数 永远属于 wild自己
			long originTimes = robot.getRobotSlotCacheData().getWildTriggerFreeCount();
			robot.getRobotSlotCacheData().setWildTriggerFreeCount(1+originTimes);
		}
		
	}
}
