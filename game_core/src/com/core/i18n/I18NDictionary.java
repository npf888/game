package com.core.i18n;

import java.util.Collection;
import java.util.Set;

/**
 * @author Thinker
 * 
 */
public interface I18NDictionary<K, V>
{

	/**
	 * 读取指定的数据
	 * 
	 * @param key
	 * @return 
	 * 
	 */
	abstract V read(K key);

	/**
	 * 加载数据 
	 * 
	 */
	abstract void load();

	/**
	 * 获取关键字集合
	 * 
	 * @return
	 */
	abstract Set<K> getKeySet();

	/**
	 * 获取数据值集合
	 * 
	 * @return
	 */
	abstract Collection<V> getValues(); 
}