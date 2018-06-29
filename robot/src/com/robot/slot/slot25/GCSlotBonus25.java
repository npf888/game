package com.robot.slot.slot25;

import java.util.Random;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.CGSlotType25Bouns;
import com.gameserver.slot.msg.GCSlotType25BounsInfo;
import com.gameserver.slot.msg.GCSlotType25Bouns;
import com.gameserver.slot.msg.GCSlotType25BounsInfo;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus25 extends GCSlotBonusBase{
	boolean trigger = false;
	public GCSlotBonus25(boolean tg){
		this.trigger=tg;
	}
	
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		//第一次触发访问 bonus 小游戏
		if(trigger){
			GCSlotType25BounsInfo gCSlotType25BounsInfo = (GCSlotType25BounsInfo)message;
			int bounsNum = gCSlotType25BounsInfo.getBounsNum();
			int[] posList = gCSlotType25BounsInfo.getPosList();
			//如果触发了小游戏 就把 bonus 个数放到这里
			robot.getRobotSlotCacheData().setBonusPubNum(bounsNum);
			robot.getRobotSlotCacheData().setBonusFree(true);
			robot.getBonusHandler().triggerBonusNum(robot,0);
			CGSlotType25Bouns cGSlotType25Bouns = new CGSlotType25Bouns();
			//随机一个位置传到后台（相当于用户选择了一个位置）
			int position = new Random().nextInt(5);
			cGSlotType25Bouns.setPosition(position);
			robot.sendMessage(cGSlotType25Bouns);
		}else{
			//返回消息
			GCSlotType25Bouns gCSlotType25Bouns = (GCSlotType25Bouns)message;
			/** 是否匹配：1 是，0否 */
			//不管匹配 或者 不匹配 都有奖励，只是大小的区别，然后结束游戏
			int isMatch = gCSlotType25Bouns.getIsMatch();
			long totalGold = gCSlotType25Bouns.getTotalGold();
			//中奖
			robot.getBonusHandler().countBonusNum(robot, Long.valueOf(totalGold).intValue());
			
			//触发小游戏后 ,同时 小游戏做完了，将 bonus25Num设置为0 ，以便于 老虎机的 主要转动流程 进行转动
			robot.getRobotSlotCacheData().setBonusPubNum(0);
			robot.getRobotSlotCacheData().setBonusFree(false);
			int freeTimes = robot.getRobotSlotCacheData().getFreeTimes();
			robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
		}
		
	}

}
