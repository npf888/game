package com.common.constants;

/**
 * 系统消息显示类型
 * @author Thinker
 */
public interface SysMsgShowTypes
{
	/** 普通消息类 */
	short generic = 1;
	/** 重要消息类 */
	short important = 2;
	/** 操作错误提示,直接冒出提示，也就是操作反馈 */
	short error = 3;

}