package com.robot.slot.slot20;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.slot.enums.SlotTypeEnum;
import com.gameserver.slot.msg.GCSlotSlot;
import com.gameserver.slot.template.SlotsListTemplate;
import com.robot.Robot;
import com.robot.slot.GCSlotBase;
import com.robot.slot.RobotSlotCacheData;
/**
 * 泰国象
 * @author JavaServer
 *
 */
public class Slot20 extends GCSlotBase{

	protected Logger logger = Loggers.slotLogger;
	@Override
	public synchronized void execute(Robot robot, GCSlotSlot gCSlotSlot,int slotId,int slotType) {
			/** 是否免费 */
			int free = gCSlotSlot.getFree();
			/** 免费次数 */
			int freeTimes = gCSlotSlot.getFreeTimes();
			/** 收益 */
			long rewardNum = gCSlotSlot.getRewardNum();
			/** 元素列表 */
			int[] slotElementList = gCSlotSlot.getSlotElementList();
			
			if(free == 0){
				logger.info("----------付费转动第一次接收--free:"+free+"--freeTimes:"+freeTimes+"--rewardNum:"+rewardNum+"----------");
			}else{
				logger.info("----------免费转动---------free:"+free+"--freeTimes:"+freeTimes+"--rewardNum:"+rewardNum+"----------");
			}
			boolean isBonusFree = robot.getRobotSlotCacheData().isBonusFree();
			boolean isScatterFree = robot.getRobotSlotCacheData().isScatterFree();
			logger.info("----------isBonusFree---------isBonusFree:"+isBonusFree);
			logger.info("----------isScatterFree---------isScatterFree:"+isScatterFree);
			//这个用于控制 最后一次计算钱
			boolean isOpen = true;
			RobotSlotCacheData robotSlotCacheData = robot.getRobotSlotCacheData();
			//获取scatter 的数量
			int scatterNum = getScatterNum(slotElementList,slotType);
			int scatterMin = getScatterMin(robot,slotType);
			//付费转动   free == 2 是 泰国象专属
			if(free == 0 || free == 2){
				/**
				 * 先处理 是属于 scatter 还是 bonus
				 */
				if(free == 0 && freeTimes > 0){
					robotSlotCacheData.setScatterFree(true);
					robotSlotCacheData.setScatterTimes(freeTimes);
					logger.info("----------1---------付费-free:"+free+"--freeTimes:"+freeTimes+"--rewardNum:"+rewardNum+"----------");
				}else if(free == 0){
					robotSlotCacheData.setScatterTimes(0);
				}
				if(scatterNum >= scatterMin){
					//泰国象特殊点
					if(free == 0){
						//scatter的次数：scatter游戏触发的次数；
						robotSlotCacheData.setScatterTriggerCount(robotSlotCacheData.getScatterTriggerCount()+1);
						logger.info("----------2---------付费-scatterNum:"+scatterNum+"--scatterMin:"+scatterMin);
					}
				}
				
				
				/**
				 * 赢取次数：在转动中赢得筹码的次数；
				 * 如果rewardNum 大于零 说明本次 赢得的筹码
				 */
				if(rewardNum >0){
					//计算加一次
					robotSlotCacheData.setRewardCount(robotSlotCacheData.getRewardCount()+1);
					logger.info("----------3---------付费-setRewardCount:"+robotSlotCacheData.getRewardCount());
				}
				SlotsListTemplate slotsListTemplate = robot.getXLSService().getTemplateFromXLS1(robot.getTemplateService(),slotId);
				//消耗筹码数：所有转动结束后所消耗的筹码；
				robotSlotCacheData.setRewardUsed(robotSlotCacheData.getRewardUsed()+robotSlotCacheData.getBet()*slotsListTemplate.getPayLinesNum());//TODO 乘以 多少线  几线 几线 老虎机是如何区分的
				//支付线筹码数：支付线中奖获得的筹码数量；
				robotSlotCacheData.setPayCount(robotSlotCacheData.getPayCount()+rewardNum);
				//设置免费转动次数
				
				logger.info("----------4---------付费-setRewardUsed:"+robotSlotCacheData.getRewardUsed());
				logger.info("----------4---------付费-setPayCount:"+robotSlotCacheData.getPayCount());
			//免费转动
			}else{
				if(scatterNum >= scatterMin){
					//免费  scatter的次数：scatter游戏触发的次数；
					robotSlotCacheData.setScatterTriggerFreeCount(robotSlotCacheData.getScatterTriggerFreeCount()+1);
					logger.info("----------7---------免费-SCATTER---ScatterTriggerFreeCount:"+robotSlotCacheData.getScatterTriggerCount());
				}
				if(isOpen){
					robot.getBonusHandler().countBonusNum(robot,Long.valueOf(rewardNum).intValue());
					
				}
				//中奖
			}
			robot.getRobotSlotCacheData().setFreeTimes(freeTimes);
			//免费转动
			int bonusNum = robotSlotCacheData.getBonusPubNum();
			if(bonusNum > 0){
				return;
			}
			
			robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
		}
	/**
	 * 获取 最小的scatterNum
	 * 有些老虎极可能是特殊的
	 * @param robot
	 * @param slotType
	 * @return
	 */
	private int getScatterMin(Robot robot,int slotType) {
		if(slotType == SlotTypeEnum.SlotType12.getIndex()){//维密老虎机scatterNum 是专属于自己的ScatterMultipleTemplate
			return robot.getXLSService().getScatterMultipleScatterNum(robot.getTemplateService(),slotType);
		}
		return robot.getXLSService().getNormolScatterTemplateMixScatterNum(robot.getTemplateService(), slotType);
	}
	
	private int getScatterNum(int[] slotElementList,int slotType) {
		int scatterNum = 0;
		//判断有多少scatter
		for(int i=0;i<slotElementList.length;i++){
			int scatter = slotElementList[i];
			if(slotType == 10 || slotType == 8){//马来网红 和澳门女神
				if(scatter == 1){
					scatterNum++;
				}
			}else if(slotType == 9){//白娘子
				if(scatter == 6){
					scatterNum++;
				}
			}else{
				if(scatter == 2){
					scatterNum++;
				}
			}
		}
		return scatterNum;
		
	}

}
