package com.robot.slot.slot30;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.CGSlotType28Bouns;
import com.gameserver.slot.msg.CGSlotType30Bouns;
import com.gameserver.slot.msg.GCSlotType30Bouns;
import com.gameserver.slot.msg.GCSlotType30BounsInfo;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus30 extends GCSlotBonusBase{
	boolean trigger = false;
	public GCSlotBonus30(boolean tg){
		this.trigger=tg;
	}
	
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		//第一次触发访问 bonus 小游戏
		if(trigger){
			GCSlotType30BounsInfo gCSlotType30BounsInfo = (GCSlotType30BounsInfo)message;
			int bounsNum = gCSlotType30BounsInfo.getBounsNum();
			//如果触发了小游戏 就把 bonus 个数放到这里
			robot.getRobotSlotCacheData().setBonusPubNum(bounsNum);
			CGSlotType30Bouns cGSlotType30Bouns = new CGSlotType30Bouns();
			robot.getBonusHandler().triggerBonusNum(robot,0);
			robot.sendMessage(cGSlotType30Bouns);
		}else{
			
			int touchNum = robot.getRobotSlotCacheData().getTouchPubNum();
			
			//返回消息
			GCSlotType30Bouns gCSlotType30Bouns = (GCSlotType30Bouns)message;
			
			int gold = gCSlotType30Bouns.getGold();
			int isSingleWin = gCSlotType30Bouns.getIsSingleWin();
			
			//中奖
			if(isSingleWin==1){
				robot.getBonusHandler().countBonusNum(robot, Long.valueOf(gold).intValue());
			}
			
			//第四次判断 怎么结束小游戏
			if(touchNum == 4){
				robot.getRobotSlotCacheData().setTouchPubNum(1);
				//触发小游戏后 ,同时 小游戏做完了，将 bonus30Num设置为0 ，以便于 老虎机的 主要转动流程 进行转动
				robot.getRobotSlotCacheData().setBonusPubNum(0);
				int freeTimes = robot.getRobotSlotCacheData().getFreeTimes();
				robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
				return;
			}
			robot.getRobotSlotCacheData().setTouchPubNum(touchNum+1);
			robot.getRobotSlotCacheData().setBonusFree(false);
			CGSlotType30Bouns cGSlotType30Bouns = new CGSlotType30Bouns();
			robot.sendMessage(cGSlotType30Bouns);
		}
		
	}

}
