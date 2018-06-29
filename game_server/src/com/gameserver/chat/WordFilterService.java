package com.gameserver.chat;

/**
 * 聊天不良信息过滤接口
 * @author Thinker
 */
public interface WordFilterService
{
	/**
	 * 过滤聊天信息
	 * 
	 * @param info
	 * @return
	 */
	String filter(String info);

	/**
	 * 判断是否包含关键字
	 * 
	 * @param info
	 * @return
	 */
	boolean containKeywords(String info);

	/**
	 * 过滤掉HTML标签
	 * 
	 * @param info
	 * @return
	 */
	String filterHtmlTag(String info);
}
