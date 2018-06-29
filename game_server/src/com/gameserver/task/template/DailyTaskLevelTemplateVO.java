package com.gameserver.task.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * DailyTaskLevel
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class DailyTaskLevelTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 描述 */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

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
	
	public int getDescrip() {
		return this.descrip;
	}



	public void setDescrip(int descrip) {
		this.descrip = descrip;
	}
	
	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		this.langDesc = langDesc;
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
	protected final static Map<Integer, DailyTaskLevelTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends DailyTaskLevelTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, DailyTaskLevelTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "DailyTaskLevelTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", categoryMissionLevel=" + categoryMissionLevel + ", num=" + num + ",]";
	}
}