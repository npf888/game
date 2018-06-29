package com.robot.strategy;

import com.core.msg.IMessage;
import com.robot.Robot;
import com.robot.RobotState;



public abstract class BaseRobotStrategry implements IRobotStrategy
{
	/** 第一次执行的延迟时间 */
	public final static int DEFAULT_DELAY = 100;
	
	/** 循环执行时的时间间隔 */
	public final int DEFAULT_INTERVAL = 100;
	
	/** 发起操作的机器人对象 */
	private Robot robot;
	
	private int delay = 0;
	
	private int interval = 0;

	
	public BaseRobotStrategry(Robot robot)
	{
		this.robot = robot;
		this.delay = DEFAULT_DELAY;
		this.interval = DEFAULT_INTERVAL;
	}
	
	
	public BaseRobotStrategry(Robot robot, int delay, int interval) {
		this.robot = robot;
		this.delay = delay;
		this.interval = interval;
	}
	
	
	public boolean canDoAction()
	{
		if(this.robot.getState() == RobotState.gaming)
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	public Robot getRobot() {
		return robot;
	}


	public final int getDelay() {
		return this.delay;
	}

	public final int getPeriod() {
		return this.interval;
	}
	
	public void sendMessage(IMessage msg) {
		getRobot().sendMessage(msg);
	}

	/**
	 * 记录日志信息
	 * 
	 * @param robot
	 * @param msg
	 */
	protected void logInfo(String msg) {
		System.out.println("[Robot] " + this.robot.getPassportId() + " " + msg);
	}
}
