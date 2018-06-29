package com.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 随机数工具
 * 
 * @author Thinker
 * 
 */
public class RandomUtil
{

	private static Random random = new Random();

	/**
	 * 获取一个范围内的随机值
	 * 
	 * @param randomMin
	 *            区间起始值
	 * @param randomMax
	 *            区间结束值
	 * @return 返回区间的一个随机值
	 */
	public static int nextInt(int randomMin, int randomMax)
	{
		int randomBase = randomMax - randomMin;
		if (randomBase < 0)
		{
			throw new IllegalArgumentException("randomMax must be bigger than randomMin");
		} else if (randomBase == 0)
		{
			return randomMin;
		} else
		{
			return (random.nextInt(randomBase) + randomMin);
		}
	}
	
	
	/**
	 * 从对象列表中随机取一个
	 * 
	 * @param ol
	 * @param randomMax
	 * @return
	 * 
	 */
	public static <T> T nextObj(List<ProbabilityObject<T>> ol)
	{

		if (ol == null || ol.isEmpty())
		{
			return null;
		}

		// 根据概率总和随机选择一个对象
		return nextObj(ol, ProbabilityObject.sumProbability(ol));
	}

	/**
	 * 从对象列表中随机取一个
	 * 
	 * @param ol
	 * @param randomMax
	 * @return
	 * 
	 */
	public static <T> T nextObj(List<ProbabilityObject<T>> ol, int randomMax)
	{

		if (ol == null || ol.isEmpty())
		{
			return null;
		}

		// 随机值
		int randomVal = nextInt(0, randomMax);
		// 偏移量
		int offset = 0;

		for (ProbabilityObject<T> o : ol) 
		{
			if (randomVal >= offset && randomVal <= offset + o.getProbability())
			{
				return o.getInnerObj();
			} else 
			{
				// 移动偏移量
				offset += o.getProbability();
			}
		}

		return null;
	}

	/***
	 * 给出一个proplist,随机直到出现与endVal相同数值，并且不重复
	 * 
	 * @param ol
	 * @param randomMax
	 * @param endVal
	 * @return
	 */
	public static <T extends Object> List<T> randomListObj(List<ProbabilityObject<T>> ol, T endVal)
	{
		List<ProbabilityObject<T>> newOl = new ArrayList<ProbabilityObject<T>>(ol);
		List<T> retList = new ArrayList<T>();
		T val;
		do
		{
			val = nextObj(newOl);
			removeObj(newOl, val);
			if (val != null) 
			{
				retList.add(val);
			}
		} while (val != endVal && newOl.size() > 0);
		return retList;
	}

	public static <T extends Object> void removeObj(List<ProbabilityObject<T>> list, T obj) 
	{
		for (int i = 0;i<list.size();i++) 
		{
			if (list.get(i).getInnerObj() == obj) 
			{
				list.remove(i);
				return;
			}
		}
	}
}
