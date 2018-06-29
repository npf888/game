package com.robot.strategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.core.msg.IMessage;
import com.robot.Robot;


/**
 * 循环执行策略
 *@author Thinker
 */
public abstract class LoopExecuteStrategy extends BaseRobotStrategry
{
	private long startMills = 0L;
	
	private File file = new File("d:\\log.txt");
	/**
	 * 类参数构造器
	 * 
	 * @param robot 发起操作的机器人对象
	 */
	public LoopExecuteStrategy(Robot robot)
	{
		super(robot);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param robot 发起操作的机器人对象
	 * @param delay 第一次执行的延迟时间
	 * @param period 循环执行时的时间间隔
	 */
	public LoopExecuteStrategy(Robot robot, int delay, int interval)
	{
		super(robot,delay,interval);
	}
	
	@Override
	public final boolean isRepeatable()
	{
		return true;
	}
	
	@Override
	public void onResponse(IMessage message) {
		
		try {
			if(!file.exists())
				file.createNewFile();
			FileWriter pw = new FileWriter(file,true);
			pw.write(this.getClass().getName()+":"+(System.currentTimeMillis() - startMills)+"\r\n");
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void doAction() {
		this.startMills = System.currentTimeMillis();
	}
}
