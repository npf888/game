package com.gameserver.shop.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * 商品
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ShopTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 描述 */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** icon */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** 分类 */
	@ExcelCellBinding(offset = 5)
	protected int category;

	/** 金钱类型 */
	@ExcelCellBinding(offset = 6)
	protected int currencyType;

	/** 分类 */
	@ExcelCellBinding(offset = 7)
	protected int smallCategory;

	/** 花费数量 */
	@ExcelCellBinding(offset = 8)
	protected int num;

	/** 道具id */
	@ExcelCellBinding(offset = 9)
	protected int itemId;

	/** 道具数量 */
	@ExcelCellBinding(offset = 10)
	protected int itemNum;

	/** 额外比例 万分比 */
	@ExcelCellBinding(offset = 11)
	protected int upRate;


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
	
	public int getCategory() {
		return this.category;
	}


	public final static int getCategoryMinLimit() {
		return 1;
	}

	public void setCategory(int category) {
		if (category < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[分类]category的值不得小于1");
		}
		this.category = category;
	}
	
	public int getCurrencyType() {
		return this.currencyType;
	}


	public final static int getCurrencyTypeMinLimit() {
		return 1;
	}

	public void setCurrencyType(int currencyType) {
		if (currencyType < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[金钱类型]currencyType的值不得小于1");
		}
		this.currencyType = currencyType;
	}
	
	public int getSmallCategory() {
		return this.smallCategory;
	}


	public final static int getSmallCategoryMinLimit() {
		return 1;
	}

	public void setSmallCategory(int smallCategory) {
		if (smallCategory < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[分类]smallCategory的值不得小于1");
		}
		this.smallCategory = smallCategory;
	}
	
	public int getNum() {
		return this.num;
	}


	public final static int getNumMinLimit() {
		return 1;
	}

	public void setNum(int num) {
		if (num < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[花费数量]num的值不得小于1");
		}
		this.num = num;
	}
	
	public int getItemId() {
		return this.itemId;
	}


	public final static int getItemIdMinLimit() {
		return 1;
	}

	public void setItemId(int itemId) {
		if (itemId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[道具id]itemId的值不得小于1");
		}
		this.itemId = itemId;
	}
	
	public int getItemNum() {
		return this.itemNum;
	}


	public final static int getItemNumMinLimit() {
		return 1;
	}

	public void setItemNum(int itemNum) {
		if (itemNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[道具数量]itemNum的值不得小于1");
		}
		this.itemNum = itemNum;
	}
	
	public int getUpRate() {
		return this.upRate;
	}


	public final static int getUpRateMinLimit() {
		return 0;
	}

	public void setUpRate(int upRate) {
		if (upRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[额外比例 万分比]upRate的值不得小于0");
		}
		this.upRate = upRate;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ShopTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ShopTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ShopTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ShopTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", category=" + category + ", currencyType=" + currencyType + ", smallCategory=" + smallCategory + ", num=" + num + ", itemId=" + itemId + ", itemNum=" + itemNum + ", upRate=" + upRate + ",]";
	}
}