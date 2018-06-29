package com.core.util;

import java.util.List;

/**
 * 
 * 枚举工具
 * @author Thinker
 * 
 */
public class EnumUtil
{
	/**
	 * 根据枚举index返回枚举元素，index从0开始
	 * 
	 * @param <T>
	 *            枚举类型
	 * @param values
	 *            枚举元素输注
	 * @param index
	 *            从0开始的index
	 * @return 枚举元素
	 */
	public static <T extends Enum<T>> T valueOf(List<T> values, int index)
	{
		T value = null;
		try
		{
			value = values.get(index);
		} catch (Exception e)
		{
			String typeName = "unknown";
			if (values != null) 
			{
				for (T enu : values) 
				{
					if (enu != null) 
					{
						typeName = enu.getClass().getName();
						break;
					}
				}
			}
			return null;
		}
		return value;
	}
}
