package com.gameserver.common.i18n;

import com.common.Reloadable;
import com.core.i18n.SysLangService;
import com.core.template.TemplateService;

/**
 * 多语言管理器
 * @author Thinker
 */
public interface LangService extends Reloadable 
{
	/**
	 * 读系统多语言数据
	 * 
	 * @param key
	 * @return
	 */
	String readSysLang(Integer key);

	/**
	 * 读取系统内部的多语言数据,如果params不为空,则用其格式化结果
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	String readSysLang(Integer key, Object... params);

	String readSysLang(Integer key, Object param);

	String readSysLang(Integer key, Object param1, Object param2);

	/**
	 * 度excel表中的多语言数据
	 * 
	 * @param key
	 * @return
	 */
	String readExcelLang(String key);

	/**
	 * 从templateManager中读取各个template的名字数据建立NamelookupTable<br/>
	 * NPC、怪物的名字因为涉及到其坐标不再此处做，在各自的manager初始化对象时做
	 * 
	 * @param templateManager
	 */
	public void initNameLookupTable(TemplateService templateManager);

	/**
	 * 获取名字查找表
	 * 
	 * @return
	 */
	public NameLookupTable getNameLookupTable();

	/**
	 * 在所有excel中的多语言读取完成后调用，做一些清理工作
	 */
	public void onAllExcelLangRead();

	/**
	 * 获取SysLangService
	 * 
	 * @return
	 */
	public SysLangService getSysLangSerivce();
}
