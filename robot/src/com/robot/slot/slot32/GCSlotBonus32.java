package com.robot.slot.slot32;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType32Bonus;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus32 extends GCSlotBonusBase{
	
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
			//三个小游戏
			if(message instanceof GCSlotType32Bonus){
				//返回消息
				GCSlotType32Bonus gCSlotType32Bonus = (GCSlotType32Bonus)message;
				
				int[] levelList = gCSlotType32Bonus.getLevelList();
				int[] isSuccessList = gCSlotType32Bonus.getIsSuccessList();
				long[] rewardNumList = gCSlotType32Bonus.getRewardNumList();
				int totalgolds = 0;
				for(int i=0;i<rewardNumList.length;i++){
					totalgolds+=rewardNumList[i];
				}
				robot.getRobotSlotCacheData().setBonusFree(true);
				//中奖
				robot.getBonusHandler().countBonusNum(robot, totalgolds);
				
				//触发小游戏后 ,同时 小游戏做完了，将 bonus26Num设置为0 ，以便于 老虎机的 主要转动流程 进行转动
				robot.getRobotSlotCacheData().setBonusPubNum(0);
				robot.getRobotSlotCacheData().setBonusFree(false);
				int freeTimes = robot.getRobotSlotCacheData().getFreeTimes();
				robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
		}
		
	}

}
