package com.robot.manager;

import com.robot.Robot;

/**
 * 抽象管理类
 * @author Thinker
 *
 */
@Deprecated
public class AbstractManager implements IManager
{
	
	private Robot owner = null;
	
	public AbstractManager(Robot owner)
	{
		this.owner = owner;
	}

	@Override
	public Robot getOwner()
	{
		return owner;
	}
}
