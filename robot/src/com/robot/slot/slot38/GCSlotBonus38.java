package com.robot.slot.slot38;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.CGSlotType38Bonus;
import com.gameserver.slot.msg.GCSlotType38Bonus;
import com.gameserver.slot.msg.GCSlotType38BonusTrigger;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

/**
 * 万圣节的小游戏
 * @author JavaServer
 *
 */
public class GCSlotBonus38 extends GCSlotBonusBase{
	boolean trigger = false;
	public GCSlotBonus38(boolean tg){
		this.trigger=tg;
	}
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		if(trigger){
			//三个小游戏
			if(message instanceof GCSlotType38BonusTrigger){
				//返回消息
				GCSlotType38BonusTrigger gCSlotType38BonusTrigger = (GCSlotType38BonusTrigger)message;
				robot.getRobotSlotCacheData().setBonusPubNum(3);
				CGSlotType38Bonus CGSlotType38Bonus = new CGSlotType38Bonus();
				CGSlotType38Bonus.setIsGhost(3);
				robot.sendMessage(CGSlotType38Bonus);
			}
		}else{
			if(message instanceof GCSlotType38Bonus){
				GCSlotType38Bonus GCSlotType38Bonus = new GCSlotType38Bonus();
				long totalGold = GCSlotType38Bonus.getTotalGold();
				robot.getRobotSlotCacheData().setBonusFree(true);
				//中奖
				robot.getBonusHandler().countBonusNum(robot,totalGold);
				
				//触发小游戏后 ,同时 小游戏做完了，将 bonus26Num设置为0 ，以便于 老虎机的 主要转动流程 进行转动
				robot.getRobotSlotCacheData().setBonusPubNum(0);
				robot.getRobotSlotCacheData().setBonusFree(false);
				
				int freeTimes = robot.getRobotSlotCacheData().getFreeTimes();
				robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
			}
			
		}
			
		
	}

}
