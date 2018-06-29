package com.robot.strategy;


/**
 * 某个strategy内部的substrategy，由外部的strategy进行调度
 * @author Thinker
 *
 */
public interface IInnerStrategy extends IRobotStrategy
{
	public long getLastActionTime();
	
	public void setLastActionTime(long lastActionTime);
	
	public int getActionInterval();
}
