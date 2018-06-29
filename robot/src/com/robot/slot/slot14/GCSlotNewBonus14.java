package com.robot.slot.slot14;

import com.core.msg.IMessage;
import com.gameserver.slot.data.Bonus14Data;
import com.gameserver.slot.msg.GCSlotType14AppleBonus;
import com.gameserver.slot.msg.GCSlotType14PreyBonus;
import com.gameserver.slot.msg.GCSlotType14RuneBonus;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotNewBonus14 extends GCSlotBonusBase{

	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		robot.getRobotSlotCacheData().setBonusPubNum(3);
		if(message instanceof GCSlotType14AppleBonus){
			GCSlotType14AppleBonus GCSlotType14AppleBonus = (GCSlotType14AppleBonus)message;
			Bonus14Data[] Bonus14DataArr = GCSlotType14AppleBonus.getBonus14Data();
			robot.getRobotSlotCacheData().setBonusFree(true);
			robot.getBonusHandler().triggerBonusNum(robot,0);
			robot.getBonusHandler().countBonusNum(robot, Bonus14DataArr[Bonus14DataArr.length-1].getOverlyingGold());
			robot.getRobotSlotCacheData().setBonusPubNum(0);
			robot.getRobotSlotCacheData().setBonusFree(false);
			
		}else if(message instanceof GCSlotType14RuneBonus){
			GCSlotType14RuneBonus GCSlotType14RuneBonus = (GCSlotType14RuneBonus)message;
			
			robot.getRobotSlotCacheData().setBonusFree(true);
			robot.getBonusHandler().triggerBonusNum(robot,0);
			robot.getBonusHandler().countBonusNum(robot, GCSlotType14RuneBonus.getTotalGold());
			robot.getRobotSlotCacheData().setBonusPubNum(0);
			robot.getRobotSlotCacheData().setBonusFree(false);
			
		}else if(message instanceof GCSlotType14PreyBonus){
			GCSlotType14PreyBonus GCSlotType14PreyBonus = (GCSlotType14PreyBonus)message;
			
			
			robot.getRobotSlotCacheData().setBonusFree(true);
			robot.getBonusHandler().triggerBonusNum(robot,0);
			robot.getBonusHandler().countBonusNum(robot, GCSlotType14PreyBonus.getTotalGold());
			robot.getRobotSlotCacheData().setBonusPubNum(0);
			robot.getRobotSlotCacheData().setBonusFree(false);
			
		}
		
		
	}

}
