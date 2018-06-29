package com.robot.slot.slot27;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.msg.GCSlotSlot;
import com.gameserver.slot.template.SlotsListTemplate;
import com.robot.Robot;
import com.robot.slot.GCSlotBase;
import com.robot.slot.RobotSlotCacheData;
/**
 * 犀牛
 * @author JavaServer
 *
 */
public class Slot27 extends GCSlotBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public synchronized void execute(Robot robot, GCSlotSlot gCSlotSlot,int slotId,int slotType) {
		//主转 流程
		robot.getgCSlotSlotHandler().handlerGCSlotSlot(robot,gCSlotSlot,slotId,slotType);
	}

}
