package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * ScatterWheel
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ScatterWheelTemplateVO extends TemplateObject {

	/** 老虎机类型 */
	@ExcelCellBinding(offset = 1)
	protected int slotType;

	/** 中奖图标数量 */
	@ExcelCellBinding(offset = 2)
	protected int scatterNum;


	public int getSlotType() {
		return this.slotType;
	}


	public final static int getSlotTypeMinLimit() {
		return 0;
	}

	public void setSlotType(int slotType) {
		if (slotType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[老虎机类型]slotType的值不得小于0");
		}
		this.slotType = slotType;
	}
	
	public int getScatterNum() {
		return this.scatterNum;
	}


	public final static int getScatterNumMinLimit() {
		return 0;
	}

	public void setScatterNum(int scatterNum) {
		if (scatterNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[中奖图标数量]scatterNum的值不得小于0");
		}
		this.scatterNum = scatterNum;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ScatterWheelTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ScatterWheelTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ScatterWheelTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ScatterWheelTemplateVO [  slotType=" + slotType + ", scatterNum=" + scatterNum + ",]";
	}
}