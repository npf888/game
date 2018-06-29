package com.robot.slot.slot26;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.CGSlotType26Bouns;
import com.gameserver.slot.msg.GCSlotType26Bouns;
import com.gameserver.slot.msg.GCSlotType26BounsInfo;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus26 extends GCSlotBonusBase{
	boolean trigger = false;
	public GCSlotBonus26(boolean tg){
		this.trigger=tg;
	}
	
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		//第一次触发访问 bonus 小游戏
		if(trigger){
			GCSlotType26BounsInfo gCSlotType26BounsInfo = (GCSlotType26BounsInfo)message;
			int bounsNum = gCSlotType26BounsInfo.getBounsNum();
			int[] posList = gCSlotType26BounsInfo.getPosList();
			//如果触发了小游戏 就把 bonus 个数放到这里
			robot.getRobotSlotCacheData().setBonusPubNum(bounsNum);
			robot.getBonusHandler().triggerBonusNum(robot,0);
			CGSlotType26Bouns cGSlotType26Bouns = new CGSlotType26Bouns();
			robot.sendMessage(cGSlotType26Bouns);
		}else{
			//返回消息
			GCSlotType26Bouns gCSlotType26Bouns = (GCSlotType26Bouns)message;
			
			int totalGold = gCSlotType26Bouns.getTotalGold();
			//中奖
			robot.getBonusHandler().countBonusNum(robot, Long.valueOf(totalGold).intValue());
			
			//触发小游戏后 ,同时 小游戏做完了，将 bonus26Num设置为0 ，以便于 老虎机的 主要转动流程 进行转动
			robot.getRobotSlotCacheData().setBonusPubNum(0);
			robot.getRobotSlotCacheData().setBonusFree(false);
			int freeTimes = robot.getRobotSlotCacheData().getFreeTimes();
			robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
		}
		
	}

}
