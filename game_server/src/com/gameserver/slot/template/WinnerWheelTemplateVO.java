package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * WinnerWheelTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class WinnerWheelTemplateVO extends TemplateObject {

	/** 描述 */
	@ExcelCellBinding(offset = 1)
	protected String descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 2)
	protected String langDesc;

	/** icon */
	@ExcelCellBinding(offset = 3)
	protected String icon;

	/** 客户端类型 */
	@ExcelCellBinding(offset = 4)
	protected int itemId;

	/** 服务器类型 */
	@ExcelCellBinding(offset = 5)
	protected int itemMul;

	/** 转盘1权值 */
	@ExcelCellBinding(offset = 6)
	protected int value1;

	/** 转盘2权值 */
	@ExcelCellBinding(offset = 7)
	protected int value2;

	/** 转盘3权值 */
	@ExcelCellBinding(offset = 8)
	protected int value3;

	/** 转盘4权值 */
	@ExcelCellBinding(offset = 9)
	protected int value4;

	/** 转盘5权值 */
	@ExcelCellBinding(offset = 10)
	protected int value5;

	/** 转盘6权值 */
	@ExcelCellBinding(offset = 11)
	protected int value6;


	public String getDescrip() {
		return this.descrip;
	}



	public void setDescrip(String descrip) {
		if (StringUtils.isEmpty(descrip)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[描述]descrip不可以为空");
		}
		this.descrip = descrip;
	}
	
	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		if (StringUtils.isEmpty(langDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[多语言描述]langDesc不可以为空");
		}
		this.langDesc = langDesc;
	}
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public int getItemId() {
		return this.itemId;
	}



	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public int getItemMul() {
		return this.itemMul;
	}



	public void setItemMul(int itemMul) {
		this.itemMul = itemMul;
	}
	
	public int getValue1() {
		return this.value1;
	}



	public void setValue1(int value1) {
		this.value1 = value1;
	}
	
	public int getValue2() {
		return this.value2;
	}



	public void setValue2(int value2) {
		this.value2 = value2;
	}
	
	public int getValue3() {
		return this.value3;
	}



	public void setValue3(int value3) {
		this.value3 = value3;
	}
	
	public int getValue4() {
		return this.value4;
	}



	public void setValue4(int value4) {
		this.value4 = value4;
	}
	
	public int getValue5() {
		return this.value5;
	}



	public void setValue5(int value5) {
		this.value5 = value5;
	}
	
	public int getValue6() {
		return this.value6;
	}



	public void setValue6(int value6) {
		this.value6 = value6;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, WinnerWheelTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends WinnerWheelTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, WinnerWheelTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "WinnerWheelTemplateVO [  descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", itemId=" + itemId + ", itemMul=" + itemMul + ", value1=" + value1 + ", value2=" + value2 + ", value3=" + value3 + ", value4=" + value4 + ", value5=" + value5 + ", value6=" + value6 + ",]";
	}
}