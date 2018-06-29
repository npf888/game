package com.core.util;

import java.util.List;

/**
 * 概率对象
 * 
 * @author Thinker
 * 
 */
public class ProbabilityObject<T>
{
	/** 概率 */
	private int _probability = -1;
	/** 内置对象 */
	private T _innerObj = null;

	/**
	 * 类默认构造器
	 * 
	 */
	public ProbabilityObject()
	{
	}

	/**
	 * 类参数构造器
	 * 
	 * @param innerObj 
	 * @param probability 
	 * 
	 */
	public ProbabilityObject(T innerObj, int probability) 
	{
		// 断言参数不为空
		Assert.notNull(innerObj);
		// 设置内置对象和概率
		this._innerObj = innerObj;
		this._probability = probability;
	}

	/**
	 * 获取概率值
	 * 
	 * @return 
	 * 
	 */
	public int getProbability()
	{
		return this._probability;
	}

	/**
	 * 设置概率
	 * 
	 * @param value 
	 * 
	 */
	public void setProbability(int value)
	{
		this._probability = value;
	}

	/**
	 * 获取内置对象
	 * 
	 * @return 
	 * 
	 */
	public T getInnerObj()
	{
		return this._innerObj;
	}

	/**
	 * 设置内置对象
	 * 
	 * @param value 
	 * 
	 */
	public void setInnerObj(T value) 
	{
		this._innerObj = value;
	}

	/**
	 * 获取概率总和
	 * 
	 * @return 
	 * 
	 */
	public static <T> int sumProbability(List<ProbabilityObject<T>> pol) 
	{
		if (pol == null || pol.isEmpty())
		{
			return 0;
		}

		int sumProba = 0;

		for (ProbabilityObject<?> po : pol)
		{
			// 累加概率
			sumProba += po.getProbability();
		}

		return sumProba;
	}
}
