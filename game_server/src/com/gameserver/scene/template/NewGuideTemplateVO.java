package com.gameserver.scene.template;

import java.util.Map;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;
import com.core.annotation.ExcelRowBinding;
import com.core.template.TemplateObject;
import com.google.common.collect.Maps;

/**
 * 新手行为表
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class NewGuideTemplateVO extends TemplateObject {

	/** 引导分组ID */
	@ExcelCellBinding(offset = 1)
	protected int guideId;

	/** 引导步骤ID */
	@ExcelCellBinding(offset = 2)
	protected int guideStepId;

	/** 完成引导关键步骤 */
	@ExcelCellBinding(offset = 3)
	protected int guideStep1Id;


	public int getGuideId() {
		return this.guideId;
	}



	public void setGuideId(int guideId) {
		this.guideId = guideId;
	}
	
	public int getGuideStepId() {
		return this.guideStepId;
	}



	public void setGuideStepId(int guideStepId) {
		this.guideStepId = guideStepId;
	}
	
	public int getGuideStep1Id() {
		return this.guideStep1Id;
	}



	public void setGuideStep1Id(int guideStep1Id) {
		this.guideStep1Id = guideStep1Id;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, NewGuideTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends NewGuideTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, NewGuideTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "NewGuideTemplateVO [  guideId=" + guideId + ", guideStepId=" + guideStepId + ", guideStep1Id=" + guideStep1Id + ",]";
	}
}