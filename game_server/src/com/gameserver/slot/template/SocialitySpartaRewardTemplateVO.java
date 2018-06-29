package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * SocialitySpartaRewardTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class SocialitySpartaRewardTemplateVO extends TemplateObject {

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

	/** 1.第一奖池 2.第二奖池 3.第三奖池 4.第四奖池 */
	@ExcelCellBinding(offset = 6)
	protected int type;

	/** 奖池wild个数 */
	@ExcelCellBinding(offset = 7)
	protected int wildNum;

	/** 奖池奖励，单线下注额的倍数，数值除以100使用 */
	@ExcelCellBinding(offset = 8)
	protected int rewardNum;

	/** 攻城次数（N1<玩家≤N2）,起始位0 */
	@ExcelCellBinding(offset = 9)
	protected int wallTimes;

	/** 奖励百分比，奖励除以100 */
	@ExcelCellBinding(offset = 10)
	protected int rewardPercent;


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
					7, "[1.第一奖池 2.第二奖池 3.第三奖池 4.第四奖池]type的值不得小于0");
		}
		this.type = type;
	}
	
	public int getWildNum() {
		return this.wildNum;
	}


	public final static int getWildNumMinLimit() {
		return 0;
	}

	public void setWildNum(int wildNum) {
		if (wildNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[奖池wild个数]wildNum的值不得小于0");
		}
		this.wildNum = wildNum;
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
					9, "[奖池奖励，单线下注额的倍数，数值除以100使用]rewardNum的值不得小于0");
		}
		this.rewardNum = rewardNum;
	}
	
	public int getWallTimes() {
		return this.wallTimes;
	}


	public final static int getWallTimesMinLimit() {
		return 0;
	}

	public void setWallTimes(int wallTimes) {
		if (wallTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[攻城次数（N1<玩家≤N2）,起始位0]wallTimes的值不得小于0");
		}
		this.wallTimes = wallTimes;
	}
	
	public int getRewardPercent() {
		return this.rewardPercent;
	}


	public final static int getRewardPercentMinLimit() {
		return 0;
	}

	public void setRewardPercent(int rewardPercent) {
		if (rewardPercent < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[奖励百分比，奖励除以100]rewardPercent的值不得小于0");
		}
		this.rewardPercent = rewardPercent;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, SocialitySpartaRewardTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends SocialitySpartaRewardTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, SocialitySpartaRewardTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "SocialitySpartaRewardTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", slotsNum=" + slotsNum + ", type=" + type + ", wildNum=" + wildNum + ", rewardNum=" + rewardNum + ", wallTimes=" + wallTimes + ", rewardPercent=" + rewardPercent + ",]";
	}
}