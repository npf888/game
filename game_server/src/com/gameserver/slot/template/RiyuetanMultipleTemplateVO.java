package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * RiyuetanMultipleTemplate
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class RiyuetanMultipleTemplateVO extends TemplateObject {

	/** 倍数值除100 */
	@ExcelCellBinding(offset = 1)
	protected int times;

	/** 权重 */
	@ExcelCellBinding(offset = 2)
	protected int weight;


	public int getTimes() {
		return this.times;
	}



	public void setTimes(int times) {
		this.times = times;
	}
	
	public int getWeight() {
		return this.weight;
	}



	public void setWeight(int weight) {
		this.weight = weight;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, RiyuetanMultipleTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends RiyuetanMultipleTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, RiyuetanMultipleTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "RiyuetanMultipleTemplateVO [  times=" + times + ", weight=" + weight + ",]";
	}
}