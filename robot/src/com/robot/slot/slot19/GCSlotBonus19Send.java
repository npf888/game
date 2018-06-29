package com.robot.slot.slot19;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.slot.ScatterCrystalService;
import com.gameserver.slot.msg.GCSlotType19;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;
/**
 * 水晶魔法 老虎机 小游戏
 * @author JavaServer
 *
 */
public class GCSlotBonus19Send extends GCSlotBonusBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		GCSlotType19 gCSlotType19 = (GCSlotType19)message;
		int levelEnough = gCSlotType19.getLevelEnough();
		if(levelEnough == ScatterCrystalService.level_enough_yes){
			long mt = gCSlotType19.getMt();
			int mType = gCSlotType19.getMType();
			/** 1.单线下注额的倍数 2.免费转动次数 */
			if(mType == 1){
				
				logger.info("宙斯小游戏 总共获得的 钱----------："+mt);
				robot.getRobotSlotCacheData().setScatterFree(true);
				robot.getBonusHandler().countBonusNum(robot, (int)mt);
				robot.getRobotSlotCacheData().setScatterFree(false);
			}/*else{
				logger.info("宙斯小游戏 总共获得的 次数----------："+mt);
				robot.getBonusHandler().triggerBonusNum(robot,(int)mt);
			}*/
		}
		
	}

}
