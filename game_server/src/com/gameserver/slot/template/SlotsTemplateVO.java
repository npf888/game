package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * slots
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class SlotsTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** icon */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** 下限 */
	@ExcelCellBinding(offset = 5)
	protected int slotsNum;

	/** 下限 */
	@ExcelCellBinding(offset = 6)
	protected int turn;

	/** 种类 */
	@ExcelCellBinding(offset = 7)
	protected int type;

	/** 支付表所在页数，0：不显示，1：第一页，2：第二页 */
	@ExcelCellBinding(offset = 8)
	protected int pages;


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
		if (StringUtils.isEmpty(langDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[多语言描述]langDesc不可以为空");
		}
		this.langDesc = langDesc;
	}
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public int getSlotsNum() {
		return this.slotsNum;
	}


	public final static int getSlotsNumMinLimit() {
		return 0;
	}

	public void setSlotsNum(int slotsNum) {
		if (slotsNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[下限]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
	}
	
	public int getTurn() {
		return this.turn;
	}


	public final static int getTurnMinLimit() {
		return 0;
	}

	public void setTurn(int turn) {
		if (turn < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[下限]turn的值不得小于0");
		}
		this.turn = turn;
	}
	
	public int getType() {
		return this.type;
	}


	public final static int getTypeMinLimit() {
		return 0;
	}

	public void setType(int type) {
		if (type < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[种类]type的值不得小于0");
		}
		this.type = type;
	}
	
	public int getPages() {
		return this.pages;
	}


	public final static int getPagesMinLimit() {
		return 0;
	}

	public void setPages(int pages) {
		if (pages < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[支付表所在页数，0：不显示，1：第一页，2：第二页]pages的值不得小于0");
		}
		this.pages = pages;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, SlotsTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends SlotsTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, SlotsTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "SlotsTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", slotsNum=" + slotsNum + ", turn=" + turn + ", type=" + type + ", pages=" + pages + ",]";
	}
}