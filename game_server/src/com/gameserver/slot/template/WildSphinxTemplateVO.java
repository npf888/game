package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * WildSphinxTemplate
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class WildSphinxTemplateVO extends TemplateObject {

	/** 1.老虎机类型 */
	@ExcelCellBinding(offset = 1)
	protected int slotsNum;

	/** 单线下注额倍数,选取的次数(百分比，实际值除以100） */
	@ExcelCellBinding(offset = 2)
	protected double times;

	/** 权重 */
	@ExcelCellBinding(offset = 3)
	protected int weight;


	public int getSlotsNum() {
		return this.slotsNum;
	}


	public final static int getSlotsNumMinLimit() {
		return 0;
	}

	public void setSlotsNum(int slotsNum) {
		if (slotsNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[1.老虎机类型]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
	}
	
	public double getTimes() {
		return this.times;
	}


	public final static double getTimesMinLimit() {
		return 0;
	}

	public void setTimes(double times) {
		if (times < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[单线下注额倍数,选取的次数(百分比，实际值除以100）]times的值不得小于0");
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
					4, "[权重]weight的值不得小于0");
		}
		this.weight = weight;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, WildSphinxTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends WildSphinxTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, WildSphinxTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "WildSphinxTemplateVO [  slotsNum=" + slotsNum + ", times=" + times + ", weight=" + weight + ",]";
	}
}