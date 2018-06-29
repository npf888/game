package com.gameserver.luckyspin.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * luckyspin
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class TurntableTemplateVO extends TemplateObject {

	/** 类型 0 免费 1 收费 */
	@ExcelCellBinding(offset = 1)
	protected int type;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected String descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** icon */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** itemId */
	@ExcelCellBinding(offset = 5)
	protected int itemId;

	/** itemNum */
	@ExcelCellBinding(offset = 6)
	protected int itemNum;

	/** 池子1 */
	@ExcelCellBinding(offset = 7)
	protected int value1;

	/** 池子1 */
	@ExcelCellBinding(offset = 8)
	protected int value2;

	/** 池子1 */
	@ExcelCellBinding(offset = 9)
	protected int value3;

	/** 池子1 */
	@ExcelCellBinding(offset = 10)
	protected int value4;

	/** 池子1 */
	@ExcelCellBinding(offset = 11)
	protected int value5;

	/** 池子1 */
	@ExcelCellBinding(offset = 12)
	protected int value6;

	/** 池子1 */
	@ExcelCellBinding(offset = 13)
	protected int value7;

	/** 池子1 */
	@ExcelCellBinding(offset = 14)
	protected int value8;

	/** 池子1 */
	@ExcelCellBinding(offset = 15)
	protected int value9;

	/** 池子1 */
	@ExcelCellBinding(offset = 16)
	protected int value10;

	/** pid */
	@ExcelCellBinding(offset = 17)
	protected int pidID;


	public int getType() {
		return this.type;
	}


	public final static int getTypeMinLimit() {
		return 0;
	}

	public void setType(int type) {
		if (type < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[类型 0 免费 1 收费]type的值不得小于0");
		}
		this.type = type;
	}
	
	public String getDescrip() {
		return this.descrip;
	}



	public void setDescrip(String descrip) {
		if (StringUtils.isEmpty(descrip)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[多语言描述id]descrip不可以为空");
		}
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
		if (StringUtils.isEmpty(icon)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[icon]icon不可以为空");
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
					6, "[itemId]itemId的值不得小于0");
		}
		this.itemId = itemId;
	}
	
	public int getItemNum() {
		return this.itemNum;
	}


	public final static int getItemNumMinLimit() {
		return 0;
	}

	public void setItemNum(int itemNum) {
		if (itemNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[itemNum]itemNum的值不得小于0");
		}
		this.itemNum = itemNum;
	}
	
	public int getValue1() {
		return this.value1;
	}


	public final static int getValue1MinLimit() {
		return 0;
	}

	public void setValue1(int value1) {
		if (value1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[池子1]value1的值不得小于0");
		}
		this.value1 = value1;
	}
	
	public int getValue2() {
		return this.value2;
	}


	public final static int getValue2MinLimit() {
		return 0;
	}

	public void setValue2(int value2) {
		if (value2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[池子1]value2的值不得小于0");
		}
		this.value2 = value2;
	}
	
	public int getValue3() {
		return this.value3;
	}


	public final static int getValue3MinLimit() {
		return 0;
	}

	public void setValue3(int value3) {
		if (value3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[池子1]value3的值不得小于0");
		}
		this.value3 = value3;
	}
	
	public int getValue4() {
		return this.value4;
	}


	public final static int getValue4MinLimit() {
		return 0;
	}

	public void setValue4(int value4) {
		if (value4 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[池子1]value4的值不得小于0");
		}
		this.value4 = value4;
	}
	
	public int getValue5() {
		return this.value5;
	}


	public final static int getValue5MinLimit() {
		return 0;
	}

	public void setValue5(int value5) {
		if (value5 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[池子1]value5的值不得小于0");
		}
		this.value5 = value5;
	}
	
	public int getValue6() {
		return this.value6;
	}


	public final static int getValue6MinLimit() {
		return 0;
	}

	public void setValue6(int value6) {
		if (value6 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[池子1]value6的值不得小于0");
		}
		this.value6 = value6;
	}
	
	public int getValue7() {
		return this.value7;
	}


	public final static int getValue7MinLimit() {
		return 0;
	}

	public void setValue7(int value7) {
		if (value7 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[池子1]value7的值不得小于0");
		}
		this.value7 = value7;
	}
	
	public int getValue8() {
		return this.value8;
	}


	public final static int getValue8MinLimit() {
		return 0;
	}

	public void setValue8(int value8) {
		if (value8 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[池子1]value8的值不得小于0");
		}
		this.value8 = value8;
	}
	
	public int getValue9() {
		return this.value9;
	}


	public final static int getValue9MinLimit() {
		return 0;
	}

	public void setValue9(int value9) {
		if (value9 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[池子1]value9的值不得小于0");
		}
		this.value9 = value9;
	}
	
	public int getValue10() {
		return this.value10;
	}


	public final static int getValue10MinLimit() {
		return 0;
	}

	public void setValue10(int value10) {
		if (value10 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[池子1]value10的值不得小于0");
		}
		this.value10 = value10;
	}
	
	public int getPidID() {
		return this.pidID;
	}


	public final static int getPidIDMinLimit() {
		return 0;
	}

	public void setPidID(int pidID) {
		if (pidID < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[pid]pidID的值不得小于0");
		}
		this.pidID = pidID;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, TurntableTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends TurntableTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, TurntableTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "TurntableTemplateVO [  type=" + type + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", itemId=" + itemId + ", itemNum=" + itemNum + ", value1=" + value1 + ", value2=" + value2 + ", value3=" + value3 + ", value4=" + value4 + ", value5=" + value5 + ", value6=" + value6 + ", value7=" + value7 + ", value8=" + value8 + ", value9=" + value9 + ", value10=" + value10 + ", pidID=" + pidID + ",]";
	}
}