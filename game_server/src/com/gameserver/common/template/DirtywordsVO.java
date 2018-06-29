package com.gameserver.common.template;

import java.util.Map;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;
import com.core.annotation.ExcelRowBinding;
import com.core.template.TemplateObject;
import com.google.common.collect.Maps;

/**
 * DirtywordsTemplate
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class DirtywordsVO extends TemplateObject {

	/**  */
	@ExcelCellBinding(offset = 1)
	protected String words;


	public String getWords() {
		return this.words;
	}



	public void setWords(String words) {
		this.words = words;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, DirtywordsVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends DirtywordsVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, DirtywordsVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "DirtywordsVO [  words=" + words + ",]";
	}
}