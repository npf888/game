package com.core.util;

/**
 * 将对象中的数据转化成json格式字符串的转化器
 * @author Thinker
 */
public interface JsonPackageSerializer <T> 
{
	/**
	 * 序列化
	 * 将obj对象中的相关数据取出，串行化为json字符串
	 * @param obj
	 * @return
	 */
	public String serialize(T obj);
	
	/**
	 * 反序列化
	 * 将json字符串里的信息抽取，反馈到对象中
	 * @param obj
	 * @param pack
	 */
	public void deserialize(T obj, String pack);
}
