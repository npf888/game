package com.robot.slot.slot29;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.msg.GCSlotSlot;
import com.gameserver.slot.template.SlotsListTemplate;
import com.robot.Robot;
import com.robot.slot.GCSlotBase;
import com.robot.slot.RobotSlotCacheData;
/***
 * 西方龙
 * @author JavaServer
 *
 */
public class Slot29 extends GCSlotBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public synchronized void execute(Robot robot, GCSlotSlot gCSlotSlot,int slotId,int slotType) {
		//主转 流程
		robot.getgCSlotSlotHandler().handlerGCSlotSlot(robot,gCSlotSlot,slotId,slotType);
	
	}
}
