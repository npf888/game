package com.robot.slot.slot14;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType14Bonus;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus14 extends GCSlotBonusBase{

	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		robot.getRobotSlotCacheData().setBonusPubNum(3);
		GCSlotType14Bonus gCSlotType14Bonus = (GCSlotType14Bonus)message;
		int totalGold = gCSlotType14Bonus.getBounsWeight();
		robot.getRobotSlotCacheData().setBonusFree(true);
		robot.getBonusHandler().triggerBonusNum(robot,0);
		robot.getBonusHandler().countBonusNum(robot, Long.valueOf(totalGold).intValue());
		robot.getRobotSlotCacheData().setBonusPubNum(0);
		robot.getRobotSlotCacheData().setBonusFree(false);
		
	}

}
