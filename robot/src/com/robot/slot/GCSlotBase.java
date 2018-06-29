package com.robot.slot;

import com.gameserver.slot.msg.GCSlotSlot;
import com.robot.Robot;

/**
 * 处理不同老虎机返回消息的基类
 * @author JavaServer
 *
 */
public abstract class GCSlotBase {

	//不同的子类处理不同的返回 消息
	public abstract void execute(Robot robot, GCSlotSlot gCSlotSlot,int slotId,int slotType);
		
	
}
