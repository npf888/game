package com.robot.slot;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.common.msg.GCMessage;
import com.gameserver.slot.enums.SlotTypeEnum;
import com.gameserver.slot.msg.GCFreeSlotReward;
import com.robot.Robot;
import com.robot.slot.slot14.GCSlotBonus14;

public abstract class GCSlotBonusBase {
	

	public abstract void execute(Robot robot, IMessage message, int slotId, int slotType);
}
