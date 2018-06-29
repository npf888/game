package com.robot.slot.slot1;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCFreeSlotReward;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

/**
 * 盒子的结算
 * @author JavaServer
 *
 */
public class GCFreeSlotRewardEnd extends GCSlotBonusBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		if(message instanceof GCFreeSlotReward){
			GCFreeSlotReward gCFreeSlotReward = (GCFreeSlotReward)message;
			long rewardNum = gCFreeSlotReward.getRewardNum();
			robot.getBonusHandler().countBonusNum(robot,Long.valueOf(rewardNum).intValue());
			logger.info("----------盒子的结尾---------rewardNum:"+rewardNum);
			robot.getRobotSlotCacheData().setFree(0);
			robot.getRobotSlotCacheData().setBonusPubNum(0);
			robot.getTurnSlot().turn(robot, slotId);
		}
		
	}

}
