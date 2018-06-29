package com.robot.slot.slot29;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.CGSlotType29Bouns;
import com.gameserver.slot.msg.GCSlotType29Bouns;
import com.gameserver.slot.msg.GCSlotType29BounsInfo;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus29 extends GCSlotBonusBase{
	boolean trigger = false;
	public GCSlotBonus29(boolean tg){
		this.trigger=tg;
	}
	
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		//第一次触发访问 bonus 小游戏
		if(trigger){
			GCSlotType29BounsInfo gCSlotType29BounsInfo = (GCSlotType29BounsInfo)message;
			int bounsNum = gCSlotType29BounsInfo.getBounsNum();
			int[] posList = gCSlotType29BounsInfo.getPosList();
			//如果触发了小游戏 就把 bonus 个数放到这里
			robot.getRobotSlotCacheData().setBonusPubNum(bounsNum);
			robot.getBonusHandler().triggerBonusNum(robot,0);
			CGSlotType29Bouns cGSlotType29Bouns = new CGSlotType29Bouns();
			robot.sendMessage(cGSlotType29Bouns);
		}else{
			boolean stop = false;
			int touchNum = robot.getRobotSlotCacheData().getTouchPubNum();
			
			//返回消息
			GCSlotType29Bouns gCSlotType29Bouns = (GCSlotType29Bouns)message;
			/** 1:是龙子，0不是龙子 */
			int isSon = gCSlotType29Bouns.getIsSon();
			int[] golds = gCSlotType29Bouns.getGold();
			//第三次  -- 这个第三次肯定 不是 龙子 -- 肯定停止
			if(touchNum == 3 && isSon == 0){
				robot.getBonusHandler().countBonusNum(robot, Long.valueOf(golds[0]).intValue());
				stop=true;
			}else{
				//（第一次） 或者  （ 第二次） 或者 （ 第三次同时 是龙子）   属于 同一种情况
				int totalGold = 0;
				for(int i=0;i<golds.length;i++){
					totalGold+=golds[i];
				}
				robot.getBonusHandler().countBonusNum(robot, Long.valueOf(totalGold).intValue());
			}
			if(isSon == 1){//是龙子 就停止游戏--- 肯定停止
				stop=true;
			}
			
			//第四次判断 怎么结束小游戏
			if(stop){
				robot.getRobotSlotCacheData().setTouchPubNum(1);
				//触发小游戏后 ,同时 小游戏做完了，将 bonus29Num设置为0 ，以便于 老虎机的 主要转动流程 进行转动
				robot.getRobotSlotCacheData().setBonusPubNum(0);
				int freeTimes = robot.getRobotSlotCacheData().getFreeTimes();
				robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
				return;
			}
			robot.getRobotSlotCacheData().setTouchPubNum(touchNum+1);
			robot.getRobotSlotCacheData().setBonusFree(false);
			CGSlotType29Bouns cGSlotType29Bouns = new CGSlotType29Bouns();
			robot.sendMessage(cGSlotType29Bouns);
		}
		
	}

}
