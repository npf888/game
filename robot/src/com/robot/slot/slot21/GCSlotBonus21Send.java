package com.robot.slot.slot21;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType21BounsInfo;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;
/**
 * 老虎 老虎机
 * @author 牛鹏飞
 *
 */
public class GCSlotBonus21Send extends GCSlotBonusBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		GCSlotType21BounsInfo gCSlotType21BounsInfo = (GCSlotType21BounsInfo)message;
		int[] rewardPoolArr = gCSlotType21BounsInfo.getRewardPool();
		long[] rewardTimeArr = gCSlotType21BounsInfo.getRewardTimeList();
		
		int[] isMathArr = gCSlotType21BounsInfo.getIsMatch();
		long[] totalGoldArr = gCSlotType21BounsInfo.getTotalGold();
		int allMoney = 0;
		for(int i=0;i<totalGoldArr.length;i++){
				allMoney+=totalGoldArr[i];
		}
		logger.info("宙斯小游戏 总共获得的 钱----------："+allMoney);
		robot.getRobotSlotCacheData().setBonusFree(true);
		robot.getBonusHandler().triggerBonusNum(robot,0);
		robot.getBonusHandler().countBonusNum(robot, (int)allMoney);
		robot.getRobotSlotCacheData().setBonusFree(false);
	}

}
