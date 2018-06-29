package com.robot.slot;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.slot.msg.CGFreeSlotReward;
import com.gameserver.slot.msg.CGSlotSlot;
import com.gameserver.slot.template.BoxTemplate;
import com.robot.Robot;

/**
 * 转动老虎机
 * @author JavaServer
 *
 */
public class TurnSlot {

	protected Logger logger = Loggers.slotLogger;
	
	public  void turn(Robot robot,int slotId){
		int num = robot.getRobotSlotCacheData().getNum();
		int maxNum = robot.getRobotSlotCacheData().getMaxNum();
		int free = robot.getRobotSlotCacheData().getFree();
		if(robot.getRobotSlotCacheData().getSlotType() == 2 && robot.getRobotSlotCacheData().getWildFreeTimes() >0){
			free = 2;
		}
		logger.info("num::"+num+"--maxNum::"+maxNum);
		//每次付费转动 算一次
		if(free==0){
			//每次付费转动的时候 下边两个值 如果为 false 都设置为
			robot.getRobotSlotCacheData().setScatterFree(false);
			robot.getRobotSlotCacheData().setBonusFree(false);
			//第几次老虎机转动
			String slotName = robot.getRobotSlotCacheData().getSlotName();
			int slotType = robot.getRobotSlotCacheData().getSlotType();
			logger.info("==============================老虎机名称==("+slotName+")-老虎机 类型==("+slotType+")-老虎机ID==("+slotId+")");
			logger.info("第"+num+"个老虎机--------------------------开始转动");
			logger.info("剩余的 免费次数：："+robot.getRobotSlotCacheData().getFreeTimes()+"：：-----------------------------");
			robot.getRobotSlotCacheData().setNum(num+1);
//			if((num-1) > maxNum){
			if(num!=1 && (num-1)%maxNum == 0){
				//最后保存数据
				robot.getRobotSlotCacheDataService().save(robot, slotId);
//				return;
			}
		}
		turnMain(robot,robot.getRobotSlotCacheData().getBet(),free);
		
	}

	public void turnFree(Robot robot, int freeTimes,int slotId) {
		RobotSlotCacheData robotSlotCacheData = robot.getRobotSlotCacheData();
		robotSlotCacheData.setFreeTimes(freeTimes);
		int free = 1;//免费转动
		logger.info("执行scatter------------------------freeTimes:"+freeTimes);
		if(freeTimes > 0){
			robotSlotCacheData.setFree(free);
			turn(robot,slotId);
		}else{
			free = 0;
			//没有盒子的和以前一样
			int slotType = robot.getRobotSlotCacheData().getSlotType();
			if(slotType>10 || slotType == 8 || slotType == 4 || slotType == 3){//维密 12 和 沙滩 4 是一样的了
//				CGFreeSlotReward CGFreeSlotReward = new CGFreeSlotReward();
//				CGFreeSlotReward.setPos(-1);
//				//没转完一次 发送-1 结算
//				robot.sendMessage(CGFreeSlotReward);
				//然后再一次转动老虎机
				robotSlotCacheData.setFree(free);
				turn(robot,slotId);
			//有盒子的 就不一样了 这里需要在进行 交互计算，所有免费转的的钱的倍数
			}else{
				if(robotSlotCacheData.getFree() == 1){//最后一次免费转动结束
					CGFreeSlotReward CGFreeSlotReward = new CGFreeSlotReward();
					if(slotType == 10){//马来网红
						CGFreeSlotReward cGFreeSlotReward = new CGFreeSlotReward();
						cGFreeSlotReward.setPos(robot.getRobotSlotCacheData().getBoxId());
						robot.sendMessage(cGFreeSlotReward);
						return;
					}else{
						int i = robot.getXLSService().getBoxTemplateSize(robot.getTemplateService(),slotType);
						CGFreeSlotReward.setPos(i);
					}
					//
//					robot.sendMessage(CGFreeSlotReward);
				}
				//付费转动获得了免费转动次数，ScatterTimes 就会 >0 ,然后就走自用转动的结算（选三个盒子的逻辑），
				if(robot.getRobotSlotCacheData().getScatterTimes() == 0){
					robotSlotCacheData.setFree(free);
					turn(robot,slotId);
				}
			}
		}
	}
	public void turnMain(Robot robot, int bet, int free) {
		//每次转动老虎记得时候都要 初始化一些数据
		CGSlotSlot ss = new CGSlotSlot();
		ss.setBet(bet);
		ss.setFree(free);
		robot.getRobotSlotCacheData().setBet(bet);
		robot.getRobotSlotCacheData().setFree(free);
		robot.sendMessage(ss);
		
		logger.info("------------------------开始发送消息-------------------------------");
		
	}
}
