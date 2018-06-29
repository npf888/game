package com.gameserver.collect.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * ShavePrizeTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ShavePrizeTemplateVO extends TemplateObject {

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

	/** 奖池类型 */
	@ExcelCellBinding(offset = 5)
	protected int poolType;

	/** 物品ID */
	@ExcelCellBinding(offset = 6)
	protected int itemID;

	/** 数量 */
	@ExcelCellBinding(offset = 7)
	protected int rewardNum;

	/** 权重 */
	@ExcelCellBinding(offset = 8)
	protected int probability;


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
	
	public int getPoolType() {
		return this.poolType;
	}


	public final static int getPoolTypeMinLimit() {
		return 0;
	}

	public void setPoolType(int poolType) {
		if (poolType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[奖池类型]poolType的值不得小于0");
		}
		this.poolType = poolType;
	}
	
	public int getItemID() {
		return this.itemID;
	}


	public final static int getItemIDMinLimit() {
		return 0;
	}

	public void setItemID(int itemID) {
		if (itemID < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[物品ID]itemID的值不得小于0");
		}
		this.itemID = itemID;
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
	
	public int getProbability() {
		return this.probability;
	}


	public final static int getProbabilityMinLimit() {
		return 0;
	}

	public void setProbability(int probability) {
		if (probability < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[权重]probability的值不得小于0");
		}
		this.probability = probability;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ShavePrizeTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ShavePrizeTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ShavePrizeTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ShavePrizeTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", poolType=" + poolType + ", itemID=" + itemID + ", rewardNum=" + rewardNum + ", probability=" + probability + ",]";
	}
}