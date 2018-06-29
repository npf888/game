package com.core.persistance;

import com.core.object.LifeCycle;

/**
 * 数据更新接口
 * @author Thinker
 *
 */
public interface DataUpdater
{
	public boolean addUpdate(final LifeCycle lifeCycle);
	
	public boolean addDelete(final LifeCycle lifeCycle);
	
	public void update();
	
	/**
	 * 需要更新的项
	 * 
	 */
	public static class UpdateEntry 
	{
		public static final int OPER_UPDATE = 1;
		public static final int OPER_DEL = 2;
		public final int operation;
		
		/** 待更新的业务对象 */
		public final LifeCycle obj;

		public UpdateEntry(int operation, LifeCycle obj) 
		{
			this.operation = operation;
			this.obj = obj;
		}
	}
}
