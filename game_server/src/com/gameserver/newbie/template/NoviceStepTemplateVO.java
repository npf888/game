package com.gameserver.newbie.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * NoviceStepTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class NoviceStepTemplateVO extends TemplateObject {

	/** 描述 */
	@ExcelCellBinding(offset = 1)
	protected String stepDesc;

	/** 步骤 */
	@ExcelCellBinding(offset = 2)
	protected int step;

	/** 小步 */
	@ExcelCellBinding(offset = 3)
	protected int substep;


	public String getStepDesc() {
		return this.stepDesc;
	}



	public void setStepDesc(String stepDesc) {
		this.stepDesc = stepDesc;
	}
	
	public int getStep() {
		return this.step;
	}



	public void setStep(int step) {
		this.step = step;
	}
	
	public int getSubstep() {
		return this.substep;
	}



	public void setSubstep(int substep) {
		this.substep = substep;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, NoviceStepTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends NoviceStepTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, NoviceStepTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "NoviceStepTemplateVO [  stepDesc=" + stepDesc + ", step=" + step + ", substep=" + substep + ",]";
	}
}