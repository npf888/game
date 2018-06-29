package com.robot.slot.slot13;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType13SendBouns;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;
/**
 * 宙斯 小游戏 
 * @author JavaServer
 *
 */
public class GCSlotBonus13Send extends GCSlotBonusBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		GCSlotType13SendBouns gCSlotType13SendBouns = (GCSlotType13SendBouns)message;
		long[] moneyOrTimes = gCSlotType13SendBouns.getMoneyOrTimes();
		int[] mtType = gCSlotType13SendBouns.getMtType();
		long allMoney = 0;
		long allTimes = 0;
		for(int i=0;i<moneyOrTimes.length;i++){
			if(mtType[i]==1){//是金币
				allMoney+=moneyOrTimes[i];
				//计算
			}else{//是次数
				allTimes+=moneyOrTimes[i];
			}
		}
		logger.info("宙斯小游戏 总共获得的 免费次数----------："+allTimes);
		robot.getRobotSlotCacheData().setBonusFree(true);
		robot.getBonusHandler().triggerBonusNum(robot,0);
		robot.getBonusHandler().countBonusNum(robot, (int)allMoney);
		robot.getRobotSlotCacheData().setBonusFree(false);
	}

	
}
