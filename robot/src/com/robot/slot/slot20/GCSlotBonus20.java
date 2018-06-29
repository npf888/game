package com.robot.slot.slot20;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType20BounsNew;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;
/**
 * 泰国象
 * @author JavaServer
 *
 */
public class GCSlotBonus20 extends GCSlotBonusBase{
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
			
		/*	GCSlotType20Bouns gCSlotType20Bouns = (GCSlotType20Bouns)message;
			*/
			/** 1：是           0：否 */
		/*
			int[]  multiples = gCSlotType20Bouns.getMultiples();
			int num = 1;
			for(int n : multiples){
				num = num*(n/100);
			}
			int allBet = num*robot.getRobotSlotCacheData().getBet();
			
			robot.getBonusHandler().triggerBonusNum(robot,3);
			robot.getBonusHandler().countBonusNum(robot, Long.valueOf(allBet).intValue());
			//如果bonus 没有免费次数，只是获得奖金，那么久吧 bonusFree设置为false
			robot.getRobotSlotCacheData().setBonusFree(false);*/
				
		GCSlotType20BounsNew gCSlotType20BounsNew = (GCSlotType20BounsNew)message;
		    /** 1：是           0：否 */
			int[]  isPictureArr = gCSlotType20BounsNew.getIsPicture();
			long[] rewardArr = gCSlotType20BounsNew.getReward();
			long[] samePictureGoldArr = gCSlotType20BounsNew.getSamePictureGold();
			long totalGold = gCSlotType20BounsNew.getTotalGold();
			
			robot.getBonusHandler().countBonusNum(robot, Long.valueOf(totalGold).intValue());
			//如果bonus 没有免费次数，只是获得奖金，那么久吧 bonusFree设置为false
			robot.getRobotSlotCacheData().setBonusFree(false);
		
	}

}
