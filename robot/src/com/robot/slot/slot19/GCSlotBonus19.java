/*package com.robot.slot.slot19;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType19;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;
*//**
 * 水晶魔法宝石
 * @author JavaServer
 *
 *//*
public class GCSlotBonus19 extends GCSlotBonusBase{
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
			
			GCSlotType19 gCSlotType19 = (GCSlotType19)message;
			*//** 1：是           0：否 *//*
			int levelEnough = gCSlotType19.getLevelEnough();
			*//** 1.单线下注额的倍数 2.免费转动次数 *//*
			int mType = gCSlotType19.getMType();
			int mt = gCSlotType19.getMt();
			//如果级别足够
			if(levelEnough == 1){
				robot.getBonusHandler().triggerBonusNum(robot,0);
				//金钱
				if(mType == 1){
					//如果是付费的转动 所有钱 就属于 bonus 自己,但如果是免费的 就一定属于scatter
					//中奖
					robot.getBonusHandler().countBonusNum(robot,mt);
					robot.getRobotSlotCacheData().setBonusPubNum(0);
					robot.getRobotSlotCacheData().setBonusFree(false);
				}
				robot.getRobotSlotCacheData().setBonusPubNum(0);
			}
	}

}
*/