package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * BonusStoneAgeAppleTemplate
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BonusStoneAgeAppleTemplateVO extends TemplateObject {

	/** 老虎机类型 */
	@ExcelCellBinding(offset = 1)
	protected int slotsNum;

	/** 接住苹果的个数，最终奖励为：个数*单个倍数 */
	@ExcelCellBinding(offset = 2)
	protected int appleNum;

	/** 权值 */
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
					2, "[老虎机类型]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
	}
	
	public int getAppleNum() {
		return this.appleNum;
	}


	public final static int getAppleNumMinLimit() {
		return 0;
	}

	public void setAppleNum(int appleNum) {
		if (appleNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[接住苹果的个数，最终奖励为：个数*单个倍数]appleNum的值不得小于0");
		}
		this.appleNum = appleNum;
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
					4, "[权值]weight的值不得小于0");
		}
		this.weight = weight;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BonusStoneAgeAppleTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BonusStoneAgeAppleTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BonusStoneAgeAppleTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BonusStoneAgeAppleTemplateVO [  slotsNum=" + slotsNum + ", appleNum=" + appleNum + ", weight=" + weight + ",]";
	}
}