package com.gameserver.recharge.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * WheelConditionTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class WheelConditionTemplateVO extends TemplateObject {

	/** 跟recharge表对应的pid 表示哪个需要翻倍 */
	@ExcelCellBinding(offset = 1)
	protected int pid;


	public int getPid() {
		return this.pid;
	}



	public void setPid(int pid) {
		this.pid = pid;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, WheelConditionTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends WheelConditionTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, WheelConditionTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "WheelConditionTemplateVO [  pid=" + pid + ",]";
	}
}