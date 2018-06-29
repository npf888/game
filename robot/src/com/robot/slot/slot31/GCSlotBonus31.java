package com.robot.slot.slot31;

import com.core.msg.IMessage;
import com.core.util.RandomUtil;
import com.gameserver.slot.msg.CGSlotType31Bouns;
import com.gameserver.slot.msg.GCSlotType31Bonus;
import com.gameserver.slot.msg.GCSlotType31BonusOne;
import com.gameserver.slot.msg.GCSlotType31BonusThree;
import com.gameserver.slot.msg.GCSlotType31BonusTwo;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus31 extends GCSlotBonusBase{
	boolean trigger = false;
	public GCSlotBonus31(boolean tg){
		this.trigger=tg;
	}
	
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		//第一次触发访问 bonus 小游戏
		if(trigger){
			GCSlotType31Bonus gCSlotType31Bonus = (GCSlotType31Bonus)message;
			int[] whichNum = gCSlotType31Bonus.getWhichNum();
			int theNum = RandomUtil.nextInt(0, whichNum.length);
			//如果触发了小游戏 就把 bonus 个数放到这里
			robot.getRobotSlotCacheData().setBonusPubNum(3);
			robot.getBonusHandler().triggerBonusNum(robot,0);
			CGSlotType31Bouns cGSlotType31Bouns = new CGSlotType31Bouns();
			cGSlotType31Bouns.setWhichNum(whichNum[theNum]);
			robot.sendMessage(cGSlotType31Bouns);
		}else{
			//三个小游戏
			if(message instanceof GCSlotType31BonusOne){
				//返回消息
				GCSlotType31BonusOne gCSlotType31BonusOne = (GCSlotType31BonusOne)message;
				
				int firstFew = gCSlotType31BonusOne.getFirstFew();
				long[] golds = gCSlotType31BonusOne.getGoldsOrNumList();
				int multiple = gCSlotType31BonusOne.getMultiple();
				int[] obtainFew = gCSlotType31BonusOne.getTypeList();
				
				int totalGolds = 0;
				for(int i=0;i<golds.length;i++){
					totalGolds+=golds[i];
				}
				robot.getRobotSlotCacheData().setBonusFree(true);
				//中奖
				robot.getBonusHandler().countBonusNum(robot, totalGolds*multiple/100);
				
				//触发小游戏后 ,同时 小游戏做完了，将 bonus26Num设置为0 ，以便于 老虎机的 主要转动流程 进行转动
				robot.getRobotSlotCacheData().setBonusPubNum(0);
				robot.getRobotSlotCacheData().setBonusFree(false);
				int freeTimes = robot.getRobotSlotCacheData().getFreeTimes();
				robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
			}else if(message instanceof GCSlotType31BonusTwo){
				//返回消息
				GCSlotType31BonusTwo gCSlotType31BonusTwo = (GCSlotType31BonusTwo)message;
				
				int[] rewardPoolList = 	gCSlotType31BonusTwo.getRewardPoolList();
				int[] matchNumList = gCSlotType31BonusTwo.getMatchNumList();
				long gold = gCSlotType31BonusTwo.getGold();
				robot.getRobotSlotCacheData().setBonusFree(true);
				//中奖
				robot.getBonusHandler().countBonusNum(robot, Long.valueOf(gold).intValue());
				
				//触发小游戏后 ,同时 小游戏做完了，将 bonus26Num设置为0 ，以便于 老虎机的 主要转动流程 进行转动
				robot.getRobotSlotCacheData().setBonusPubNum(0);
				robot.getRobotSlotCacheData().setBonusFree(false);
				int freeTimes = robot.getRobotSlotCacheData().getFreeTimes();
				robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
			}else if(message instanceof GCSlotType31BonusThree){
				//返回消息
				GCSlotType31BonusThree gCSlotType31BonusThree = (GCSlotType31BonusThree)message;
				
				long[] rewardNumList = gCSlotType31BonusThree.getRewardNumList();
				int[] rewardTypeList = gCSlotType31BonusThree.getRewardTypeList();
				int num = 0;
				for(int i=0;i<rewardNumList.length;i++){
					num+=rewardNumList[i];
				}
				robot.getRobotSlotCacheData().setBonusFree(true);
				//中奖
				robot.getBonusHandler().countBonusNum(robot, num);
				
				//触发小游戏后 ,同时 小游戏做完了，将 bonus26Num设置为0 ，以便于 老虎机的 主要转动流程 进行转动
				robot.getRobotSlotCacheData().setBonusPubNum(0);
				robot.getRobotSlotCacheData().setBonusFree(false);
				int freeTimes = robot.getRobotSlotCacheData().getFreeTimes();
				robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
				
			}
		}
		
	}

}
