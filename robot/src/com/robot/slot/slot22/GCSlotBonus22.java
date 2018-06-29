/*package com.robot.slot.slot22;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.CGSlotType21Bouns;
import com.gameserver.slot.msg.CGSlotType22;
import com.gameserver.slot.msg.GCSlotType21Bouns;
import com.gameserver.slot.msg.GCSlotType22;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;
*//**
 * 西部牛仔
 * @author JavaServer
 *
 *//*
public class GCSlotBonus22 extends GCSlotBonusBase{
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		//第一次触发访问 bonus 小游戏
			GCSlotType22 gCSlotType22 = (GCSlotType22)message;
			*//*** 剩余次数 ***//*
			int remaining = gCSlotType22.getRemaining();
			*//** 总收益 *//*
			long rewardNum =0;//gCSlotType22.getRewardNum();
			int templateRewardNum = robot.getXLSService().getScatterCowboyRewardNum(robot.getTemplateService(), slotType);
			if(remaining == templateRewardNum){//如果相等说明第一次触发 bonus小游戏
				//如果触发了小游戏 就把 bonus 个数放到这里
				robot.getRobotSlotCacheData().setBonusPubNum(templateRewardNum);
				robot.getBonusHandler().triggerBonusNum(robot,0);
				CGSlotType22 cGSlotType22 = new CGSlotType22();
				cGSlotType22.setBet(robot.getRobotSlotCacheData().getBet());
				robot.sendMessage(cGSlotType22);
				return;
			}
			//如果剩余次数大于零 就可以接着玩
			if(remaining > 0){
				CGSlotType22 cGSlotType22 = new CGSlotType22();
				cGSlotType22.setBet(robot.getRobotSlotCacheData().getBet());
				robot.sendMessage(cGSlotType22);
				return;
			}
			
			//rewardNum 这个参数为  玩完整个bonus 小游戏 获得的总奖励，所以在最后玩完后 加上就行
			robot.getBonusHandler().countBonusNum(robot, Long.valueOf(rewardNum).intValue());
			robot.getRobotSlotCacheData().setBonusFree(false);
			//触发小游戏后 ,同时 小游戏做完了，将 bonus30Num设置为0 ，以便于 老虎机的 主要转动流程 进行转动
			robot.getRobotSlotCacheData().setBonusPubNum(0);
			int freeTimes = robot.getRobotSlotCacheData().getFreeTimes();
			robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
		
	}

}
*/