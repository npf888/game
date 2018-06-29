package com.gameserver.club.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * ClubRecharge.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ClubRechargeTemplateVO extends TemplateObject {

	/** 描述 */
	@ExcelCellBinding(offset = 1)
	protected int descrip;

	/** 注释 */
	@ExcelCellBinding(offset = 2)
	protected String langDesc;

	/** ico */
	@ExcelCellBinding(offset = 3)
	protected String icon;

	/** 物品id */
	@ExcelCellBinding(offset = 4)
	protected int item1Id;

	/** 购买者获得金币数量 */
	@ExcelCellBinding(offset = 5)
	protected int itemNum1;

	/** 物品id */
	@ExcelCellBinding(offset = 6)
	protected int item2Id;

	/** 购买者获得金币数量 */
	@ExcelCellBinding(offset = 7)
	protected int itemNum2;

	/** 物品id */
	@ExcelCellBinding(offset = 8)
	protected int item3Id;

	/** 购买者获得数量 */
	@ExcelCellBinding(offset = 9)
	protected int itemNum3;


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
	
	public int getItem1Id() {
		return this.item1Id;
	}


	public final static int getItem1IdMinLimit() {
		return 0;
	}

	public void setItem1Id(int item1Id) {
		if (item1Id < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[物品id]item1Id的值不得小于0");
		}
		this.item1Id = item1Id;
	}
	
	public int getItemNum1() {
		return this.itemNum1;
	}


	public final static int getItemNum1MinLimit() {
		return 0;
	}

	public void setItemNum1(int itemNum1) {
		if (itemNum1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[购买者获得金币数量]itemNum1的值不得小于0");
		}
		this.itemNum1 = itemNum1;
	}
	
	public int getItem2Id() {
		return this.item2Id;
	}


	public final static int getItem2IdMinLimit() {
		return 0;
	}

	public void setItem2Id(int item2Id) {
		if (item2Id < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[物品id]item2Id的值不得小于0");
		}
		this.item2Id = item2Id;
	}
	
	public int getItemNum2() {
		return this.itemNum2;
	}


	public final static int getItemNum2MinLimit() {
		return 0;
	}

	public void setItemNum2(int itemNum2) {
		if (itemNum2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[购买者获得金币数量]itemNum2的值不得小于0");
		}
		this.itemNum2 = itemNum2;
	}
	
	public int getItem3Id() {
		return this.item3Id;
	}


	public final static int getItem3IdMinLimit() {
		return 0;
	}

	public void setItem3Id(int item3Id) {
		if (item3Id < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[物品id]item3Id的值不得小于0");
		}
		this.item3Id = item3Id;
	}
	
	public int getItemNum3() {
		return this.itemNum3;
	}


	public final static int getItemNum3MinLimit() {
		return 0;
	}

	public void setItemNum3(int itemNum3) {
		if (itemNum3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[购买者获得数量]itemNum3的值不得小于0");
		}
		this.itemNum3 = itemNum3;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ClubRechargeTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ClubRechargeTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ClubRechargeTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ClubRechargeTemplateVO [  descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", item1Id=" + item1Id + ", itemNum1=" + itemNum1 + ", item2Id=" + item2Id + ", itemNum2=" + itemNum2 + ", item3Id=" + item3Id + ", itemNum3=" + itemNum3 + ",]";
	}
}