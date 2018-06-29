package com.robot.slot.slot17;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType17;
import com.gameserver.slot.msg.GCSlotType27BounsInfo;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;
/**
 * 狼老虎机
 * @author JavaServer
 *
 */
public class GCSlotBonus17 extends GCSlotBonusBase{
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
			robot.getRobotSlotCacheData().setBonusPubNum(3);
			GCSlotType17 gCSlotType17 = (GCSlotType17)message;
			long bounsNum = gCSlotType17.getBonusNum();
			//如果触发了小游戏 就把 bonus 个数放到这里
			robot.getRobotSlotCacheData().setBonusFree(true);
			robot.getBonusHandler().triggerBonusNum(robot,0);
			robot.getBonusHandler().countBonusNum(robot, Long.valueOf(bounsNum).intValue());
			robot.getRobotSlotCacheData().setBonusPubNum(0);
			robot.getRobotSlotCacheData().setBonusFree(false);
	}

}
