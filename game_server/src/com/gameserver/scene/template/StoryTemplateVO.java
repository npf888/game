package com.gameserver.scene.template;

import java.util.Map;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;
import com.core.annotation.ExcelRowBinding;
import com.core.template.TemplateObject;
import com.google.common.collect.Maps;

/**
 * 剧情表
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class StoryTemplateVO extends TemplateObject {

	/** 触发事件 */
	@ExcelCellBinding(offset = 1)
	protected int eventType;

	/** 触发参数A */
	@ExcelCellBinding(offset = 2)
	protected int paramA;

	/** 触发参数B */
	@ExcelCellBinding(offset = 3)
	protected int paramB;

	/** 触发参数C */
	@ExcelCellBinding(offset = 4)
	protected int paramC;


	public int getEventType() {
		return this.eventType;
	}



	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	
	public int getParamA() {
		return this.paramA;
	}



	public void setParamA(int paramA) {
		this.paramA = paramA;
	}
	
	public int getParamB() {
		return this.paramB;
	}



	public void setParamB(int paramB) {
		this.paramB = paramB;
	}
	
	public int getParamC() {
		return this.paramC;
	}



	public void setParamC(int paramC) {
		this.paramC = paramC;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, StoryTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends StoryTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, StoryTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "StoryTemplateVO [  eventType=" + eventType + ", paramA=" + paramA + ", paramB=" + paramB + ", paramC=" + paramC + ",]";
	}
}