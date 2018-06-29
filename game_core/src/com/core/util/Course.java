package com.core.util;

/**
 * 过程枚举，表示一件事的准备、进行和结束三阶段
 * @author Thinker
 *
 */
public enum Course 
{
	/**
	 * 准备阶段，尚未开始
	 */
	Prepare,
	
	/**
	 * 进行中的阶段
	 */
	Underway,
	
	/**
	 * 结束状态
	 */
	Terminate;
}
