package com.robot.slot.slot1;

import javax.swing.plaf.synth.SynthRadioButtonMenuItemUI;

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
 * 第一个到第九个老虎机，他们的返回类型是一样的，处理方式一样 （注意：第八个 不一样，但是对于客户端 没有什么 影响，在这里用是没有问题的）
 */
public class Slot1 extends GCSlotBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public synchronized void  execute(Robot robot, GCSlotSlot gCSlotSlot,int slotId,int slotType) {
		//主转 流程
		robot.getgCSlotSlotHandler().handlerGCSlotSlot(robot,gCSlotSlot,slotId,slotType);
	}
}
