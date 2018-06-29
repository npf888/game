package com.robot.slot.slot26;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.slot.msg.GCSlotSlot;
import com.robot.Robot;
import com.robot.slot.GCSlotBase;
/**
 * 女巫魔法
 * @author JavaServer
 *
 */
public class Slot26 extends GCSlotBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public synchronized void execute(Robot robot, GCSlotSlot gCSlotSlot,int slotId,int slotType) {
		//主转 流程
		robot.getgCSlotSlotHandler().handlerGCSlotSlot(robot,gCSlotSlot,slotId,slotType);
	}
}
