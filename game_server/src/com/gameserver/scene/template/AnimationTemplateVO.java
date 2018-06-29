package com.gameserver.scene.template;

import java.util.Map;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;
import com.core.annotation.ExcelRowBinding;
import com.core.template.TemplateObject;
import com.core.util.StringUtils;
import com.google.common.collect.Maps;

/**
 * 剧情动画表
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class AnimationTemplateVO extends TemplateObject {

	/** 动画API */
	@ExcelCellBinding(offset = 1)
	protected String API;

	/** 动画参数A */
	@ExcelCellBinding(offset = 2)
	protected int paramA;

	/** 动画参数B */
	@ExcelCellBinding(offset = 3)
	protected int paramB;


	public String getAPI() {
		return this.API;
	}



	public void setAPI(String API) {
		if (StringUtils.isEmpty(API)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[动画API]API不可以为空");
		}
		this.API = API;
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
	

	/** 模板字典 */
	protected final static Map<Integer, AnimationTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends AnimationTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, AnimationTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "AnimationTemplateVO [  API=" + API + ", paramA=" + paramA + ", paramB=" + paramB + ",]";
	}
}