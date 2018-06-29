package com.core.i18n.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.constants.CommonErrorLogInfo;
import com.core.i18n.I18NDictionary;
import com.core.util.ErrorsUtil;
import com.core.util.PoiUtils;

/**
 * 从 Excel 文件中加载数据, 主键是 Integer 类型
 * @author Thinker
 * 
 */
public class ExcelIntDictionaryImpl implements I18NDictionary<Integer, String>
{
	/** 日志 */
	private static final Logger _log = LoggerFactory.getLogger("template");
	/** 文件名 */
	private final String _fileName;
	/** 表单索引 */
	private int _sheetIndex = 0;
	/** 字典数据 */
	private final Map<Integer, String> _data = new HashMap<Integer, String>();

	/**
	 * 类参数构造器
	 * 
	 * @param fileName excel 文件名称 
	 * 
	 */
	public ExcelIntDictionaryImpl(String fileName)
	{
		this(fileName, 0);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param fileName excel 文件名称
	 * @param sheetIndex 表单索引
	 * @throws IllegalArgumentException if fileName == null
	 * 
	 */
	public ExcelIntDictionaryImpl(String fileName, int sheetIndex) 
	{
		if (fileName == null)
		{
			throw new IllegalArgumentException("fileName");
		}

		this._fileName = fileName;
		this._sheetIndex = sheetIndex < 0 ? 0 : sheetIndex;
		this.load();
	}

	@Override
	public String read(Integer val) 
	{
		String _val = _data.get(val);
		if (_val == null)
		{
			return val.toString();
		} else
		{
			return _val;
		}
	}

	@Override
	public void load()
	{
		HSSFWorkbook workbook = null;
		InputStream ins = null;
		try {
			ins = new FileInputStream(_fileName);
			workbook = new HSSFWorkbook(new POIFSFileSystem(ins));
			HSSFSheet sheet = workbook.getSheetAt(this._sheetIndex);
			int rowNumber = sheet.getLastRowNum();
			for (int rowIdxForExcel = 0; rowIdxForExcel <= rowNumber; rowIdxForExcel++) {
				HSSFRow row = sheet.getRow(rowIdxForExcel);
				if (row == null) {
					continue;
				} else {
					int key = PoiUtils.getIntValue(row.getCell(0));
					String value = (String)PoiUtils.getStringValue(row.getCell(1));
					if (key > 0) {
						if (!_data.containsKey(key)) {
							_data.put(key, value);
						} else {
							if (_log.isWarnEnabled()) {
								_log.warn(ErrorsUtil.error(CommonErrorLogInfo.CONFIG_DUP_FAIL, "Warn", _fileName + "[key:" + key
										+ ",value:" + value + "]"));
							}
						}
					}
				}
			}
		} catch (Exception ex)
		{
			if (_log.isErrorEnabled())
			{
				_log.error(String.format("multi-language(%s) excel load error", _fileName), ex);
			}
			throw new RuntimeException(ex);
		} finally
		{
			if (ins != null)
			{
				try 
				{
					ins.close();
				} catch (IOException ex)
				{
					
				}
			}
		}
	}

	@Override
	public Set<Integer> getKeySet()
	{
		return this._data.keySet();
	}

	@Override
	public Collection<String> getValues() 
	{
		return this._data.values();
	}
}
