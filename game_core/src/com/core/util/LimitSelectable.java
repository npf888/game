package com.core.util;

/**
 * 在同类对象有序集合中将要根据其上限或下限进行选择的对象
 * @author Thinker
 *
 */
public interface LimitSelectable 
{

	/**
	 * @return smallToLarge为true则是上限，反之为下限
	 */
	double getLimit();
	
	/**
	 * @return 从小到大
	 */
	boolean smallToLarge();
}
