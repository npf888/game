package com.robot.slot.slot20;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType20;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;
/**
 * 泰国象的 wild 会产生自由转动的次数
 * @author JavaServer
 *
 */
public class GCSlotWild20 extends GCSlotBonusBase{

	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		GCSlotType20 gCSlotType20 = (GCSlotType20)message;
		//自由转动的次数
		int freeNum = gCSlotType20.getFreeNum();
		robot.getRobotSlotCacheData().setWildFreeTimes(freeNum);
		if(freeNum >0 ){
			robot.getRobotSlotCacheData().setBonusPubNum(freeNum);
//			robot.getWildHandler().triggerNum(robot);
			robot.getTurnSlot().turnMain(robot,robot.getRobotSlotCacheData().getBet(),2);
		}else{
			robot.getRobotSlotCacheData().setBonusPubNum(0);
		}
	}


}
