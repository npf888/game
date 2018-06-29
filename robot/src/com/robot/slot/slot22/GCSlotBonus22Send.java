package com.robot.slot.slot22;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType22;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;
/**
 * 西部牛仔
 * @author JavaServer
 *
 */
public class GCSlotBonus22Send extends GCSlotBonusBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		GCSlotType22 gCSlotType22 = (GCSlotType22)message;
		long[] reward = gCSlotType22.getRewardNum();
		int allMoney = 0;
		for(int i=0;i<reward.length;i++){
				allMoney+=reward[i];
		}
		logger.info("西部牛仔小游戏 总共获得的 钱----------："+allMoney);
		robot.getRobotSlotCacheData().setBonusFree(true);
		robot.getBonusHandler().triggerBonusNum(robot,0);
		robot.getBonusHandler().countBonusNum(robot, (int)allMoney);
		robot.getRobotSlotCacheData().setBonusFree(false);
		
	}

}
