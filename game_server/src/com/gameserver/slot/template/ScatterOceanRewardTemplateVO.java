package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * ScatterOceanRewardTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ScatterOceanRewardTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** 老虎机类型 */
	@ExcelCellBinding(offset = 4)
	protected int slotsNum;

	/** 玩家所选乌龟的排名 */
	@ExcelCellBinding(offset = 5)
	protected int rank;

	/** 翻开贝壳后的奖励，单线下注额的倍数，数值除以100使用 */
	@ExcelCellBinding(offset = 6)
	protected int rewardNum;

	/** 权值 */
	@ExcelCellBinding(offset = 7)
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
	
	public int getSlotsNum() {
		return this.slotsNum;
	}


	public final static int getSlotsNumMinLimit() {
		return 0;
	}

	public void setSlotsNum(int slotsNum) {
		if (slotsNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[老虎机类型]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
	}
	
	public int getRank() {
		return this.rank;
	}


	public final static int getRankMinLimit() {
		return 0;
	}

	public void setRank(int rank) {
		if (rank < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[玩家所选乌龟的排名]rank的值不得小于0");
		}
		this.rank = rank;
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
					7, "[翻开贝壳后的奖励，单线下注额的倍数，数值除以100使用]rewardNum的值不得小于0");
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
					8, "[权值]value的值不得小于0");
		}
		this.value = value;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ScatterOceanRewardTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ScatterOceanRewardTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ScatterOceanRewardTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ScatterOceanRewardTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", slotsNum=" + slotsNum + ", rank=" + rank + ", rewardNum=" + rewardNum + ", value=" + value + ",]";
	}
}