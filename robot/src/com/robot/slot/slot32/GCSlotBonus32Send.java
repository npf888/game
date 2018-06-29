package com.robot.slot.slot32;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType26BounsInfo;
import com.gameserver.slot.msg.GCSlotType32SocialContact;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus32Send  extends GCSlotBonusBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		GCSlotType32SocialContact gCSlotType32SocialContact = (GCSlotType32SocialContact)message;
		int freeNum = gCSlotType32SocialContact.getFreeNum();
		logger.info("斯巴达 自由转动的次数----------："+freeNum);
		robot.getBonusHandler().triggerBonusNum(robot,0);
		if(freeNum >0 ){
			robot.getRobotSlotCacheData().setBonusPubNum(freeNum);
			robot.getTurnSlot().turnMain(robot,robot.getRobotSlotCacheData().getBet(),2);
		}else{
			robot.getRobotSlotCacheData().setBonusPubNum(0);
		}
	}

}
