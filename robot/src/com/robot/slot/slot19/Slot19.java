package com.robot.slot.slot19;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.msg.CGSlotType19Scatter;
import com.gameserver.slot.msg.GCSlotSlot;
import com.gameserver.slot.template.SlotsListTemplate;
import com.robot.Robot;
import com.robot.slot.GCSlotBase;
import com.robot.slot.RobotSlotCacheData;
/**
 * 水晶魔法宝石
 * @author JavaServer
 *
 */
public class Slot19 extends GCSlotBase{

	protected Logger logger = Loggers.slotLogger;
	@Override
	public synchronized void execute(Robot robot, GCSlotSlot gCSlotSlot,int slotId,int slotType) {
		/** 是否免费 */
		int free = gCSlotSlot.getFree();
		/** 免费次数 */
		int freeTimes = gCSlotSlot.getFreeTimes();
		/** 收益 */
		long rewardNum = gCSlotSlot.getRewardNum();
		/** 累计奖金 */
		long rewardSum = gCSlotSlot.getRewardSum();
		/** 元素列表 */
		int[] slotElementList = gCSlotSlot.getSlotElementList();
		/** 连线信息列表 */
		SlotConnectInfoData[] slotConnectInfoData = gCSlotSlot.getSlotConnectInfoDataList();
		/** 特殊连线信息列表 */
		SpecialConnectInfoData[] specialConnectInfoData = gCSlotSlot.getSpecialConnectInfoDataList();
		
		
		RobotSlotCacheData robotSlotCacheData = robot.getRobotSlotCacheData();
		
		//获取scatter 的数量
		int scatterNum = getScatterNum(slotElementList);
		int scatterMin = robot.getXLSService().getScatterTemplateMixScatterNum(robot.getTemplateService(), slotType);
		//付费转动
		if(free == 0){
			/**
			 * 先处理 是属于 scatter 还是 bonus
			 */
			if(freeTimes > 0){
				robotSlotCacheData.setScatterFree(true);
				robotSlotCacheData.setScatterTimes(freeTimes);
			}
			if(scatterNum >= scatterMin){
				//scatter的次数：scatter游戏触发的次数；
				robotSlotCacheData.setScatterTriggerCount(robotSlotCacheData.getScatterTriggerCount()+1);
//				robotSlotCacheData.setBonusNum(scatterNum);
				//不同其他老虎机之处
				CGSlotType19Scatter cGSlotType19Scatter = new CGSlotType19Scatter();
				robot.sendMessage(cGSlotType19Scatter);
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
			robot.getRobotSlotCacheData().setFreeTimes(freeTimes);
			
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
				robotSlotCacheData.setBonusNum(scatterNum);
				CGSlotType19Scatter cGSlotType19Scatter = new CGSlotType19Scatter();
				robot.sendMessage(cGSlotType19Scatter);
				logger.info("------------------------scatterNum：："+scatterNum);
			}
			
			//中奖
			robot.getBonusHandler().countBonusNum(robot,Long.valueOf(rewardNum).intValue());
		}
		logger.info("免费转动------------------------freeTimes:"+freeTimes);
		
		//免费转动
		int bonusNum = robotSlotCacheData.getBonusPubNum();
		if(bonusNum > 0){
			return;
		}
		robot.getTurnSlot().turnFree(robot, freeTimes, slotId);
		
	}
	private int getScatterNum(int[] slotElementList) {
		int scatterNum = 0;
		//判断有多少scatter
		for(int i=0;i<slotElementList.length;i++){
			int scatter = slotElementList[i];
			if(scatter == 2){
				scatterNum++;
			}
		}
		return scatterNum;
	}

}
