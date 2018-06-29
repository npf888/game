package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * BonusSpartaRewardTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BonusSpartaRewardTemplateVO extends TemplateObject {

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

	/** 老虎机类型 */
	@ExcelCellBinding(offset = 5)
	protected int slotsNum;

	/** 1.第一关 2.第二关 3.第三关 */
	@ExcelCellBinding(offset = 6)
	protected int type;

	/** 获胜次数 */
	@ExcelCellBinding(offset = 7)
	protected int winTimes;

	/** 获胜奖励：单线下注额的倍数，数值除以100使用 */
	@ExcelCellBinding(offset = 8)
	protected int rewardNum;

	/** 获胜概率（除以100） */
	@ExcelCellBinding(offset = 9)
	protected int value;


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
					6, "[老虎机类型]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
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
					7, "[1.第一关 2.第二关 3.第三关]type的值不得小于0");
		}
		this.type = type;
	}
	
	public int getWinTimes() {
		return this.winTimes;
	}


	public final static int getWinTimesMinLimit() {
		return 0;
	}

	public void setWinTimes(int winTimes) {
		if (winTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[获胜次数]winTimes的值不得小于0");
		}
		this.winTimes = winTimes;
	}
	
	public int getRewardNum() {
		return this.rewardNum;
	}


	public final static int getRewardNumMinLimit() {
		return 0;
	}

	public void setRewardNum(int rewardNum) {
		if (rewardNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[获胜奖励：单线下注额的倍数，数值除以100使用]rewardNum的值不得小于0");
		}
		this.rewardNum = rewardNum;
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
					10, "[获胜概率（除以100）]value的值不得小于0");
		}
		this.value = value;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BonusSpartaRewardTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BonusSpartaRewardTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BonusSpartaRewardTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BonusSpartaRewardTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", slotsNum=" + slotsNum + ", type=" + type + ", winTimes=" + winTimes + ", rewardNum=" + rewardNum + ", value=" + value + ",]";
	}
}