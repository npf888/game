package com.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


/**
 * 
 * @author Thinker
 *
 */
public class Selector 
{

	private static final Random rand = new Random();

	/**
	 * 对列表进行排序以便进行选择，<font color = 'red'>在queueSelect之前必须调用过</font>，当然，也可以自己去写个Comparator
	 */
	public static <T extends LimitSelectable> void sort(List<T> list) 
	{
		Collections.sort(list, new Comparator<T>() 
		{
			@Override
			public int compare(T t1, T t2)
			{
				int value = (int) ((t1.getLimit() - t2.getLimit()) * 10000);
				return t1.smallToLarge() ? value : -value;
			}
		});
	}
	
	/**
	 * 根据某一特定值从list中选择对象，该list必须要是已排序的。<br>
	 * 比如说list是从小到大的，其中对象的limit分别3、5、8，那么分别表示的是：<br>
	 * value处在(-∞,3]、(3、5]、(5,8]时将被选择的的对象，(-∞,0)为null。
	 */
	public static <T extends LimitSelectable> T limitSelect(List<T> list, double value) 
	{
		if (list == null || list.isEmpty())
			return null;

		for (T t : list)
			if ((t.smallToLarge() && value <= t.getLimit()) || (!t.smallToLarge() && value >= t.getLimit()))
				return t;

		return null;
	}

	/**
	 * 从list中根据概率随机选择
	 */
	public static <T extends OddsSelectable> T randomSelect(List<T> list)
	{
		if (list == null || list.isEmpty())
			return null;

		double value = rand.nextDouble();

		double cur = 0;
		for (T t : list)
		{
			if (value <= t.getOdds() + cur)
				return t;
			cur += t.getOdds();
		}

		return list.get(list.size() - 1);
	}

	/**
	 * 等概率选择
	 */
	public static <T> T randomSelectEvenly(List<T> list)
	{
		return list.isEmpty() ? null : list.get(rand.nextInt(list.size()));
	}
	
	/**
	 * 等概率选择N个
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> randomSelectEvenly(List<T> list, int n)
	{
		if(n <= 0 || list.isEmpty())
			return null;
		
		if(n >= list.size())
			return list;
		
		if(n == 1)
			return Arrays.asList(list.get(rand.nextInt(list.size())));
		
		if(n == list.size() - 1) 
		{
			List<T> tmp = new ArrayList<T>(list);
			tmp.remove(rand.nextInt(list.size()));
			return tmp;
		}
			
		ArrayList<Integer> idxs = new ArrayList<Integer>(list.size());
		for(int i = 0; i < list.size(); i++)
			idxs.add(i);
		
		Collections.shuffle(idxs);
		
		List<T> result = new ArrayList<T>(n);
		for(int i = 0; i < n && i < list.size(); i++)
			result.add(list.get(idxs.get(i)));
		
		return result;
	}

	/**
	 * 等概率选择
	 */
	public static <T> T randomSelectEvenly(T[] array)
	{
		return randomSelectEvenly(Arrays.asList(array));
	}
	
	/**
	 * 等概率选择
	 */
	public static <T> T randomSelectEvenly(Collection<T> coll)
	{
		return randomSelectEvenly(new ArrayList<T>(coll));
	}
	
	/**
	 * 等概率选择N个
	 */
	public static <T> List<T> randomSelectEvenly(Collection<T> coll, int n) 
	{
		return randomSelectEvenly(new ArrayList<T>(coll), n);
	}
}
