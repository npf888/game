package com.core.i18n.impl;

import java.text.MessageFormat;

import com.core.i18n.I18NDictionary;
import com.core.i18n.SysLangService;

/**
 * 多语言管理器
 * @author Thinker
 * 
 */
public class SysLangServiceImpl implements SysLangService
{
	/** 单引号 */
	private static final String QUOTE = "__&quot;__";
	/** 多语言容器 */
	private I18NDictionary<Integer, String> _sysLangs;

	/**
	 * 
	 * @param sysLangConfigFile
	 *            多语言配置的文件路径
	 */
	public SysLangServiceImpl(final String sysLangConfigFile)
	{
		_sysLangs = new ExcelIntDictionaryImpl(sysLangConfigFile);
	}

	@Override
	public String read(Integer key) 
	{
		return _sysLangs.read(key);
	}

	@Override
	public String read(Integer key, Object... params) 
	{
		String _msg = _sysLangs.read(key);
		if (_msg == null) 
		{
			return "lang_" + key;
		}
		if (params != null)
		{
			_msg = _msg.replaceAll("'", QUOTE);
			_msg = MessageFormat.format(_msg, params);
			_msg = _msg.replaceAll(QUOTE, "'");

			return _msg;
		} else 
		{
			return _msg;
		}
	}

	@Override
	public void init()
	{
		
	}

	/**
	 * 校验 sys_lang.xls 文件
	 * 
	 */
	public void check()
	{
		for (Integer key : _sysLangs.getKeySet()) 
		{
			try 
			{
				// 替换多语言字符串
				this.read(key, 
					"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", 
					"a", "b", "c", "d", "e", "f");
			} catch (Exception ex)
			{
				// 抛出运行时异常
				throw new RuntimeException("sys_lang.xls 文件错误, ID=" + key + ", value=" + this.read(key), ex);
			}
		}
	}
}
