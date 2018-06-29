package com.robot.slot.slot21;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.CGSlotType21Bouns;
import com.gameserver.slot.msg.CGSlotType30Bouns;
import com.gameserver.slot.msg.GCSlotType21Bouns;
import com.gameserver.slot.msg.GCSlotType21BounsInfo;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;
/**
 * 老虎
 * @author JavaServer
 *
 */
public class GCSlotBonus21 extends GCSlotBonusBase{
	boolean trigger = false;
	public GCSlotBonus21(boolean tg){
		this.trigger=tg;
	}
	
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		//第一次触发访问 bonus 小游戏
		if(trigger){
			GCSlotType21BounsInfo GCSlotType21BounsInfo = (GCSlotType21BounsInfo)message;
			int bounsNum = GCSlotType21BounsInfo.getBounsNum();
			//如果触发了小游戏 就把 bonus 个数放到这里
			robot.getRobotSlotCacheData().setBonusPubNum(bounsNum);
			CGSlotType21Bouns cGSlotType21Bouns = new CGSlotType21Bouns();
			robot.getBonusHandler().triggerBonusNum(robot,0);
			robot.sendMessage(cGSlotType21Bouns);
		}else{
			//返回消息
			GCSlotType21Bouns gCSlotType21Bouns = (GCSlotType21Bouns)message;
			/** 是否匹配：1 是，0 否 */
			int isMatch = gCSlotType21Bouns.getIsMatch();
			long totalGold = gCSlotType21Bouns.getTotalGold();
			
			//如果不匹配
			if(isMatch==0){
				CGSlotType21Bouns cGSlotType21Bouns = new CGSlotType21Bouns();
				robot.sendMessage(cGSlotType21Bouns);
				return;
			}
			robot.getBonusHandler().countBonusNum(robot, Long.valueOf(totalGold).intValue());
			robot.getRobotSlotCacheData().setBonusFree(false);
			//触发小游戏后 ,同时 小游戏做完了，将 bonus30Num设置为0 ，以便于 老虎机的 主要转动流程 进行转动
			robot.getRobotSlotCacheData().setBonusPubNum(0);
			int freeTimes = robot.getRobotSlotCacheData().getFreeTimes();
			robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
		}
		
	}

}
