package com.gameserver.item.template;

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
public abstract class InteractiveItemTemplateVO extends TemplateObject {

	/** 语言Id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 名称 */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** 图标 */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** 花费筹码 */
	@ExcelCellBinding(offset = 5)
	protected int costChips;

	/** 魅力值改变 */
	@ExcelCellBinding(offset = 6)
	protected int changeCharm;

	/** 品质 */
	@ExcelCellBinding(offset = 7)
	protected int quality;


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
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public int getCostChips() {
		return this.costChips;
	}



	public void setCostChips(int costChips) {
		this.costChips = costChips;
	}
	
	public int getChangeCharm() {
		return this.changeCharm;
	}



	public void setChangeCharm(int changeCharm) {
		this.changeCharm = changeCharm;
	}
	
	public int getQuality() {
		return this.quality;
	}



	public void setQuality(int quality) {
		this.quality = quality;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, InteractiveItemTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends InteractiveItemTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, InteractiveItemTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "InteractiveItemTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", costChips=" + costChips + ", changeCharm=" + changeCharm + ", quality=" + quality + ",]";
	}
}