package com.robot.slot;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.slot.msg.CGSlotSlot;
import com.robot.Robot;

/**
 * 
 * 三消 老虎机专用
 * @author JavaServer
 *
 */
public class TurnDisSlot {
	protected Logger logger = Loggers.slotLogger;
	
	/**
	 * spin
	 */
	public void turnDis(Robot robot,int slotId){
		int free = robot.getRobotSlotCacheData().getFree();
		//每次付费转动 算一次
		if(free==0){
			//每次付费转动的时候 下边两个值 如果为 false 都设置为
			robot.getRobotSlotCacheData().setScatterFree(false);
			robot.getRobotSlotCacheData().setBonusFree(false);
			//第几次老虎机转动
			String slotName = robot.getRobotSlotCacheData().getSlotName();
			int slotType = robot.getRobotSlotCacheData().getSlotType();
			
			int num = robot.getRobotSlotCacheData().getNum();
			int maxNum = robot.getRobotSlotCacheData().getMaxNum();
			logger.info("num::"+num+"--maxNum::"+maxNum);
			logger.info("第"+num+"个老虎机----------开始转动===========老虎机名称==("+slotName+")-老虎机 类型==("+slotType+")-老虎机ID==("+slotId+")");
			logger.info("剩余的 免费次数：：-------------------------------------："+robot.getRobotSlotCacheData().getFreeTimes()+"");
			robot.getRobotSlotCacheData().setNum(num+1);
//			if((num-1) > maxNum){
			if(num!=1 && (num-1)%maxNum == 0){
				//最后保存数据
				robot.getRobotSlotCacheDataService().save(robot, slotId);
//				return;
			}
		}
		
		//发送转动消息
		turnMainSlot(robot,robot.getRobotSlotCacheData().getBet(),free);
	}
	
	
	
	
	
	private void turnMainSlot(Robot robot, int bet, int free){
		//每次转动老虎记得时候都要 初始化一些数据
		CGSlotSlot ss = new CGSlotSlot();
		ss.setBet(bet);
		ss.setFree(free);
		robot.getRobotSlotCacheData().setBet(bet);
		robot.getRobotSlotCacheData().setFree(free);
		robot.sendMessage(ss);
	}

}
