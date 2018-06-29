package com.robot.slot.slot29;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.slot.data.Bonus29Data;
import com.gameserver.slot.msg.CGSlotType29Bouns;
import com.gameserver.slot.msg.GCSlotType29BounsInfo;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus29Send extends GCSlotBonusBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		
		
		
		GCSlotType29BounsInfo gCSlotType29BounsInfo= (GCSlotType29BounsInfo)message;
		//返回消息
		/** 1:是龙子，0不是龙子 */
		int[] isSonArr = gCSlotType29BounsInfo.getIsSon();
		Bonus29Data[] Bonus29Datas = gCSlotType29BounsInfo.getBonus29Data();
		int totalGold = 0;
		for(int j=0;j<isSonArr.length;j++){
			int isSon = isSonArr[j];
			Bonus29Data bonus29 = Bonus29Datas[j];
			int[] golds = bonus29.getGold();
			//第三次  -- 这个第三次肯定 不是 龙子 -- 肯定停止
			if(j == 2 && isSon == 0){
				totalGold+=golds[0];
			}else{
				//（第一次） 或者  （ 第二次） 或者 （ 第三次同时 是龙子）   属于 同一种情况
				for(int i=0;i<golds.length;i++){
					totalGold+=golds[i];
				}
			}
		}
		logger.info("宙斯小游戏 总共获得的 钱----------："+totalGold);
		robot.getRobotSlotCacheData().setBonusFree(true);
		robot.getBonusHandler().triggerBonusNum(robot,0);
		robot.getBonusHandler().countBonusNum(robot, (int)totalGold);
		robot.getRobotSlotCacheData().setBonusFree(false);
		
	}
}
