package com.robot.slot.slot12;

import java.util.Random;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.slot.msg.CGSlotType12Choose;
import com.gameserver.slot.msg.CGSlotType12Free;
import com.gameserver.slot.msg.GCSlotType12;
import com.gameserver.slot.msg.GCSlotType12Choose;
import com.gameserver.slot.msg.GCSlotType12Free;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

/**
 * 维密    GCSlotType12Choose   GCSlotType12Free
 * @author JavaServer
 *
 */
public class GCSlotBonus12 extends GCSlotBonusBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		if(message instanceof GCSlotType12){//转动老虎机的过程中 返回的消息
			GCSlotType12 gCSlotType12 = (GCSlotType12)message;
			robot.getRobotSlotCacheData().setBonusPubNum(3);
			int[] ids = gCSlotType12.getSlotsScatterData();
			Random random = new Random();
			int i = random.nextInt(ids.length);
			//去选择 卡片
			CGSlotType12Choose cGSlotType12Choose = new CGSlotType12Choose();
			cGSlotType12Choose.setId(ids[i]);
			robot.sendMessage(cGSlotType12Choose);
		}else if(message instanceof GCSlotType12Choose){
			GCSlotType12Choose gCSlotType12Choose = (GCSlotType12Choose)message;
			int freeNum = gCSlotType12Choose.getFreeNum();
			//加上自由转动
			long scatterTriggerFreeCount = robot.getRobotSlotCacheData().getScatterTriggerFreeCount();
			robot.getRobotSlotCacheData().setScatterTriggerFreeCount(scatterTriggerFreeCount+freeNum);
			robot.getRobotSlotCacheData().setBonusPubNum(0);
			//去自由转动
			robot.getTurnSlot().turnFree(robot,freeNum, slotId);
			
		}else if(message instanceof GCSlotType12Free){//最后的处理
			GCSlotType12Free gCSlotType12Free = (GCSlotType12Free)message;
			long rewardNum = gCSlotType12Free.getRewardNum();
			int multiple = gCSlotType12Free.getMultiple();
			//计算本次免费转动scatter 的 奖金
			long scatterNum = robot.getRobotSlotCacheData().getScatterNum();
			robot.getRobotSlotCacheData().setScatterNum(scatterNum+rewardNum*multiple);
			int freeTimes = robot.getRobotSlotCacheData().getFreeTimes();
			robot.getRobotSlotCacheData().setBonusPubNum(0);
			//去付费转动
			logger.info("最终计算 ---- 然后进行下一次 付费转动   ----------------   freeTimes::"+freeTimes);
			robot.getTurnSlot().turnFree(robot,freeTimes,slotId);
		}
		
	}

	
}
