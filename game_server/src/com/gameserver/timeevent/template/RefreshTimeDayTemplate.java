package com.gameserver.timeevent.template;

import java.util.HashMap;
import java.util.Map;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;

/**
 * 每日刷新时间模板
 * 
 * @author Thinker
 * 
 */
@ExcelRowBinding
public class RefreshTimeDayTemplate extends RefreshTimeDayTemplateVO {
	/** 模板字典 */
	private static Map<Integer, RefreshTimeDayTemplate> _tmplMap = null;

	@Override
	public void check() 
		throws TemplateConfigException {
		// 添加模板对象到字典
		putToMap(this);
	}

	/**
	 * 添加模板对象到字典
	 * 
	 * @param value 
	 * 
	 */
	private static void putToMap(RefreshTimeDayTemplate value) {
		if (value == null) {
			return;
		}

		if (_tmplMap == null) {
			// 创建模板字典
			_tmplMap = new HashMap<Integer, RefreshTimeDayTemplate>();
		}

		// 添加模板对象到字典
		_tmplMap.put(
			value.getId(), value
		);
	}

	/**
	 * 获取刷新时间字符串, 格式 = "00:00:00"
	 * 
	 * @param templateID 模板 ID
	 * @return 
	 * 
	 */
	public static String getRefreshTimeStr(int templateID) {
		// 获取今日刷新时间字符串
		RefreshTimeDayTemplate tmplObj = _tmplMap.get(templateID);

		if (tmplObj == null) {
			return null;
		} else {
			return tmplObj.getRefreshTimeStr();
		}
	}
}
