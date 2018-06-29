package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * BonusStoneAgePreyTemplate
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BonusStoneAgePreyTemplateVO extends TemplateObject {

	/** 老虎机类型 */
	@ExcelCellBinding(offset = 1)
	protected int slotsNum;

	/** 捕住猎物的个数，最终奖励为：个数*单个倍数 */
	@ExcelCellBinding(offset = 2)
	protected int PreyNum;

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
	
	public int getPreyNum() {
		return this.PreyNum;
	}


	public final static int getPreyNumMinLimit() {
		return 0;
	}

	public void setPreyNum(int PreyNum) {
		if (PreyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[捕住猎物的个数，最终奖励为：个数*单个倍数]PreyNum的值不得小于0");
		}
		this.PreyNum = PreyNum;
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
	protected final static Map<Integer, BonusStoneAgePreyTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BonusStoneAgePreyTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BonusStoneAgePreyTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BonusStoneAgePreyTemplateVO [  slotsNum=" + slotsNum + ", PreyNum=" + PreyNum + ", weight=" + weight + ",]";
	}
}