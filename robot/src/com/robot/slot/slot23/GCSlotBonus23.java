package com.robot.slot.slot23;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType23BounsInfo;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus23 extends GCSlotBonusBase{
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
			GCSlotType23BounsInfo gCSlotType23BounsInfo = (GCSlotType23BounsInfo)message;
			if(gCSlotType23BounsInfo.getBounsNum() < 3){
				return;
			}
			long reward = gCSlotType23BounsInfo.getObtainReward();
			robot.getRobotSlotCacheData().setBonusFree(true);
			robot.getBonusHandler().triggerBonusNum(robot,0);
			robot.getBonusHandler().countBonusNum(robot, Long.valueOf(reward).intValue());
			robot.getRobotSlotCacheData().setBonusFree(false);
		
	}

}
