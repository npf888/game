package com.common;

/**
 *  用完之后需要销毁的对象接口
 *	@author Thinker
 */
public interface DestroyRequired
{
	/**销毁对象内容*/
	void destroy();
}
