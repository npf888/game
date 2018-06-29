package com.robot.slot.slot30;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType30BounsInfo;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus30Send extends GCSlotBonusBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		GCSlotType30BounsInfo gCSlotType30BounsInfo = (GCSlotType30BounsInfo)message;
		int[] times = gCSlotType30BounsInfo.getTimes();
		long[] reward = gCSlotType30BounsInfo.getReward();
		int allMoney = 0;
		for(int i=0;i<reward.length;i++){
				allMoney+=reward[i];
		}
		logger.info("宙斯小游戏 总共获得的 钱----------："+allMoney);
		robot.getRobotSlotCacheData().setBonusFree(true);
		robot.getBonusHandler().triggerBonusNum(robot,0);
		robot.getBonusHandler().countBonusNum(robot, (int)allMoney);
		robot.getRobotSlotCacheData().setBonusFree(false);
		
	}

}
