package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * WinMultipleTemplate
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class WinMultipleTemplateVO extends TemplateObject {

	/** 倍数 */
	@ExcelCellBinding(offset = 1)
	protected int times;

	/** 权重 */
	@ExcelCellBinding(offset = 2)
	protected int weight;


	public int getTimes() {
		return this.times;
	}


	public final static int getTimesMinLimit() {
		return 0;
	}

	public void setTimes(int times) {
		if (times < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[倍数]times的值不得小于0");
		}
		this.times = times;
	}
	
	public int getWeight() {
		return this.weight;
	}


	public final static int getWeightMinLimit() {
		return 0;
	}

	public void setWeight(int weight) {
		if (weight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[权重]weight的值不得小于0");
		}
		this.weight = weight;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, WinMultipleTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends WinMultipleTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, WinMultipleTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "WinMultipleTemplateVO [  times=" + times + ", weight=" + weight + ",]";
	}
}