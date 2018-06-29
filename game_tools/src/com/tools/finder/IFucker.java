package com.tools.finder;

/**
 * 作弊者接口
 * 
 * @author Thinker
 * 
 */
public interface IFucker
{
	/**
	 * 获取玩家名称
	 * 
	 * @return 玩家名称
	 */
	public String getName();

	/**
	 * 获取UUID
	 * 
	 * @return UUID
	 */
	public long getUUID();
	
	public int getFuckCount();

	/**
	 * NULL OBJECT PATTERN
	 * 
	 * @author haijiang.jin
	 * 
	 */
	public static class NullFucker implements IFucker
	{
		@Override
		public String getName() 
		{
			return "NullFucker";
		}

		@Override
		public long getUUID() 
		{
			return -100L;
		}

		@Override
		public String toString()
		{
			return "#NullFucker, not a fucker!";
		}

		@Override
		public int getFuckCount()
		{
			return 0;
		}

	}

	/**
	 * 军功作弊者
	 * 
	 * @author Thinker
	 * 
	 */
	public static class ExploitFucker implements IFucker
	{
		private String name;
		private long uuid;
		/** 军功作弊的次数 */
		private int fuckCount;

		public ExploitFucker(String name, long uuid, int fuckCount)
		{
			this.name = name;
			this.uuid = uuid;
			this.fuckCount = fuckCount;
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public long getUUID() 
		{
			return uuid;
		}

		@Override
		public String toString()
		{
			return "#FUCKER(charName: " + name + ", UUID: " + uuid
					+ ", exploitFuckCount: " + this.fuckCount + ")";
		}

		@Override
		public int getFuckCount()
		{
			return fuckCount;
		}

	}

	/**
	 * 金錢作弊者
	 * 
	 *@author Thinker
	 * 
	 */
	public static class MoneyFucker implements IFucker 
	{
		private String name;
		private long uuid;
		private int inCount;
		private int outCount;
		private int totalIn;
		private int totalOut;

		public String getName() 
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public long getUUID()
		{
			return uuid;
		}

		public void setUuid(long uuid) 
		{
			this.uuid = uuid;
		}

		public int getInCount()
		{
			return inCount;
		}

		public void setInCount(int inCount)
		{
			this.inCount = inCount;
		}

		public int getOutCount() 
		{
			return outCount;
		}

		public void setOutCount(int outCount)
		{
			this.outCount = outCount;
		}

		public int getTotalIn() 
		{
			return totalIn;
		}

		public void setTotalIn(int totalIn)
		{
			this.totalIn = totalIn;
		}

		public int getTotalOut() 
		{
			return totalOut;
		}

		public void setTotalOut(int totalOut)
		{
			this.totalOut = totalOut;
		}

		/**
		 * 返回消耗和充入的比例
		 * 
		 * @return
		 */
		public int getOutAndInRatio() 
		{
			if (this.totalIn == 0)
			{
				return 0;
			}
			return this.totalOut * 100 / this.totalIn;
		}

		@Override
		public String toString()
		{
			return "#FUCKER(charName: " + name + ", UUID: " + uuid
					+ ", totalIn: " + totalIn + ", totalOut: " + totalOut
					+ ", ratio: " + this.getOutAndInRatio() + "%)";
		}

		@Override
		public int getFuckCount()
		{
			return getOutAndInRatio();
		}
	}
}
