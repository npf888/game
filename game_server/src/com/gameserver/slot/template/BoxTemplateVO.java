package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * slots list
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BoxTemplateVO extends TemplateObject {

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

	/** 倍数*100 */
	@ExcelCellBinding(offset = 6)
	protected int pay;

	/** 权重 */
	@ExcelCellBinding(offset = 7)
	protected int value;

	/** 等级下限 */
	@ExcelCellBinding(offset = 8)
	protected int levelDown;

	/** 等级上限 */
	@ExcelCellBinding(offset = 9)
	protected int levelUp;


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
	
	public int getPay() {
		return this.pay;
	}


	public final static int getPayMinLimit() {
		return 0;
	}

	public void setPay(int pay) {
		if (pay < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[倍数*100]pay的值不得小于0");
		}
		this.pay = pay;
	}
	
	public int getValue() {
		return this.value;
	}


	public final static int getValueMinLimit() {
		return 0;
	}

	public void setValue(int value) {
		if (value < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[权重]value的值不得小于0");
		}
		this.value = value;
	}
	
	public int getLevelDown() {
		return this.levelDown;
	}


	public final static int getLevelDownMinLimit() {
		return 0;
	}

	public void setLevelDown(int levelDown) {
		if (levelDown < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[等级下限]levelDown的值不得小于0");
		}
		this.levelDown = levelDown;
	}
	
	public int getLevelUp() {
		return this.levelUp;
	}


	public final static int getLevelUpMinLimit() {
		return 0;
	}

	public void setLevelUp(int levelUp) {
		if (levelUp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[等级上限]levelUp的值不得小于0");
		}
		this.levelUp = levelUp;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BoxTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BoxTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BoxTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BoxTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", slotsNum=" + slotsNum + ", pay=" + pay + ", value=" + value + ", levelDown=" + levelDown + ", levelUp=" + levelUp + ",]";
	}
}