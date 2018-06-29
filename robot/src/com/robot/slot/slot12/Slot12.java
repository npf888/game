package com.robot.slot.slot12;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.slot.msg.CGSlotType12Free;
import com.gameserver.slot.msg.GCSlotSlot;
import com.robot.Robot;
import com.robot.slot.GCSlotBase;
/**
 * 维密老虎机
 * @author JavaServer
 *
 */
public class Slot12 extends GCSlotBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public synchronized void execute(Robot robot, GCSlotSlot gCSlotSlot,int slotId,int slotType) {
		//主转 流程
		robot.getgCSlotSlotHandler().handlerGCSlotSlot12(robot,gCSlotSlot,slotId,slotType);
		
		
	}

}
