package com.core.util;

import java.util.Collection;

/**
 * 断言工具类, 用于对方法的传入参数进行校验. 
 * 如果未通过则抛出 <code>IllegalArgumentException</code> 异常!
 * 
 * @author Thinker
 * 
 */
public final class Assert
{
	private Assert()
	{
	
	}

	/**
	 * 断言对象不为空
	 * 
	 * @param obj
	 */
	public static void notNull(Object obj) 
	{
		notNull(obj, null);
	}

	/**
	 * 断言对象不为空
	 * 
	 * @param obj
	 */
	public static void notNull(Object obj, String msg)
	{
		if (obj == null)
		{
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * 断言字符串不为空
	 * 
	 * @param str
	 */
	public static void notNullOrEmpty(String str)
	{
		notNullOrEmpty(str, null);
	}

	/**
	 * 断言字符串不为空
	 * 
	 * @param str
	 * @param msg 
	 * 
	 */
	public static void notNullOrEmpty(String str, String msg)
	{
		if (str == null || str.isEmpty())
		{
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * 断言数组不为空
	 * 
	 * @param arr
	 * 
	 */
	public static void notNullOrEmpty(Object[] arr)
	{
		notNullOrEmpty(arr, null);
	}

	/**
	 * 断言数组不为空
	 * 
	 * @param arr
	 * @param msg 
	 * 
	 */
	public static void notNullOrEmpty(Object[] arr, String msg)
	{
		if (arr == null || 
			arr.length <= 0) 
		{
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * 断言集合不为空
	 * 
	 * @param coll
	 * 
	 */
	public static void notNullOrEmpty(Collection<?> coll)
	{
		notNullOrEmpty(coll, null);
	}

	/**
	 * 断言集合不为空
	 * 
	 * @param coll
	 * 
	 */
	public static void notNullOrEmpty(Collection<?> coll, String msg)
	{
		if (coll == null || 
			coll.isEmpty()) 
		{
			throw new IllegalArgumentException(msg);
		}
	}

    /**
     * 断言整数数组不为空
     *
     * @param intArr
     *
     */
    public static void notNullOrEmpty(int[] intArr)
    {
        notNullOrEmpty(intArr, null);
    }

    /**
     * 断言整数数组不为空
     *
     * @param intArr
     *
     */
    public static void notNullOrEmpty(int[] intArr, String msg)
    {
        if (intArr == null ||
            intArr.length <= 0)
        {
            throw new IllegalArgumentException(msg);
        }
    }

	/**
	 * 断言表达式为真
	 * 
	 * @param expr 
	 * 
	 */
	public static void isTrue(boolean expr)
	{
		isTrue(expr, null);
	}

	/**
	 * 断言表达式为真
	 * 
	 * @param expr
	 * 
	 */
	public static void isTrue(boolean expr, String msg)
	{
		if (!expr)
		{
			throw new IllegalArgumentException(msg);
		}
	}
}
