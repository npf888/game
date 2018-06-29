package com.common;

import java.util.Map;

/**
 *  提供消息编号与消息类的映射关系
 *  @author Thinker
 *
 */
public interface MessageMappingProvider
{
	/**
	 * 获得消息类型和消息类的映射关系
	 * @return
	 */
	public Map<Short, Class<?>> getMessageMapping();
}
