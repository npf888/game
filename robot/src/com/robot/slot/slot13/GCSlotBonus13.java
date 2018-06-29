package com.robot.slot.slot13;

import com.core.msg.IMessage;
import com.gameserver.slot.handler.slot13.SmallGameType13;
import com.gameserver.slot.msg.CGSlotType13BounsPrize;
import com.gameserver.slot.msg.GCSlotType13Bouns;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus13 extends GCSlotBonusBase{
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		GCSlotType13Bouns gCSlotType13Bouns = (GCSlotType13Bouns)message;
			/** 是否成功 1 成功 0 失败 ,2开启小游戏 */
			int isSuccess = gCSlotType13Bouns.getIsSuccess();
			/** 可以抽奖次数 */
			int bounsNum = gCSlotType13Bouns.getBounsNum();
			/** 抽奖获得的金钱总数 */
			long money = gCSlotType13Bouns.getMoney();
			/** 点击获得免费次数 */
			long singleBounsNum = gCSlotType13Bouns.getSingleBounsNum();
			/** 点击获得的金钱总数 */
			long singleMoney = gCSlotType13Bouns.getSingleMoney();
			//开启小游戏
			if(isSuccess == SmallGameType13.Type2.getIndex()){
				//如果触发了小游戏 就把 bonus 个数放到这里
				robot.getRobotSlotCacheData().setBonusPubNum(3);
				robot.getBonusHandler().triggerBonusNum(robot,0);
				CGSlotType13BounsPrize cGSlotType13BounsPrize = new CGSlotType13BounsPrize();
				robot.sendMessage(cGSlotType13BounsPrize);
				return;
				
			//成功
			}else if(isSuccess == SmallGameType13.Type1.getIndex()){
				//返回消息
				//中奖
				robot.getBonusHandler().countBonusNum(robot, Long.valueOf(singleMoney).intValue());
				//触发小游戏后 ,同时 小游戏做完了，将 bonus25Num设置为0 ，以便于 老虎机的 主要转动流程 进行转动
				int freeTimes = robot.getRobotSlotCacheData().getFreeTimes();
				//只要大于零，就没完，接着发消息，接着玩
				if(bounsNum > 0){
					CGSlotType13BounsPrize cGSlotType13BounsPrize = new CGSlotType13BounsPrize();
					robot.sendMessage(cGSlotType13BounsPrize);
					return;
				}
				robot.getRobotSlotCacheData().setBonusPubNum(0);
				robot.getRobotSlotCacheData().setBonusFree(false);
				robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
				return;
			//失败就跳出小游戏继续转动
			}else{
				robot.getRobotSlotCacheData().setBonusPubNum(0);
				int freeTimes = robot.getRobotSlotCacheData().getFreeTimes();
				robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
				return;
			}
		
	}

}
