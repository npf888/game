package com.core.util;


/**
 * 刷新标志, 用来记录某个系统功能是否已经在指定时刻刷新过? 
 * 如果刷新过程失败, 允许重试刷新.
 * <font color='#990000'>注意: 该类的所有函数都不是现成安全的!</font> 
 * 
 * @author Thinker
 * 
 */
public final class RefreshFlag 
{
	/** 忙碌时间最大值(2 分钟) */
	private static final long BUSY_TIME_MAX = 2L * 60L * 1000L;
	/** 最大时间点 */
	private static final long TIME_POINT_MAX = 24L * 3600L * 1000L - 1L;

	/** 刷新时间点 */
	private final long _TIME_POINT;
	/** 最大重试次数 */
	private final int _MAX_RETRY;

	/** 上次刷新完成时间 */
	private long _lastCompletedTime;
	/** 上次忙碌时间 */
	private long _lastBusyTime;
	/** 当前重试次数 */
	private int _currRetry;
	/** 上次修改重试次数的时间 */
	private long _lastUpdateRetryTime;

	/**
	 * 类默认构造器
	 * 
	 * @param timePoint 刷新时间点(时间戳). 
	 * XXX 注意: 该值的取值范围是: 00:00:00.000 ~ 23:59:59.999 这样的时间戳, 
	 * 转换为 long 类型数值是 0L ~ 86399999L
	 * 
	 * @param maxRetry 最大重试次数, 
	 * 如果最大重试次数 = 2, 
	 * 那么在第 1 次执行失败之后, 外部程序还可以有机会再执行 2 次. 即, 
	 * 最大执行次数为 3 次!
	 * 
	 * @throws IllegalArgumentException if timePoint > TIME_POINT_MAX
	 * 
	 */
	public RefreshFlag(long timePoint, int maxRetry) 
	{
		if (timePoint > TIME_POINT_MAX) 
		{
			throw new IllegalArgumentException("timePoint 取值区间是: [0, 86399999]");
		}

		this._TIME_POINT = timePoint;
		this._MAX_RETRY = maxRetry;

		this._lastCompletedTime = 0L;
		this._lastBusyTime = 0L;

		// 
		// 最大重试次数默认为 -1, 
		// 在调用 flagBegin 函数时, 该值会 +1
		// 即, 第 1 次调用 flagBegin 函数时, 算是正常调用! 
		// 执行 +1 操作后, 当前重试次数为 0.
		// 这样的目的是避免复杂算法...
		// 
		this._currRetry = -1;
	}

	/**
	 * 当前是否可以刷新, <font color="#990000">注意: 该函数是非线程安全的!</font>
	 * 
	 * @param now
	 * @return
	 */
	public boolean canRefresh(long now) 
	{
		// 获取今日刷新时间点
		long todayTimePoint = getTodayTimePoint(now, this._TIME_POINT);

		if (now < todayTimePoint)
		{
			// 如果当前时间还没到刷新时间
			// (例如当前时间是 9:00, 刷新时间是 10:00), 
			// 则直接退出!
			return false;
		} else if (this.isTodayCompleted(todayTimePoint)) 
		{
			// 如果已经刷新完成, 
			// 则直接退出!
			return false;
		} else if (this.isBusy(now)) 
		{
			// 如果正在刷新, 
			// 则直接退出!
			return false;
		} else if (!this.canRetry(todayTimePoint, now))
		{
			// 如果刷新失败且已经不能再重试, 
			// 则直接退出!
			return false;
		} else 
		{
			return true;
		}
	}

	/**
	 * 获取今日刷新时间点
	 * 
	 * @param now
	 * @param timePoint
	 * @return 
	 * 
	 */
	private static long getTodayTimePoint(long now, long timePoint) 
	{
		if (now <= 0L) 
		{
			return 0L;
		} else 
		{
			// 获得今天的 0 点时刻
			return TimeUtils.getBeginOfDay(now) + timePoint;
		}
	}

	/**
	 * 获取今日刷新时间点
	 * 
	 * @param now
	 * @return 
	 * 
	 */
	public long getTodayTimePoint(long now)
	{
		return getTodayTimePoint(now, this._TIME_POINT);
	}

	/**
	 * 今日是否已经完成刷新操作?  
	 * 
	 * @param todayTimePoint 今日刷新时间点(时间戳)
	 * @return 
	 * 
	 */
	private boolean isTodayCompleted(long todayTimePoint) 
	{
		// 如果上次刷新完成时间大于今日刷新时间点, 
		// 则说明已经刷新过!
		return (this._lastCompletedTime > todayTimePoint);
	}

	/**
	 * 现在是否正在忙碌?
	 * 
	 * @return
	 * 
	 */
	private boolean isBusy(long now) 
	{
		// 如果上一次忙碌时间和当前时间相差不到 2 分钟, 
		// 则说明现在还处于忙碌状态!
		// 服务器端代码执行一次时间不会超过 2 分钟, 
		// 如果超过 2 分钟, 
		// 则说执行过程可能被中断了, 
		// 没有最终调用 flagEnd 把忙碌标志置 0...
		// 对于这种情况应该判断时间差, 来推算忙碌标志
//		return ((this._lastBusyTime + BUSY_TIME_MAX) > now); // XXX 注意为防止 long 类型溢出所以使用等效的减法运算
		return (this._lastBusyTime > (now - BUSY_TIME_MAX));
	}

	/**
	 * 是否可以可以重试?
	 * 
	 * @param todayTimePoint 
	 * @param now 
	 * @return 
	 * 
	 */
	private boolean canRetry(long todayTimePoint, long now)
	{
		// 如果上次忙碌时间小于今日刷新时间点, 
		// 则说明今天还没有刷新过, 
		if (this._lastUpdateRetryTime < todayTimePoint)
		{
			this._currRetry = -1;
			this._lastUpdateRetryTime = now;
		}

		return this._currRetry < this._MAX_RETRY;
	}

	/**
	 * 标志开始, <font color="#990000">注意: 该函数是非线程安全的!</font>
	 * 
	 * @param now
	 * @return
	 */
	public boolean flagBegin(long now) 
	{
		if (now <= 0L)
		{
			return false;
		}

		if (!this.canRefresh(now))
		{
			// 如果不能刷新, 
			// 则直接退出!
			return false;
		}

		// 更新上一次忙碌时间为当前时间, 
		// 并增加重试次数
		this._lastBusyTime = now;

		// 在调用该函数时, 重试次数会 +1
		// 即, 第 1 次调用 flagBegin 函数时, 算是正常调用! 
		// 执行 +1 操作后, 当前重试次数为 0.
		// 这样的目的是避免复杂算法...
		this._currRetry++;
		this._lastUpdateRetryTime = now;
		return true;
	}

	/**
	 * 标志结束, <font color="#990000">注意: 该函数是非线程安全的!</font>
	 * 
	 * @param now
	 * @return
	 */
	public boolean flagEnd(long now)
	{
		if (now <= 0L || 
			now < this._lastCompletedTime)
		{
			return false;
		}

		// 更新上次刷新完成时间
		this._lastCompletedTime = now;
		
		// 刷新完成后清除变量, 包括: 重试次数、上次忙碌时间
		this._currRetry= -1;
		this._lastUpdateRetryTime = now;
		this._lastBusyTime = 0L;

		return true;
	}

	/**
	 * 获取最后完成时间
	 * 
	 * @return 
	 * 
	 */
	public long getLastCompletedTime() 
	{
		return this._lastCompletedTime;
	}

	/**
	 * 将当前对象写入到备忘录
	 * 
	 * @param value 
	 * 
	 */
	public void writeMemo(Memo value) 
	{
		if (value == null)
		{
			return;
		}

		value.setLastCompletedTime(this._lastCompletedTime);
		value.setLastBusyTime(this._lastBusyTime);
		value.setCurrRetry(this._currRetry);
		value.setLastUpdateRetryTime(this._lastBusyTime);
	}

	/**
	 * 从备忘录中还原
	 * 
	 * @param value 
	 * 
	 */
	public void readMemo(Memo value) 
	{
		if (value == null) 
		{
			return;
		}

		this._lastCompletedTime = value.getLastCompletedTime();
		this._lastBusyTime = value.getLastBusyTime();
		this._currRetry= value.getCurrRetry();
		this._lastBusyTime = value.getLastUpdateRetryTime();
	}

	/**
	 * 刷新标志备忘录, 注意: 这里通过备忘录模式, 
	 * 来获取 RefreshFlag 的内部变量! 
	 * 而不是直接将 RefreshFlag 内部变量公开给外界!
	 * 
	 * @author Thinker
	 *
	 */
	public static class Memo
	{
		/** 上次刷新完成时间 */
		private long _lastCompletedTime;
		/** 上次忙碌时间 */
		private long _lastBusyTime;
		/** 当前重试次数 */
		private int _currRetry;
		/** 上次修改重试次数的时间 */
		private long _lastUpdateRetryTime;

		/**
		 * 获取上次刷新完成时间
		 * 
		 * @return 
		 * 
		 */
		public long getLastCompletedTime()
		{
			return this._lastCompletedTime;
		}

		/**
		 * 设置上次刷新完成时间
		 * 
		 * @param value 
		 * 
		 */
		public void setLastCompletedTime(long value)
		{
			this._lastCompletedTime = value;
		}

		/**
		 * 获取上次忙碌时间
		 * 
		 * @return
		 */
		public long getLastBusyTime() 
		{
			return this._lastBusyTime;
		}

		/**
		 * 设置上次忙碌时间
		 * 
		 * @param value
		 */
		public void setLastBusyTime(long value)
		{
			this._lastBusyTime = value;
		}

		/**
		 * 获取当前重试次数
		 * 
		 * @return 
		 * 
		 */
		public int getCurrRetry() 
		{
			return this._currRetry;
		}

		/**
		 * 设置当前重试次数
		 * 
		 * @param value 
		 * 
		 */
		public void setCurrRetry(int value)
		{
			this._currRetry = value;
		}

		/**
		 * 获取最后重试时间
		 * 
		 * @return
		 */
		public long getLastUpdateRetryTime()
		{
			return this._lastUpdateRetryTime;
		}

		/**
		 * 设置最后重试时间
		 * 
		 * @param value
		 */
		public void setLastUpdateRetryTime(long value)
		{
			this._lastUpdateRetryTime = value;
		}
	}
}
