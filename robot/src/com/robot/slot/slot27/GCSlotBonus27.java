package com.robot.slot.slot27;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.CGSlotType29Bouns;
import com.gameserver.slot.msg.GCSlotType27BounsInfo;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;
/**
 * 犀牛
 * @author JavaServer
 *
 */
public class GCSlotBonus27 extends GCSlotBonusBase{
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
			robot.getRobotSlotCacheData().setBonusPubNum(3);
			GCSlotType27BounsInfo gCSlotType27BounsInfo = (GCSlotType27BounsInfo)message;
			int bounsNum = gCSlotType27BounsInfo.getBounsNum();
			long totalGold = gCSlotType27BounsInfo.getTotalGold();
			//如果触发了小游戏 就把 bonus 个数放到这里
			robot.getRobotSlotCacheData().setBonusFree(true);
			robot.getBonusHandler().triggerBonusNum(robot,0);
			robot.getBonusHandler().countBonusNum(robot, Long.valueOf(totalGold).intValue());
			robot.getRobotSlotCacheData().setBonusPubNum(0);
			robot.getRobotSlotCacheData().setBonusFree(false);
	}

}
