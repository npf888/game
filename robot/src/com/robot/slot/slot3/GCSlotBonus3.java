/*package com.robot.slot.slot3;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType3BounsStart;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus3 extends GCSlotBonusBase{
	boolean trigger = false;
	public GCSlotBonus3(boolean tg){
		this.trigger=tg;
	}
	
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		//第一次触发访问 bonus 小游戏
		if(trigger){
			GCSlotType3BounsStart gCSlotType3BounsStart = (GCSlotType3BounsStart)message;
			//如果触发了小游戏 就把 bonus 个数放到这里
			robot.getRobotSlotCacheData().setBonusPubNum(3);
			robot.getBonusHandler().triggerBonusNum(robot,0);
			CGSlotType3Bouns cGSlotType3Bouns = new CGSlotType3Bouns();
			cGSlotType3Bouns.setRewardPool(1);
			robot.sendMessage(cGSlotType3Bouns);
		}else{
			//返回消息
			GCSlotType3Bouns gCSlotType3Bouns = (GCSlotType3Bouns)message;
			int touchNum = robot.getRobotSlotCacheData().getTouchPubNum();
			
			long gold = gCSlotType3Bouns.getCurrentGold();
			*//** 是否成功获取金币  1：是，0：否 *//*
			int isSuccess = gCSlotType3Bouns.getIsSuccess();
			
			robot.getBonusHandler().countBonusNum(robot, Long.valueOf(gold).intValue());
			
			if(touchNum >= 5 || isSuccess == 0){//结束不要发送消息了
				robot.getRobotSlotCacheData().setTouchPubNum(1);
				//触发小游戏后 ,同时 小游戏做完了，将 bonus30Num设置为0 ，以便于 老虎机的 主要转动流程 进行转动
				robot.getRobotSlotCacheData().setBonusPubNum(0);
				int freeTimes = robot.getRobotSlotCacheData().getFreeTimes();
				robot.getRobotSlotCacheData().setBonusFree(false);
				robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
			}else{
			//成功 接着玩（总共5次）
				CGSlotType3Bouns cGSlotType3Bouns = new CGSlotType3Bouns();
				robot.getRobotSlotCacheData().setTouchPubNum(touchNum+1);
				cGSlotType3Bouns.setRewardPool(touchNum+1);
				robot.sendMessage(cGSlotType3Bouns);
			}
			
		}
		
	}

}
*/