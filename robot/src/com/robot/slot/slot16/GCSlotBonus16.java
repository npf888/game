package com.robot.slot.slot16;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType16;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;
/**
 * 阿兹特克 累计拼图
 * @author JavaServer
 *
 */
public class GCSlotBonus16 extends GCSlotBonusBase{
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
			GCSlotType16 gCSlotType16 = (GCSlotType16)message;
			int cardNum = gCSlotType16.getCardNumber();
			//如果触发了小游戏 就把 bonus 个数放到这里
			int bet = robot.getRobotSlotCacheData().getBet();
			if(robot.getXLSService().isBonus(robot.getTemplateService(),slotType, cardNum)){
				int reward =robot.getXLSService().getReward(robot.getTemplateService(), slotType)/100;
				int obtain = reward*bet;
				//计算
				robot.getRobotSlotCacheData().setBonusFree(true);
				robot.getBonusHandler().triggerBonusNum(robot,0);
				robot.getBonusHandler().countBonusNum(robot, obtain);
				robot.getRobotSlotCacheData().setBonusFree(false);
			}
	}

}
