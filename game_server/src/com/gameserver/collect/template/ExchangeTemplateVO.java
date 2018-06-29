package com.gameserver.collect.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * ExchangeTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ExchangeTemplateVO extends TemplateObject {

	/** 多语言描述 */
	@ExcelCellBinding(offset = 1)
	protected String langDesc;

	/** icon */
	@ExcelCellBinding(offset = 2)
	protected String icon;

	/** 消耗类型 */
	@ExcelCellBinding(offset = 3)
	protected int itemId;

	/** 数量 */
	@ExcelCellBinding(offset = 4)
	protected int consumeCount;

	/** 物品ID */
	@ExcelCellBinding(offset = 5)
	protected int exchangeID;

	/** 数量 */
	@ExcelCellBinding(offset = 6)
	protected int exchangeCount;

	/** 类型 */
	@ExcelCellBinding(offset = 7)
	protected int sort;


	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		if (StringUtils.isEmpty(langDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[多语言描述]langDesc不可以为空");
		}
		this.langDesc = langDesc;
	}
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		if (StringUtils.isEmpty(icon)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[icon]icon不可以为空");
		}
		this.icon = icon;
	}
	
	public int getItemId() {
		return this.itemId;
	}


	public final static int getItemIdMinLimit() {
		return 0;
	}

	public void setItemId(int itemId) {
		if (itemId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[消耗类型]itemId的值不得小于0");
		}
		this.itemId = itemId;
	}
	
	public int getConsumeCount() {
		return this.consumeCount;
	}


	public final static int getConsumeCountMinLimit() {
		return 0;
	}

	public void setConsumeCount(int consumeCount) {
		if (consumeCount < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[数量]consumeCount的值不得小于0");
		}
		this.consumeCount = consumeCount;
	}
	
	public int getExchangeID() {
		return this.exchangeID;
	}


	public final static int getExchangeIDMinLimit() {
		return 0;
	}

	public void setExchangeID(int exchangeID) {
		if (exchangeID < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[物品ID]exchangeID的值不得小于0");
		}
		this.exchangeID = exchangeID;
	}
	
	public int getExchangeCount() {
		return this.exchangeCount;
	}


	public final static int getExchangeCountMinLimit() {
		return 0;
	}

	public void setExchangeCount(int exchangeCount) {
		if (exchangeCount < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[数量]exchangeCount的值不得小于0");
		}
		this.exchangeCount = exchangeCount;
	}
	
	public int getSort() {
		return this.sort;
	}


	public final static int getSortMinLimit() {
		return 0;
	}

	public void setSort(int sort) {
		if (sort < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[类型]sort的值不得小于0");
		}
		this.sort = sort;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ExchangeTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ExchangeTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ExchangeTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ExchangeTemplateVO [  langDesc=" + langDesc + ", icon=" + icon + ", itemId=" + itemId + ", consumeCount=" + consumeCount + ", exchangeID=" + exchangeID + ", exchangeCount=" + exchangeCount + ", sort=" + sort + ",]";
	}
}