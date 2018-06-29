package com.gameserver.common.i18n;

import com.core.template.TemplateService;

/**
 * 应用标记
 * @author Thinker
 */
public interface RefMark<T> {

	/**
	 * 将标记转换成对应的html
	 * 
	 * @param obj
	 * @param mark
	 * @param lut
	 * @param templateService
	 * @return
	 * @throws Exception
	 */
	public String transToHtml(T obj, String mark, NameLookupTable lut,
			TemplateService templateService) throws Exception;
}
