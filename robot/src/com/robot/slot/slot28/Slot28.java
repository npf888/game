package com.robot.slot.slot28;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.msg.GCSlotSlot;
import com.gameserver.slot.template.SlotsListTemplate;
import com.robot.Robot;
import com.robot.slot.GCSlotBase;
import com.robot.slot.RobotSlotCacheData;
/**
 * 海洋世界
 * 没有 scatter 免费转动
 * @author JavaServer
 *
 */
public class Slot28 extends GCSlotBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public synchronized void execute(Robot robot, GCSlotSlot gCSlotSlot,int slotId,int slotType) {
		
		/** 是否免费 */
		int free = gCSlotSlot.getFree();
		/** 免费次数 */
		int freeTimes = gCSlotSlot.getFreeTimes();
		/** 收益 */
		long rewardNum = gCSlotSlot.getRewardNum();
		
		RobotSlotCacheData robotSlotCacheData = robot.getRobotSlotCacheData();
		/**
		 * 赢取次数：在转动中赢得筹码的次数；
		 * 如果rewardNum 大于零 说明本次 赢得的筹码
		 */
		if(free == 0){
			logger.info("----------付费转动第一次接收--free:"+free+"--freeTimes:"+freeTimes+"--rewardNum:"+rewardNum+"----------");
		}else{
			logger.info("----------免费转动---------free:"+free+"--freeTimes:"+freeTimes+"--rewardNum:"+rewardNum+"----------");
		}
		boolean isBonusFree = robot.getRobotSlotCacheData().isBonusFree();
		logger.info("----------isBonusFree---------isBonusFree:"+isBonusFree);
		if(free == 0){
			if(rewardNum >0){
				//计算加一次
				robotSlotCacheData.setRewardCount(robotSlotCacheData.getRewardCount()+1);
				logger.info("----------1---------付费-setRewardCount:"+robotSlotCacheData.getRewardCount());
			}
			logger.info("----------2---------付费-free:"+free+"--freeTimes:"+freeTimes+"--rewardNum:"+rewardNum+"----------");
			robotSlotCacheData.setFreeTimes(freeTimes);
			SlotsListTemplate slotsListTemplate = robot.getXLSService().getTemplateFromXLS1(robot.getTemplateService(),slotId);
			//消耗筹码数：所有转动结束后所消耗的筹码；
			long originRewardUsed = robotSlotCacheData.getRewardUsed();
			robotSlotCacheData.setRewardUsed(originRewardUsed+robotSlotCacheData.getBet()*slotsListTemplate.getPayLinesNum());//TODO 乘以 多少线  几线 几线 老虎机是如何区分的
			//支付线筹码数：支付线中奖获得的筹码数量；
			long payCountOrigin = robotSlotCacheData.getPayCount();
			robotSlotCacheData.setPayCount(payCountOrigin+rewardNum);
			logger.info("----------3---------付费-payCount:"+robotSlotCacheData.getPayCount());
			
		}else{
			//这个老虎机特殊没有scatter 所以免费的转动获得的钱都属于 bonus
			long originGold = robot.getRobotSlotCacheData().getBonusNum();
			robot.getRobotSlotCacheData().setBonusNum(rewardNum+originGold);
			logger.info("----------5---------免费-BONUS---BonusNum:"+robot.getRobotSlotCacheData().getBonusNum());
			
		}
		
		//判断是否有小游戏
		int bonus28Num = robotSlotCacheData.getBonusPubNum();
		if(bonus28Num != 0){
			return;
		}
		//如果有小游戏 就不走这里
		robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
		
		
	}

}
