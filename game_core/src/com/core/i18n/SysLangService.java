package com.core.i18n;

import com.common.InitializeRequired;

/**
 * 多语言管理服务管理
 * @author Thinker
 *
 */
public interface SysLangService extends InitializeRequired
{
	/**
	 * 检查多语言资源 sys_lang.xls
	 * 
	 */
	void check();
	
	/**
	 * 读取系统内部的多语言数据
	 * 
	 * @param key
	 * @return
	 */
	String read(Integer key);
	
	/**
	 * 读取系统内部的多语言数据,如果params不为空,则用其格式化结果
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	String read(Integer key, Object... params);




}
