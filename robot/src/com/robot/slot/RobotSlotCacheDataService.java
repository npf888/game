package com.robot.slot;

import java.util.Date;

import com.gameserver.slot.RobotStatisData;
import com.robot.Robot;

public class RobotSlotCacheDataService {

	public synchronized void save(Robot robot,int slotId) {
		RobotSlotCacheData robotSlotCacheData= robot.getRobotSlotCacheData();
		RobotStatisData robotStatisData = new RobotStatisData();
		robotStatisData.setSlotName(robot.getRobotSlotCacheData().getSlotName());
		robotStatisData.setSlotId(slotId);
		robotStatisData.setSlotType(robot.getRobotSlotCacheData().getSlotType());
		robotStatisData.setChartId(Long.valueOf(robot.getPassportId()));
		robotStatisData.setCreateTime(new Date());
		robotStatisData.setUpdateTime(new Date());
		robotStatisData.setBonusNum(robotSlotCacheData.getBonusNum());
		robotStatisData.setBonusTriggerCount(robotSlotCacheData.getBonusTriggerCount());
		robotStatisData.setBonusTriggerFreeCount(robotSlotCacheData.getBonusTriggerFreeCount());
		robotStatisData.setScatterNum(robotSlotCacheData.getScatterNum());
		robotStatisData.setScatterTriggerCount(robotSlotCacheData.getScatterTriggerCount());
		robotStatisData.setScatterTriggerFreeCount(robotSlotCacheData.getScatterTriggerFreeCount());
		robotStatisData.setRewardCount(robotSlotCacheData.getRewardCount());
		robotStatisData.setRewardUsed(robotSlotCacheData.getRewardUsed());
		robotStatisData.setPayCount(robotSlotCacheData.getPayCount());
		
		
		robot.getDao().save(robotStatisData.toEntity());
		
		
	}
}
