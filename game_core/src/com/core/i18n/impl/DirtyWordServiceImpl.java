package com.core.i18n.impl;

import java.util.Collection;

import com.core.i18n.DirtyWordService;
import com.core.i18n.I18NDictionary;

/**
 * 过滤词管理器
 * 
 * @author Thinker
 * 
 */
public class DirtyWordServiceImpl implements DirtyWordService 
{
	/** excel 文件名称 */
	private String _xlsFilename;
	/** 屏蔽字 */
	private I18NDictionary<Integer, String> _dirtyWords;
	/** 屏蔽名称 */
	private I18NDictionary<Integer, String> _dirtyNames;

	/**
	 * 类参数构造器
	 * 
	 * @param xlsFilename 屏蔽字 excel 文件名称
	 * @throws IllegalArgumentException if xlsFilename == null
	 * 
	 */
	public DirtyWordServiceImpl(String xlsFilename)
	{
		if (xlsFilename == null) 
		{
			throw new IllegalArgumentException("xlsFilename");
		}

		this._xlsFilename = xlsFilename;
	}

	@Override
	public void init()
	{
		this._dirtyWords = new ExcelIntDictionaryImpl(this._xlsFilename, 0);
		this._dirtyNames = new ExcelIntDictionaryImpl(this._xlsFilename, 1);
	}

	/**
	 * 获取屏蔽字
	 * 
	 * @return
	 */
	public I18NDictionary<Integer, String> getDirtyWords()
	{
		return this._dirtyWords;
	}

	/**
	 * 获取屏蔽名称
	 * 
	 * @return
	 */
	public I18NDictionary<Integer, String> getDirtyNames()
	{
		return this._dirtyNames;
	}

	/**
	 * 返回屏蔽字数组
	 * 
	 * @return
	 */
	public String[] getDirtyWordsArray() 
	{
		if (this._dirtyWords == null) 
		{
			return new String[0];
		}

		Collection<String> vals = this._dirtyWords.getValues();

		if (vals == null)
		{
			return new String[0];
		}

		return vals.toArray(new String[0]);
	}

	/**
	 * 返回屏蔽名称数组
	 * 
	 * @return
	 */
	public String[] getDirtyNamesArray()
	{
		if (this._dirtyNames == null) 
		{
			return new String[0];
		}

		Collection<String> vals = this._dirtyNames.getValues();

		if (vals == null) 
		{
			return new String[0];
		}

		return vals.toArray(new String[0]);
	}
}
