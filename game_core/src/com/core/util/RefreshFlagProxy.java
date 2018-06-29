package com.core.util;

import com.core.time.TimeService;
import com.core.util.RefreshFlag.Memo;

/**
 * 刷新标志, 注意: 这里通过代理模式, 来包装 RefreshFlag 类! 
 * 默认可以自动获取当前时间, 省去额外编码.  
 * 
 * @author Thinker
 * 
 */
public final class RefreshFlagProxy
{
	/** 默认重试次数(3 次) */
	private static final int DEFAULT_RETRY = 3;

	/** 刷新标志 */
	private RefreshFlag _innerFlag = null;
	/** 时间服务 */
	private TimeService _timeServ = null;

	/**
	 * 类参数构造器
	 * 
	 * @param timePoint
	 * @param maxRetry
	 * @param timeServ 
	 * 
	 * @throws IllegalArgumentException if timeServ == null 
	 * 
	 */
	public RefreshFlagProxy(long timePoint, int maxRetry, TimeService timeServ)
	{
		if (timeServ == null)
		{
			throw new IllegalArgumentException("timeServ is null");
		}

		this._innerFlag = new RefreshFlag(timePoint, maxRetry);
		this._timeServ = timeServ;
	}

	/**
	 * 类参数构造器
	 * 
	 * @param timePoint
	 * @param timeServ  
	 * 
	 * 
	 */
	public RefreshFlagProxy(long timePoint, TimeService timeServ) 
	{
		// 调用自身构造器
		this(timePoint, DEFAULT_RETRY, timeServ);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	private long getCurrTime() 
	{
		return this._timeServ.now();
	}

	/**
	 * 是否可以刷新?
	 * 
	 * @return
	 * 
	 */
	public boolean canRefresh()
	{
		return this._innerFlag.canRefresh(this.getCurrTime());
	}

	/**
	 * 标志开始
	 * 
	 * @return
	 * 
	 */
	public boolean flagBegin() 
	{
		return this._innerFlag.flagBegin(this.getCurrTime());
	}

	/**
	 * 标志结束
	 * 
	 * 
	 */
	public boolean flagEnd() 
	{
		return this._innerFlag.flagEnd(this.getCurrTime());
	}

	/**
	 * 获取最后完成时间
	 * 
	 * @return 
	 * 
	 */
	public long getLastCompletedTime() 
	{
		return this._innerFlag.getLastCompletedTime();
	}

	/**
	 * 将当前对象写入到备忘录
	 * 
	 * @param value 
	 * 
	 */
	public void writeMemo(Memo value) 
	{
		this._innerFlag.writeMemo(value);
	}

	/**
	 * 从备忘录中还原
	 * 
	 * @param value 
	 * 
	 */
	public void readMemo(Memo value)
	{
		this._innerFlag.readMemo(value);
	}
}
