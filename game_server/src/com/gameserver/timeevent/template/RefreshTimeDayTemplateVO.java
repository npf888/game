package com.gameserver.timeevent.template;

import java.util.Map;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;
import com.core.annotation.ExcelRowBinding;
import com.core.template.TemplateObject;
import com.core.util.StringUtils;
import com.google.common.collect.Maps;

/**
 * 每日刷新时间
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class RefreshTimeDayTemplateVO extends TemplateObject {

	/** 刷新时间字符串 */
	@ExcelCellBinding(offset = 1)
	protected String refreshTimeStr;


	public String getRefreshTimeStr() {
		return this.refreshTimeStr;
	}



	public void setRefreshTimeStr(String refreshTimeStr) {
		if (StringUtils.isEmpty(refreshTimeStr)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[刷新时间字符串]refreshTimeStr不可以为空");
		}
		this.refreshTimeStr = refreshTimeStr;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, RefreshTimeDayTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends RefreshTimeDayTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, RefreshTimeDayTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "RefreshTimeDayTemplateVO [  refreshTimeStr=" + refreshTimeStr + ",]";
	}
}