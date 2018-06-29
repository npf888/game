package com.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Predicate;

/**
 * 集合相关的工具类
 * 
 * @author Thinker
 * 
 */
public class CollectionUtil
{
	/**
	 * 构建泛型类型的 HashMap, 该 Map 的初始容量是 0
	 * 
	 * @param <K>
	 * @param <V>
	 * @return
	 * 
	 */
	public static <K, V> Map<K, V> buildHashMap()
	{
		return new HashMap<K, V>(0);
	}

	/**
	 * 过滤列表, 并返回过滤后的结果
	 * 
	 * @param list
	 * @param predicate
	 * @return
	 * 
	 */
	public static <T> List<T> filter(List<T> list, Predicate<T> predicate)
	{
		List<T> resultList = new ArrayList<T>();
		
		for (T t : list)
		{
			if (predicate.apply(t)) 
			{
				resultList.add(t);
			}
		}

		return resultList;
	}

	/**
	 * 从集合中删除元素
	 * 
	 * @param coll
	 * @param predicate
	 * @return 
	 * 
	 */
	public static <T> List<T> remove(Collection<T> coll, Predicate<T> predicate)
	{
		List<T> list = new LinkedList<T>();
		Iterator<T> it = coll.iterator();

		while (it.hasNext())
		{
			T t = it.next();

			if (predicate.apply(t))
			{
				it.remove();
				list.add(t);
			}
		}

		return list;	
	}

	/**
	 * 获取最后一个元素
	 * 
	 * @param l
	 * @return 
	 * 
	 */
	public static <T> T getLastItem(List<T> l) 
	{
		if (l == null || l.size() <= 0) 
		{
			return null;
		} else
		{
			return l.get(l.size() - 1);
		}
	}
}
