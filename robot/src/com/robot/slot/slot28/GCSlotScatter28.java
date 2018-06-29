package com.robot.slot.slot28;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType28ScatterInfo;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotScatter28 extends GCSlotBonusBase{
	
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		GCSlotType28ScatterInfo gCSlotType28ScatterInfo = (GCSlotType28ScatterInfo)message;
			int[] rands = gCSlotType28ScatterInfo.getRands();
			long[] rewards = gCSlotType28ScatterInfo.getRewards();
			int[] specilScatter = gCSlotType28ScatterInfo.getSpecilScatter();
			robot.getBonusHandler().triggerBonusNum(robot, 1);
			robot.getBonusHandler().countBonusNum(robot, rewards[0]);
	}

}
