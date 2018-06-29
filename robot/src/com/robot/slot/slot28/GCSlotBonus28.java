package com.robot.slot.slot28;

import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.slot.msg.CGSlotType28Bouns;
import com.gameserver.slot.msg.GCSlotType28Bouns;
import com.gameserver.slot.msg.GCSlotType28BounsInfo;
import com.gameserver.slot.template.BonusOceanRewardTemplate;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus28 extends GCSlotBonusBase{
	protected Logger logger = Loggers.slotLogger;
	boolean trigger = false;
	public GCSlotBonus28(boolean tg){
		this.trigger=tg;
	}
	
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		//第一次触发访问 bonus 小游戏
		if(trigger){
			GCSlotType28BounsInfo gCSlotType28BounsInfo = (GCSlotType28BounsInfo)message;
			int bounsNum = gCSlotType28BounsInfo.getBounsNum();
			int[] posList = gCSlotType28BounsInfo.getPosList();
			//如果触发了小游戏 就把 bonus 个数放到这里
			robot.getRobotSlotCacheData().setBonusPubNum(bounsNum);
			CGSlotType28Bouns cGSlotType28Bouns = new CGSlotType28Bouns();
			robot.getBonusHandler().triggerBonusNum(robot,0);
			robot.sendMessage(cGSlotType28Bouns);
			
		}else{
			//触发小游戏后 将 bonus28Num设置为0 ，以便于 老虎机的 主要转动流程 进行转动
			robot.getRobotSlotCacheData().setBonusPubNum(0);
			GCSlotType28Bouns gCSlotType28Bouns = (GCSlotType28Bouns)message;
			List<BonusOceanRewardTemplate> bonusOceanRewardTemplateList = robot.getXLSService().getTemplateFromXLS28(robot.getTemplateService(), slotType);
			BonusOceanRewardTemplate bonusOceanRewardTemplate = null;
			for(BonusOceanRewardTemplate bort :bonusOceanRewardTemplateList){
				if(bort.getId()==gCSlotType28Bouns.getRewardId()){
					bonusOceanRewardTemplate=bort;
				}
			}
			int totalGold = 0;
			int type = bonusOceanRewardTemplate.getType();
			int rewardNum = bonusOceanRewardTemplate.getRewardNum();
			int curBet = robot.getRobotSlotCacheData().getBet();
			if(type ==1 || type == 3){//直接赠送奖金
				totalGold = (curBet*rewardNum)/100;
				//Bonus筹码数：中bonus游戏获得的筹码数量；
				robot.getBonusHandler().countBonusNum(robot, Long.valueOf(totalGold).intValue());
				robot.getTurnSlot().turn(robot, slotId);
				//转下一次
			}else if(type == 2){//赠送转动次数
				totalGold = rewardNum/100;
				//免费转动
				robot.getRobotSlotCacheData().setFree(1);
				robot.getTurnSlot().turnFree(robot, totalGold, slotId);
			}
		}
		
	}

}
