package com.gameserver.task.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * CategoryLevel
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class CategoryLevelTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 描述 */
	@ExcelCellBinding(offset = 2)
	protected String descrip;

	/** 第二大分类 */
	@ExcelCellBinding(offset = 3)
	protected int category;

	/** 最大分类 */
	@ExcelCellBinding(offset = 4)
	protected int categoryMissionLevel;

	/** 随机个数 */
	@ExcelCellBinding(offset = 5)
	protected int num;


	public int getNameId() {
		return this.nameId;
	}



	public void setNameId(int nameId) {
		this.nameId = nameId;
	}
	
	public String getDescrip() {
		return this.descrip;
	}



	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	
	public int getCategory() {
		return this.category;
	}



	public void setCategory(int category) {
		this.category = category;
	}
	
	public int getCategoryMissionLevel() {
		return this.categoryMissionLevel;
	}



	public void setCategoryMissionLevel(int categoryMissionLevel) {
		this.categoryMissionLevel = categoryMissionLevel;
	}
	
	public int getNum() {
		return this.num;
	}



	public void setNum(int num) {
		this.num = num;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, CategoryLevelTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends CategoryLevelTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, CategoryLevelTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "CategoryLevelTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", category=" + category + ", categoryMissionLevel=" + categoryMissionLevel + ", num=" + num + ",]";
	}
}