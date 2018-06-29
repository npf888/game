package com.robot.slot.slot38;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType33BonusList;
import com.gameserver.slot.msg.GCSlotType38Jackpot;
import com.gameserver.slot.msg.GCSlotType38Pumpkin;
import com.gameserver.slot.msg.GCSlotType38Wild;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

/**
 * 万圣节的小游戏
 * @author JavaServer
 *
 */
public class GCSlotOther38 extends GCSlotBonusBase{
	
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		
		
			//三个小游戏
			if(message instanceof GCSlotType38Jackpot){
				//返回消息
				GCSlotType38Jackpot gCSlotType38Jackpot = (GCSlotType38Jackpot)message;
				
				int[] jackpotList = gCSlotType38Jackpot.getJackpotList();
				long number = gCSlotType38Jackpot.getNumber();
				long reward1 = gCSlotType38Jackpot.getReward1();
				long reward2 = gCSlotType38Jackpot.getReward2();
				long totalgolds = reward1+reward2;
				
				
				robot.getRobotSlotCacheData().setBonusFree(true);
				robot.getBonusHandler().triggerBonusNum(robot,0);
				robot.getBonusHandler().countBonusNum(robot, totalgolds);
				robot.getRobotSlotCacheData().setBonusFree(false);
			}else if(message instanceof GCSlotType38Pumpkin){
				GCSlotType38Pumpkin gCSlotType38Pumpkin = new GCSlotType38Pumpkin();
				long totalGold = gCSlotType38Pumpkin.getTotalGold();
				
				
				robot.getRobotSlotCacheData().setBonusFree(true);
				robot.getBonusHandler().triggerBonusNum(robot,0);
				robot.getBonusHandler().countBonusNum(robot, totalGold);
				robot.getRobotSlotCacheData().setBonusFree(false);
				
			}else if(message instanceof GCSlotType38Wild){
				GCSlotType38Wild gCSlotType38Wild = new GCSlotType38Wild();
				gCSlotType38Wild.setMultiple(gCSlotType38Wild.getMultiple());
			}
		
	}

}
