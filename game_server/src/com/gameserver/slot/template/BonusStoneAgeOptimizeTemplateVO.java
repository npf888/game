package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * BonusStoneAgeOptimizeTemplate
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BonusStoneAgeOptimizeTemplateVO extends TemplateObject {

	/** 名称ID */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 描述ID */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 描述（自己看） */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** 图标ID */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** 老虎机类型 */
	@ExcelCellBinding(offset = 5)
	protected int slotsNum;

	/** bonus个数 */
	@ExcelCellBinding(offset = 6)
	protected int bonusNum;

	/** 翻符文游戏相同元素数量 */
	@ExcelCellBinding(offset = 7)
	protected int samNum;

	/** 摘苹果游戏次数 */
	@ExcelCellBinding(offset = 8)
	protected int appleGameNum;

	/** 单线下注额倍数,(百分比，实际值除以100） */
	@ExcelCellBinding(offset = 9)
	protected int appleReward;

	/** 狩猎游戏，夹子数量 */
	@ExcelCellBinding(offset = 10)
	protected int preyNum;

	/** 狩猎游戏单线下注额倍数 */
	@ExcelCellBinding(offset = 11)
	protected int preyReward;


	public int getNameId() {
		return this.nameId;
	}


	public final static int getNameIdMinLimit() {
		return 0;
	}

	public void setNameId(int nameId) {
		if (nameId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[名称ID]nameId的值不得小于0");
		}
		this.nameId = nameId;
	}
	
	public int getDescrip() {
		return this.descrip;
	}


	public final static int getDescripMinLimit() {
		return 0;
	}

	public void setDescrip(int descrip) {
		if (descrip < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[描述ID]descrip的值不得小于0");
		}
		this.descrip = descrip;
	}
	
	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		if (StringUtils.isEmpty(langDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[描述（自己看）]langDesc不可以为空");
		}
		this.langDesc = langDesc;
	}
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		if (StringUtils.isEmpty(icon)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[图标ID]icon不可以为空");
		}
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
	
	public int getBonusNum() {
		return this.bonusNum;
	}


	public final static int getBonusNumMinLimit() {
		return 0;
	}

	public void setBonusNum(int bonusNum) {
		if (bonusNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[bonus个数]bonusNum的值不得小于0");
		}
		this.bonusNum = bonusNum;
	}
	
	public int getSamNum() {
		return this.samNum;
	}


	public final static int getSamNumMinLimit() {
		return 0;
	}

	public void setSamNum(int samNum) {
		if (samNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[翻符文游戏相同元素数量]samNum的值不得小于0");
		}
		this.samNum = samNum;
	}
	
	public int getAppleGameNum() {
		return this.appleGameNum;
	}


	public final static int getAppleGameNumMinLimit() {
		return 0;
	}

	public void setAppleGameNum(int appleGameNum) {
		if (appleGameNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[摘苹果游戏次数]appleGameNum的值不得小于0");
		}
		this.appleGameNum = appleGameNum;
	}
	
	public int getAppleReward() {
		return this.appleReward;
	}


	public final static int getAppleRewardMinLimit() {
		return 0;
	}

	public void setAppleReward(int appleReward) {
		if (appleReward < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[单线下注额倍数,(百分比，实际值除以100）]appleReward的值不得小于0");
		}
		this.appleReward = appleReward;
	}
	
	public int getPreyNum() {
		return this.preyNum;
	}


	public final static int getPreyNumMinLimit() {
		return 0;
	}

	public void setPreyNum(int preyNum) {
		if (preyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[狩猎游戏，夹子数量]preyNum的值不得小于0");
		}
		this.preyNum = preyNum;
	}
	
	public int getPreyReward() {
		return this.preyReward;
	}


	public final static int getPreyRewardMinLimit() {
		return 0;
	}

	public void setPreyReward(int preyReward) {
		if (preyReward < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[狩猎游戏单线下注额倍数]preyReward的值不得小于0");
		}
		this.preyReward = preyReward;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BonusStoneAgeOptimizeTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BonusStoneAgeOptimizeTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BonusStoneAgeOptimizeTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BonusStoneAgeOptimizeTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", slotsNum=" + slotsNum + ", bonusNum=" + bonusNum + ", samNum=" + samNum + ", appleGameNum=" + appleGameNum + ", appleReward=" + appleReward + ", preyNum=" + preyNum + ", preyReward=" + preyReward + ",]";
	}
}