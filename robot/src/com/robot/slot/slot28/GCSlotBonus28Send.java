package com.robot.slot.slot28;

import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.slot.msg.GCSlotType28BounsInfo;
import com.gameserver.slot.template.BonusOceanRewardTemplate;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus28Send extends GCSlotBonusBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		GCSlotType28BounsInfo gCSlotType28BounsInfo = (GCSlotType28BounsInfo)message;
		List<BonusOceanRewardTemplate> bonusOceanRewardTemplateList = robot.getXLSService().getTemplateFromXLS28(robot.getTemplateService(), slotType);
		BonusOceanRewardTemplate bonusOceanRewardTemplate = null;
		for(BonusOceanRewardTemplate bort :bonusOceanRewardTemplateList){
			if(bort.getId()==gCSlotType28BounsInfo.getRewardId()){
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
			robot.getRobotSlotCacheData().setBonusFree(true);
			robot.getBonusHandler().triggerBonusNum(robot,0);
			robot.getBonusHandler().countBonusNum(robot, (int)totalGold);
			robot.getRobotSlotCacheData().setBonusFree(false);
			//转下一次
		}else if(type == 2){//赠送转动次数
			totalGold = rewardNum/100;
			//免费转动
			robot.getBonusHandler().triggerBonusNum(robot,totalGold);
			robot.getBonusHandler().countBonusNum(robot, (int)totalGold);
		}
		logger.info("宙斯小游戏 总共获得的 钱----------："+totalGold);
		
		
		
	}

}
