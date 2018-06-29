package com.core.heartbeat;

import java.util.concurrent.Callable;

/**
 * 抽象运行器：主要实现重载或者运行ID
 * @author Thinker
 * 
 */
public abstract class LLAbstractRunner implements Callable<Integer>  
{
	@Override
	public Integer call() throws Exception 
	{
		return getId();
	}
	/**
	 * 获取运行ID
	 * @return
	 */
	public abstract int getId();
}
