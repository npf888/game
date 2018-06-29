package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * BonusNinjaRewardTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BonusNinjaRewardTemplateVO extends TemplateObject {

	/** 老虎机类型 */
	@ExcelCellBinding(offset = 1)
	protected int slotsNum;

	/** 是否中奖 1 中，0 否 */
	@ExcelCellBinding(offset = 2)
	protected int type;

	/** 单线下注额倍数,选取的次数(百分比，实际值除以100） */
	@ExcelCellBinding(offset = 3)
	protected int times;

	/** 权值 */
	@ExcelCellBinding(offset = 4)
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
					2, "[老虎机类型]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
	}
	
	public int getType() {
		return this.type;
	}


	public final static int getTypeMinLimit() {
		return 0;
	}

	public void setType(int type) {
		if (type < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[是否中奖 1 中，0 否]type的值不得小于0");
		}
		this.type = type;
	}
	
	public int getTimes() {
		return this.times;
	}


	public final static int getTimesMinLimit() {
		return 0;
	}

	public void setTimes(int times) {
		if (times < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[单线下注额倍数,选取的次数(百分比，实际值除以100）]times的值不得小于0");
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
					5, "[权值]weight的值不得小于0");
		}
		this.weight = weight;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BonusNinjaRewardTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BonusNinjaRewardTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BonusNinjaRewardTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BonusNinjaRewardTemplateVO [  slotsNum=" + slotsNum + ", type=" + type + ", times=" + times + ", weight=" + weight + ",]";
	}
}