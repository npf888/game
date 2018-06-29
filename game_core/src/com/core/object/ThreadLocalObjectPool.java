package com.core.object;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 可重用的单例对象
 * @author Thinker
 *
 */
public class ThreadLocalObjectPool
{
	/** 类字典 */
	private static final Map<Class<?>, ThreadLocal<?>> CLAZZ_MAP = new ConcurrentHashMap<Class<?>, ThreadLocal<?>>();

	/**
	 * 获取类对象, <font color="#990000">注意 : 该类对象必须提供默认构造器</font>
	 * 
	 * @param clazz
	 * @return 
	 * 
	 */
	public static <T> T get(Class<T> clazz) 
	{
		if (clazz == null) 
		{
			return null;
		}

		@SuppressWarnings("unchecked")
		ThreadLocal<T> tl = (ThreadLocal<T>)CLAZZ_MAP.get(clazz);

		if (tl == null) 
		{
			synchronized (clazz)
			{
				if (tl == null) 
				{
					// 创建线程本地对象
					tl = newThreadLocal(clazz);
					// 并加入到字典
					CLAZZ_MAP.put(clazz, tl);
				}
			}
		}

		return tl.get();
	}

	/**
	 * 创建线程本地对象
	 * 
	 * @param clazz
	 * @return 
	 * 
	 */
	private static <T> ThreadLocal<T> newThreadLocal(final Class<T> clazz)
	{
		if (clazz == null)
		{
			return null;
		}

		// 创建新的线程本地对象
		return new ThreadLocal<T>()
		{
			@Override
			protected T initialValue()
			{
				// 创建并返回新对象
				return newObj(clazz);
			}
		};
	}

	/**
	 * 创建对象
	 * 
	 * @param clazz
	 * @return 
	 * 
	 */
	private static <T> T newObj(Class<T> clazz)
	{
		if (clazz == null)
		{
			return null;
		}

		try 
		{
			// 返回新对象
			return clazz.newInstance();
		} catch (Exception ex)
		{
			// 抛出异常
			throw new RuntimeException(ex);
		}
	}
}
