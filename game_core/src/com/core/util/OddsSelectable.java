package com.core.util;

/**
 * 在同类对象集合中将要根据其概率进行随机选择的对象
 * @author Thinker
 *
 */
public interface OddsSelectable
{
	/**
	 * @return 此对象被选中的概率
	 */
	double getOdds();

}
