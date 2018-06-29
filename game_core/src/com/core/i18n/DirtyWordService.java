package com.core.i18n;

import com.common.InitializeRequired;

/**
 * 过滤词服务管理
 * @author Thinker
 * 
 */
public interface DirtyWordService extends InitializeRequired 
{
	/**
	 * 返回屏蔽字数组
	 * 
	 * @return
	 */
	String[] getDirtyWordsArray();

	/**
	 * 返回屏蔽名称数组
	 * 
	 * @return
	 */
	String[] getDirtyNamesArray();
}
