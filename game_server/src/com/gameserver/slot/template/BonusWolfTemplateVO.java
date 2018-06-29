package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * BonusWolfTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BonusWolfTemplateVO extends TemplateObject {

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

	/** 1：单次bouns中奖,2；两次bouns中奖,3；三次bouns中奖 */
	@ExcelCellBinding(offset = 6)
	protected int type;

	/** 1：单次bouns中奖，2；两次bouns中奖，3；三次bouns中奖 */
	@ExcelCellBinding(offset = 7)
	protected int bounsNum;

	/** 拼图完成后奖励，为单线下注额的倍数，数值除以100使用 */
	@ExcelCellBinding(offset = 8)
	protected double rewardNum;


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
	
	public int getType() {
		return this.type;
	}



	public void setType(int type) {
		this.type = type;
	}
	
	public int getBounsNum() {
		return this.bounsNum;
	}



	public void setBounsNum(int bounsNum) {
		this.bounsNum = bounsNum;
	}
	
	public double getRewardNum() {
		return this.rewardNum;
	}



	public void setRewardNum(double rewardNum) {
		this.rewardNum = rewardNum;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BonusWolfTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BonusWolfTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BonusWolfTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BonusWolfTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", slotsNum=" + slotsNum + ", type=" + type + ", bounsNum=" + bounsNum + ", rewardNum=" + rewardNum + ",]";
	}
}