package com.robot.slot.slot33;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType33BonusList;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

/**
 * 小红帽的小游戏
 * @author JavaServer
 *
 */
public class GCSlotBonus33 extends GCSlotBonusBase{
	
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
			//三个小游戏
			if(message instanceof GCSlotType33BonusList){
				//返回消息
				GCSlotType33BonusList gCSlotType33BonusList = (GCSlotType33BonusList)message;
				
				long[] rewardList = gCSlotType33BonusList.getReward();
				int[] rollTypeList = gCSlotType33BonusList.getRollType();
				int selectNum = gCSlotType33BonusList.getSelectNum();
				int totalgolds = 0;
				for(int i=0;i<rewardList.length;i++){
					totalgolds+=rewardList[i];
				}
				//中奖
				robot.getBonusHandler().countBonusNum(robot, totalgolds);
				
				//触发小游戏后 ,同时 小游戏做完了，将 bonus26Num设置为0 ，以便于 老虎机的 主要转动流程 进行转动
				robot.getRobotSlotCacheData().setBonusPubNum(0);
				robot.getRobotSlotCacheData().setBonusFree(false);
				robot.getTurnDisSlot().turnDis(robot, slotId);
		}
		
	}

}
