package com.gameserver.collect.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * CardRewardTemplateVO.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class CardRewardTemplateVO extends TemplateObject {

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

	/** 集齐类型 */
	@ExcelCellBinding(offset = 5)
	protected int cardType;

	/** 类型 */
	@ExcelCellBinding(offset = 6)
	protected int rewardId;

	/** 数量 */
	@ExcelCellBinding(offset = 7)
	protected int rewardNum;


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
	
	public int getCardType() {
		return this.cardType;
	}


	public final static int getCardTypeMinLimit() {
		return 0;
	}

	public void setCardType(int cardType) {
		if (cardType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[集齐类型]cardType的值不得小于0");
		}
		this.cardType = cardType;
	}
	
	public int getRewardId() {
		return this.rewardId;
	}


	public final static int getRewardIdMinLimit() {
		return 0;
	}

	public void setRewardId(int rewardId) {
		if (rewardId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[类型]rewardId的值不得小于0");
		}
		this.rewardId = rewardId;
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
					8, "[数量]rewardNum的值不得小于0");
		}
		this.rewardNum = rewardNum;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, CardRewardTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends CardRewardTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, CardRewardTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "CardRewardTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", cardType=" + cardType + ", rewardId=" + rewardId + ", rewardNum=" + rewardNum + ",]";
	}
}