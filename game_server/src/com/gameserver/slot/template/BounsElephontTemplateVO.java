package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * BounsElephontTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BounsElephontTemplateVO extends TemplateObject {

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

	/** 老虎机机器编号 */
	@ExcelCellBinding(offset = 5)
	protected int slotsNum;

	/** bonus个数 */
	@ExcelCellBinding(offset = 6)
	protected int bonusNum;

	/** 宝箱倍数 除以100 */
	@ExcelCellBinding(offset = 7)
	protected int box1;

	/** 权重 */
	@ExcelCellBinding(offset = 8)
	protected int value1;

	/**  */
	@ExcelCellBinding(offset = 9)
	protected int box2;

	/**  */
	@ExcelCellBinding(offset = 10)
	protected int value2;

	/**  */
	@ExcelCellBinding(offset = 11)
	protected int box3;

	/**  */
	@ExcelCellBinding(offset = 12)
	protected int value3;

	/**  */
	@ExcelCellBinding(offset = 13)
	protected int box4;

	/**  */
	@ExcelCellBinding(offset = 14)
	protected int value4;


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



	public void setSlotsNum(int slotsNum) {
		this.slotsNum = slotsNum;
	}
	
	public int getBonusNum() {
		return this.bonusNum;
	}



	public void setBonusNum(int bonusNum) {
		this.bonusNum = bonusNum;
	}
	
	public int getBox1() {
		return this.box1;
	}



	public void setBox1(int box1) {
		this.box1 = box1;
	}
	
	public int getValue1() {
		return this.value1;
	}



	public void setValue1(int value1) {
		this.value1 = value1;
	}
	
	public int getBox2() {
		return this.box2;
	}



	public void setBox2(int box2) {
		this.box2 = box2;
	}
	
	public int getValue2() {
		return this.value2;
	}



	public void setValue2(int value2) {
		this.value2 = value2;
	}
	
	public int getBox3() {
		return this.box3;
	}



	public void setBox3(int box3) {
		this.box3 = box3;
	}
	
	public int getValue3() {
		return this.value3;
	}



	public void setValue3(int value3) {
		this.value3 = value3;
	}
	
	public int getBox4() {
		return this.box4;
	}



	public void setBox4(int box4) {
		this.box4 = box4;
	}
	
	public int getValue4() {
		return this.value4;
	}



	public void setValue4(int value4) {
		this.value4 = value4;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BounsElephontTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BounsElephontTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BounsElephontTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BounsElephontTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", slotsNum=" + slotsNum + ", bonusNum=" + bonusNum + ", box1=" + box1 + ", value1=" + value1 + ", box2=" + box2 + ", value2=" + value2 + ", box3=" + box3 + ", value3=" + value3 + ", box4=" + box4 + ", value4=" + value4 + ",]";
	}
}