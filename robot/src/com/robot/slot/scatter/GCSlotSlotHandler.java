package com.robot.slot.scatter;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.slot.enums.SlotTypeEnum;
import com.gameserver.slot.msg.CGSlotType12Free;
import com.gameserver.slot.msg.GCSlotSlot;
import com.gameserver.slot.template.SlotsListTemplate;
import com.robot.Robot;
import com.robot.slot.RobotSlotCacheData;
import com.robot.slot.util.ExportValue;

/**
 * 转动返回 GCSlotSlot的处理
 * @author JavaServer
 *
 */
public class GCSlotSlotHandler {
	protected Logger logger = Loggers.slotLogger;
	public void handlerGCSlotSlot(Robot robot, GCSlotSlot gCSlotSlot,int slotId,int slotType){
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
			if(freeTimes > 0){
				robotSlotCacheData.setScatterFree(true);
				robotSlotCacheData.setScatterTimes(freeTimes);
				logger.info("----------1---------付费-free:"+free+"--freeTimes:"+freeTimes+"--rewardNum:"+rewardNum+"----------");
			}else{
				robotSlotCacheData.setScatterTimes(0);
			}
			if(scatterNum >= scatterMin){
				//泰国象特殊点
				if(slotType == 20 && free == 0){
					//scatter的次数：scatter游戏触发的次数；
					robotSlotCacheData.setScatterTriggerCount(robotSlotCacheData.getScatterTriggerCount()+1);
					logger.info("----------2---------付费-scatterNum:"+scatterNum+"--scatterMin:"+scatterMin);
				}else{
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
			//如果wildFree是true 说明是大象 固定wild转动中
			if(robotSlotCacheData.isWildFree()){
				if(robotSlotCacheData.getWildFreeTimes() == 0){//如果==0 说明是WILD最后一次转动
					isOpen=false;
					//计算钱 最后一次计算前在这里计算
					robotSlotCacheData.setWildNum(robotSlotCacheData.getWildNum()+rewardNum);
					//关闭wild
					robotSlotCacheData.setWildFree(false);
					logger.info("----------5---------免费-WILD--最后一次-isWildFree:"+robotSlotCacheData.getWildNum());
				}
			}
			//这个是bonus免费转动 刚好处理完毕  然后所获得的钱就不会放在 bonus里
			if(robotSlotCacheData.isBonusFree() && freeTimes == (robotSlotCacheData.getScatterTimes()-1)){
				robotSlotCacheData.setBonusFree(false);
				logger.info("----------6---------免费-BONUS--结束-isBonusFree:"+robotSlotCacheData.isBonusFree()+"---freeTimes:"+freeTimes+"--robotSlotCacheData.getScatterTimes()"+robotSlotCacheData.getScatterTimes());
			}
			
			if(scatterNum >= scatterMin){
				//免费  scatter的次数：scatter游戏触发的次数；
				robotSlotCacheData.setScatterTriggerFreeCount(robotSlotCacheData.getScatterTriggerFreeCount()+1);
				logger.info("----------7---------免费-SCATTER---ScatterTriggerFreeCount:"+robotSlotCacheData.getScatterTriggerCount());
			}
			//1-7 9 10  最终一块结算翻倍 
			if(slotType >10 || slotType == 8 || slotType == 3){
				if(isOpen){
					robot.getBonusHandler().countBonusNum(robot,Long.valueOf(rewardNum).intValue());
					
				}
			}
//			ExportValue.exportValue("C:/img/bonusReward.txt","freeTimes="+freeTimes+"--rewardNum="+ rewardNum);
			//中奖
		}
		robot.getRobotSlotCacheData().setFreeTimes(freeTimes);
		//Scatter筹码数：中scatter游戏获得的筹码数量；
		boolean isBonusFree1 = robot.getRobotSlotCacheData().isBonusFree();
		boolean isScatterFree1 = robot.getRobotSlotCacheData().isScatterFree();
		logger.info("----------isBonusFree1---------isBonusFree1:"+isBonusFree1);
		logger.info("----------isScatterFree1---------isScatterFree1:"+isScatterFree1);
		//免费转动
		int bonusNum = robotSlotCacheData.getBonusPubNum();
		logger.info("----------8---------bonusNum:"+bonusNum);
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
		}else if(slotType == SlotTypeEnum.SlotType22.getIndex()){//西部牛仔老虎机scatterNum 是专属于自己的ScatterMultipleTemplate
			return robot.getXLSService().getWestScatterNum(robot.getTemplateService(),slotType);
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
	/**
	 * 维密老虎机的转动
	 * @param robot
	 * @param gCSlotSlot
	 * @param slotId
	 * @param slotType
	 */
	public void handlerGCSlotSlot12(Robot robot, GCSlotSlot gCSlotSlot,
			int slotId, int slotType) {
		/** 是否免费 */
		int free = gCSlotSlot.getFree();
		/** 免费次数 */
		int freeTimes = gCSlotSlot.getFreeTimes();
		/** 收益 */
		long rewardNum = gCSlotSlot.getRewardNum();
		/** 元素列表 */
		int[] slotElementList = gCSlotSlot.getSlotElementList();
		
		logger.info("服务器返回的免费转动次数：：："+freeTimes+"----------------------------------------------------");
		RobotSlotCacheData robotSlotCacheData = robot.getRobotSlotCacheData();
		//获取scatter 的数量
		int scatterNum = getScatterNum(slotElementList,slotType);
		int scatterMin = getScatterMin(robot,slotType);
		//付费转动
		if(free == 0){
			/**
			 * 先处理 是属于 scatter 还是 bonus
			 */
			if(freeTimes > 0){
				robotSlotCacheData.setScatterTimes(freeTimes);
			}
			if(scatterNum >= scatterMin){
				//scatter的次数：scatter游戏触发的次数；
				robotSlotCacheData.setScatterTriggerCount(robotSlotCacheData.getScatterTriggerCount()+1);
				logger.info("------------------------scatterNum：："+scatterNum);
			}
			
			/**
			 * 赢取次数：在转动中赢得筹码的次数；
			 * 如果rewardNum 大于零 说明本次 赢得的筹码
			 */
			if(rewardNum >0){
				//计算加一次
				robotSlotCacheData.setRewardCount(robotSlotCacheData.getRewardCount()+1);
			}
			SlotsListTemplate slotsListTemplate = robot.getXLSService().getTemplateFromXLS1(robot.getTemplateService(),slotId);
			//消耗筹码数：所有转动结束后所消耗的筹码；
			robotSlotCacheData.setRewardUsed(robotSlotCacheData.getRewardUsed()+robotSlotCacheData.getBet()*slotsListTemplate.getPayLinesNum());//TODO 乘以 多少线  几线 几线 老虎机是如何区分的
			//支付线筹码数：支付线中奖获得的筹码数量；
			robotSlotCacheData.setPayCount(robotSlotCacheData.getPayCount()+rewardNum);
			//设置免费转动次数
			
			logger.info("付费转动------------------------freeTimes：："+freeTimes);
		//免费转动
		}else{
			//这个是bonus免费转动 刚好处理完毕  然后所获得的钱就不会放在 bonus里
			if(robotSlotCacheData.isBonusFree() && freeTimes == (robotSlotCacheData.getScatterTimes()-1)){
				robotSlotCacheData.setBonusFree(false);
			}
			if(scatterNum >= scatterMin){
				//免费  scatter的次数：scatter游戏触发的次数；
				robotSlotCacheData.setScatterTriggerFreeCount(robotSlotCacheData.getScatterTriggerFreeCount()+1);
				logger.info("------------------------scatterNum：："+scatterNum);
			}
			//中奖
			robot.getBonusHandler().countBonusNum(robot,Long.valueOf(rewardNum).intValue());
			logger.info("免费转动------------------------freeTimes:"+freeTimes);
		}
		robot.getRobotSlotCacheData().setFreeTimes(freeTimes);
		//Scatter筹码数：中scatter游戏获得的筹码数量；
		
		
		
		//自己特有的处理  只在免费的时候使用
		if(gCSlotSlot.getFree()==1){
			if(freeTimes == 0){
				robot.getRobotSlotCacheData().setBonusPubNum(3);
				CGSlotType12Free cGSlotType12Free = new CGSlotType12Free();
				logger.info(" 发送最终 总结计算的消息-----------------------------");
				robot.sendMessage(cGSlotType12Free);
			}
		}
		//免费转动
		int bonusNum = robotSlotCacheData.getBonusPubNum();
		logger.info("bonusNum-----bonusNum---------bonusNum----------bonusNum:"+bonusNum);
		if(bonusNum > 0){
			return;
		}
		robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
		
	}
}
