package com.robot.slot;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.robot.Robot;

public class TimeTask {
	protected Logger logger = Loggers.slotLogger;
	public void timeSeting(Robot robot){
		
		//设置定时器 myTask1
		final long timeInterval1 = 5000;  
		Runnable runnable1 = new Runnable() {  
			public void run() {  
				while (true) {  
					try {  
						Thread.sleep(timeInterval1); 
						/**
						 * 看看跑到第几个了  有没有停下来
						 * 先取出一个值，隔三秒 再取一个值，如果没有变化，说明停了，然后重新发起
						 */
						
						int maxNum = robot.getRobotSlotCacheData().getMaxNum();
						int num = robot.getRobotSlotCacheData().getNum();
						if(num > maxNum){
							return;
						}
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							logger.info("定时器失败：："+e.getMessage());
							e.printStackTrace();
						}
						int num2 = robot.getRobotSlotCacheData().getNum();
						logger.info("第一个num::"+num+"----第二个num2:::"+num2);
						if(num == num2){
//							robot.getRobotSlotCacheDataService().save(robot, robot.getRobotSlotCacheData().getSlotId());
//							robot.getTurnSlot().turn(robot, robot.getRobotSlotCacheData().getSlotId());;
							return;
						}
					} catch (InterruptedException e) {  
						e.printStackTrace();  
					}  
				}  
			}  
		};  
		logger.info("检查老虎机是否停止的定时器 开启... ...");
		Thread thread1 = new Thread(runnable1);  
		thread1.start();  
		logger.info("检查老虎机是否停止的定时器 开启成功... ...");
	
	}
}
