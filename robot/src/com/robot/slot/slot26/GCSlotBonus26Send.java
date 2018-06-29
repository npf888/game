package com.robot.slot.slot26;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType26BounsInfo;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus26Send  extends GCSlotBonusBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		GCSlotType26BounsInfo gCSlotType26BounsInfo = (GCSlotType26BounsInfo)message;
		long totalGold = gCSlotType26BounsInfo.getTotalGold();
		logger.info("宙斯小游戏 总共获得的 钱----------："+totalGold);
		robot.getRobotSlotCacheData().setBonusFree(true);
		robot.getBonusHandler().triggerBonusNum(robot,0);
		robot.getBonusHandler().countBonusNum(robot, (int)totalGold);
		robot.getRobotSlotCacheData().setBonusFree(false);
		
	}

}
