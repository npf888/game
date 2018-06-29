package com.gameserver.club.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * ClubIconTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ClubIconTemplateVO extends TemplateObject {

	/** 名称id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** ico */
	@ExcelCellBinding(offset = 3)
	protected String icon;

	/** 注释 */
	@ExcelCellBinding(offset = 4)
	protected String langDesc;

	/** type */
	@ExcelCellBinding(offset = 5)
	protected int type;

	/** 条件 */
	@ExcelCellBinding(offset = 6)
	protected int condition;

	/** 值 */
	@ExcelCellBinding(offset = 7)
	protected int value;


	public int getNameId() {
		return this.nameId;
	}



	public void setNameId(int nameId) {
		this.nameId = nameId;
	}
	
	public int getDescrip() {
		return this.descrip;
	}



	public void setDescrip(int descrip) {
		this.descrip = descrip;
	}
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		this.langDesc = langDesc;
	}
	
	public int getType() {
		return this.type;
	}



	public void setType(int type) {
		this.type = type;
	}
	
	public int getCondition() {
		return this.condition;
	}



	public void setCondition(int condition) {
		this.condition = condition;
	}
	
	public int getValue() {
		return this.value;
	}



	public void setValue(int value) {
		this.value = value;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ClubIconTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ClubIconTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ClubIconTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ClubIconTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", icon=" + icon + ", langDesc=" + langDesc + ", type=" + type + ", condition=" + condition + ", value=" + value + ",]";
	}
}