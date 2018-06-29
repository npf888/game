package com.gameserver.texas.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * interactiveItem
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class SngMatchConfigTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int lv;

	/** sng id */
	@ExcelCellBinding(offset = 2)
	protected int fieldId;

	/** 起始小盲注 */
	@ExcelCellBinding(offset = 3)
	protected int smallBlind;

	/** 升盲时间 */
	@ExcelCellBinding(offset = 4)
	protected int blindTime;


	public int getLv() {
		return this.lv;
	}



	public void setLv(int lv) {
		this.lv = lv;
	}
	
	public int getFieldId() {
		return this.fieldId;
	}



	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}
	
	public int getSmallBlind() {
		return this.smallBlind;
	}



	public void setSmallBlind(int smallBlind) {
		if (smallBlind == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[起始小盲注]smallBlind不可以为0");
		}
		this.smallBlind = smallBlind;
	}
	
	public int getBlindTime() {
		return this.blindTime;
	}



	public void setBlindTime(int blindTime) {
		this.blindTime = blindTime;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, SngMatchConfigTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends SngMatchConfigTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, SngMatchConfigTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "SngMatchConfigTemplateVO [  lv=" + lv + ", fieldId=" + fieldId + ", smallBlind=" + smallBlind + ", blindTime=" + blindTime + ",]";
	}
}