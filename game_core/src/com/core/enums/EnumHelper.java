package com.core.enums;

import com.core.util.Assert;

/**
 * 枚举帮着类
 * @author Thinker
 * 
 */
public final class EnumHelper
{
	/**
	 * 转换整数值为枚举
	 * 
	 * @param intVal 
	 * @param enums 
	 * @return 
	 * 
	 */
	public static <T extends ICustomEnum> T parse(int intVal, T[] enums)
	{
		// 断言参数不为空
		Assert.notNullOrEmpty(enums, "enums is null or empty");

		for (T $enum : enums)
		{
			if ($enum.intVal() == intVal)
			{
				return $enum;
			}
		}
		throw new RuntimeException("can't parse intVal " + intVal);
	}

	/**
	 * 转换字符串为枚举
	 * 
	 * @param strVal 
	 * @param enums 
	 * @return 
	 * 
	 */
	public static <T extends ICustomEnum> T parse(String strVal, T[] enums)
	{
		// 断言参数不为空
		Assert.notNullOrEmpty(enums, "enums is null or empty");

		for (T $enum : enums) 
		{
			if ($enum.strVal().equals(strVal))
			{
				return $enum;
			}
		}
		throw new RuntimeException("can't parse strVal " + strVal);
	}

	/**
	 * 自定义枚举
	 * @author Thinker
	 * 
	 */
	public static interface ICustomEnum
	{
		/**
		 * 获取整数值
		 * 
		 * @return
		 * 
		 */
		int intVal();

		/**
		 * 获取字符串值
		 * 
		 * @return 
		 * 
		 */
		String strVal();
	}
}
