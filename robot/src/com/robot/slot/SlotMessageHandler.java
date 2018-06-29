package com.robot.slot;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotEnter;
import com.gameserver.slot.msg.GCSlotSlot;
import com.robot.Robot;

public class SlotMessageHandler {
	protected Logger logger = Loggers.slotLogger;
	public  void execute(Robot robot, IMessage message) {
		
		int slotId = robot.getRobotSlotCacheData().getSlotId();
		int slotType = robot.getRobotSlotCacheData().getSlotType();
		logger.info("1-first返回转动信息-------------------");
		if(message instanceof GCSlotEnter){
			//转动老虎机
			//如果是三消老虎机  就用三消的发送消息
			if(slotType == 33){
				robot.getTurnDisSlot().turnDis(robot,slotId);
			}else{
				robot.getTurnSlot().turn(robot,slotId);
			}
		}else if(message instanceof GCSlotSlot){
			//根据类型 获取老虎机
			GCSlotBase slotbase = robot.getSlotMessageType().getSlot(slotType);
			GCSlotSlot GCSlotSlot= (GCSlotSlot)message;
			logger.info("2-gcSlotSlot返回转动信息-------------------"+GCSlotSlot.getFreeTimes());
			//处理老虎机的返回值，根据返回值 进行下一步操作
			slotbase.execute(robot, (GCSlotSlot)message,slotId,slotType);
		}else {
			logger.info("3-bonus返回转动信息-------------------"+message.getTypeName());
			//处理bonus小游戏
			GCSlotBonusBase gCSlotBonusBase = robot.getSlotMessageType().getBonus(message.getTypeName());
			if(gCSlotBonusBase != null){
				gCSlotBonusBase.execute(robot, message,slotId, slotType);
			}
		}
		
	}
	
	

	

}
