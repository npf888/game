package com.robot.slot.slot10;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.slot.msg.CGFreeSlotReward;
import com.gameserver.slot.msg.GCSlotType10Scatter;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

/**
 * 马来网红自己的结算
 * @author JavaServer
 *
 */
public class GCFreeSlot10RewardEnd extends GCSlotBonusBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		if(message instanceof GCSlotType10Scatter){
			GCSlotType10Scatter gCSlotType10Scatter = (GCSlotType10Scatter)message;
			int boxId = gCSlotType10Scatter.getPos();
			robot.getRobotSlotCacheData().setBoxId(boxId);
			
			//免费转动次数结束以后才能 下边的消息，必须先缓存起来
			/*CGFreeSlotReward cGFreeSlotReward = new CGFreeSlotReward();
			cGFreeSlotReward.setPos(boxId);
			robot.sendMessage(cGFreeSlotReward);*/
		}
		
	}

}
