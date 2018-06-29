package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * BonusBrazilTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BonusBrazilTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** 小游戏类型 1：喝酒小游戏 2：桑巴小游戏 */
	@ExcelCellBinding(offset = 4)
	protected int type;

	/** icon */
	@ExcelCellBinding(offset = 5)
	protected String icon;

	/** 老虎机类型 */
	@ExcelCellBinding(offset = 6)
	protected int slotsNum;

	/** bonus的个数 */
	@ExcelCellBinding(offset = 7)
	protected int bonusNum;

	/** 权值 */
	@ExcelCellBinding(offset = 8)
	protected int value;

	/** 机会数量 */
	@ExcelCellBinding(offset = 9)
	protected int chance;


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
	
	public int getType() {
		return this.type;
	}



	public void setType(int type) {
		this.type = type;
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
					7, "[老虎机类型]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
	}
	
	public int getBonusNum() {
		return this.bonusNum;
	}


	public final static int getBonusNumMinLimit() {
		return 0;
	}

	public void setBonusNum(int bonusNum) {
		if (bonusNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[bonus的个数]bonusNum的值不得小于0");
		}
		this.bonusNum = bonusNum;
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
					9, "[权值]value的值不得小于0");
		}
		this.value = value;
	}
	
	public int getChance() {
		return this.chance;
	}


	public final static int getChanceMinLimit() {
		return 0;
	}

	public void setChance(int chance) {
		if (chance < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[机会数量]chance的值不得小于0");
		}
		this.chance = chance;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BonusBrazilTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BonusBrazilTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BonusBrazilTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BonusBrazilTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", type=" + type + ", icon=" + icon + ", slotsNum=" + slotsNum + ", bonusNum=" + bonusNum + ", value=" + value + ", chance=" + chance + ",]";
	}
}