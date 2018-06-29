package com.robot.slot.bonus;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.robot.Robot;
import com.robot.slot.util.ExportValue;

/**
 * 计算 bonusNum
 * @author JavaServer
 *
 */
public class BonusHandler {
	 protected Logger logger = Loggers.slotLogger;
	/**
	 * 计算 钱  是属于bonus 还是属于  scatter
	 * @param robot
	 */
	public void countBonusNum(Robot robot,long reward){
		boolean wildFree = robot.getRobotSlotCacheData().isWildFree();
		boolean bonusFree = robot.getRobotSlotCacheData().isBonusFree();
		boolean scatterFree = robot.getRobotSlotCacheData().isScatterFree();
		//以bonus为主，如果bonnus 为ture 就算在它身上
		if(wildFree){
			robot.getRobotSlotCacheData().setWildNum(robot.getRobotSlotCacheData().getWildNum()+reward);
			logger.info("----------11---------wildFree:"+wildFree+"---setWildNum:"+robot.getRobotSlotCacheData().getWildNum());
		}else if(bonusFree){
//			ExportValue.exportValue("C:/img/bonusReward.txt","bonus="+ reward);
			long originBonusNum = robot.getRobotSlotCacheData().getBonusNum();
			robot.getRobotSlotCacheData().setBonusNum(originBonusNum+reward);
			logger.info("----------22---------bonusFree:"+bonusFree+"---setBonusNum:"+robot.getRobotSlotCacheData().getBonusNum());
		}else if(scatterFree){
			long sn = robot.getRobotSlotCacheData().getScatterNum();
			ExportValue.exportValue("C:/img/bonusReward.txt","sum="+sn+"--scatter="+ reward);
			robot.getRobotSlotCacheData().setScatterNum(robot.getRobotSlotCacheData().getScatterNum()+reward);
			logger.info("----------33---------scatterFree:"+scatterFree+"---setScatterNum:"+robot.getRobotSlotCacheData().getScatterNum());
		}
	}
	/**
	 * 触发 bonus 的那里
	 * @param robot
	 */
	public void triggerBonusNum(Robot robot,int bonusTimes){
		if(robot.getRobotSlotCacheData().getFree() == 0){//付费
			long originTimes = robot.getRobotSlotCacheData().getBonusTriggerCount();
			robot.getRobotSlotCacheData().setBonusTriggerCount(1+originTimes);
			if(bonusTimes>0){
				robot.getRobotSlotCacheData().setBonusFree(true);
			}
			logger.info("----------111---------BonusFree:"+true+"---BonusTriggerCount:"+robot.getRobotSlotCacheData().getBonusTriggerCount());
		}else{//免费
			long originTimes = robot.getRobotSlotCacheData().getBonusTriggerFreeCount();
			robot.getRobotSlotCacheData().setBonusTriggerFreeCount(1+originTimes);
			logger.info("----------222---------BonusTriggerFreeCount:"+robot.getRobotSlotCacheData().getBonusTriggerFreeCount());
		}
	}
	
}
