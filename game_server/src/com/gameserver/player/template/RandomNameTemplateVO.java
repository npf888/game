package com.gameserver.player.template;

import java.util.Map;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;
import com.core.annotation.ExcelRowBinding;
import com.core.template.TemplateObject;
import com.google.common.collect.Maps;

/**
 * 随机名信息表
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class RandomNameTemplateVO extends TemplateObject {

	/** 角色性多语言ID */
	@ExcelCellBinding(offset = 1)
	protected int prevNameLangId;

	/** 性枚举 */
	@ExcelCellBinding(offset = 2)
	protected String prevName;

	/** 角色名多语言ID */
	@ExcelCellBinding(offset = 3)
	protected int nextNameLangId;

	/** 名字枚举 */
	@ExcelCellBinding(offset = 4)
	protected String nextName;


	public int getPrevNameLangId() {
		return this.prevNameLangId;
	}


	public final static int getPrevNameLangIdMinLimit() {
		return 0;
	}

	public void setPrevNameLangId(int prevNameLangId) {
		if (prevNameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[角色性多语言ID]prevNameLangId的值不得小于0");
		}
		this.prevNameLangId = prevNameLangId;
	}
	
	public String getPrevName() {
		return this.prevName;
	}



	public void setPrevName(String prevName) {
		this.prevName = prevName;
	}
	
	public int getNextNameLangId() {
		return this.nextNameLangId;
	}


	public final static int getNextNameLangIdMinLimit() {
		return 0;
	}

	public void setNextNameLangId(int nextNameLangId) {
		if (nextNameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[角色名多语言ID]nextNameLangId的值不得小于0");
		}
		this.nextNameLangId = nextNameLangId;
	}
	
	public String getNextName() {
		return this.nextName;
	}



	public void setNextName(String nextName) {
		this.nextName = nextName;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, RandomNameTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends RandomNameTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, RandomNameTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "RandomNameTemplateVO [  prevNameLangId=" + prevNameLangId + ", prevName=" + prevName + ", nextNameLangId=" + nextNameLangId + ", nextName=" + nextName + ",]";
	}
}