package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * GoldBonus
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class GoldBonusTemplateVO extends TemplateObject {

	/** 1 gold 2 free */
	@ExcelCellBinding(offset = 1)
	protected int type;

	/** 2 2转盘 3 3转盘 */
	@ExcelCellBinding(offset = 2)
	protected int wheelType;

	/** 中奖类型，1、linebet，2、转盘2*倍数，3转盘2累加，4大转盘,5:免费转动 */
	@ExcelCellBinding(offset = 3)
	protected int rewardType;

	/** 中奖数量,0:代表大转盘 */
	@ExcelCellBinding(offset = 4)
	protected int rewardNum;


	public int getType() {
		return this.type;
	}


	public final static int getTypeMinLimit() {
		return 0;
	}

	public void setType(int type) {
		if (type < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[1 gold 2 free]type的值不得小于0");
		}
		this.type = type;
	}
	
	public int getWheelType() {
		return this.wheelType;
	}


	public final static int getWheelTypeMinLimit() {
		return 0;
	}

	public void setWheelType(int wheelType) {
		if (wheelType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[2 2转盘 3 3转盘]wheelType的值不得小于0");
		}
		this.wheelType = wheelType;
	}
	
	public int getRewardType() {
		return this.rewardType;
	}


	public final static int getRewardTypeMinLimit() {
		return 0;
	}

	public void setRewardType(int rewardType) {
		if (rewardType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[中奖类型，1、linebet，2、转盘2*倍数，3转盘2累加，4大转盘,5:免费转动]rewardType的值不得小于0");
		}
		this.rewardType = rewardType;
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
					5, "[中奖数量,0:代表大转盘]rewardNum的值不得小于0");
		}
		this.rewardNum = rewardNum;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, GoldBonusTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends GoldBonusTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, GoldBonusTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "GoldBonusTemplateVO [  type=" + type + ", wheelType=" + wheelType + ", rewardType=" + rewardType + ", rewardNum=" + rewardNum + ",]";
	}
}