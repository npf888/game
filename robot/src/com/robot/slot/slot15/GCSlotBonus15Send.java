package com.robot.slot.slot15;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType15BounsStart;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;
/**
 *  狮身人面像老虎机 小游戏
 * @author JavaServer
 *
 */
public class GCSlotBonus15Send extends GCSlotBonusBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		GCSlotType15BounsStart gCSlotType15BounsStart = (GCSlotType15BounsStart)message;
		long[] currentGolds = gCSlotType15BounsStart.getCurrentGold();
		int allMoney = 0;
		for(int i=0;i<currentGolds.length;i++){
				allMoney+=currentGolds[i];
		}
		logger.info("宙斯小游戏 总共获得的 钱----------："+allMoney);
		robot.getRobotSlotCacheData().setBonusFree(true);
		robot.getBonusHandler().triggerBonusNum(robot,0);
		robot.getBonusHandler().countBonusNum(robot, (int)allMoney);
		robot.getRobotSlotCacheData().setBonusFree(false);
		
	}

}
