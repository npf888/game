package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * BonusStoneAgeRuneTemplate
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BonusStoneAgeRuneTemplateVO extends TemplateObject {

	/** 老虎机类型 */
	@ExcelCellBinding(offset = 1)
	protected int slotsNum;

	/** 转盘游戏奖池 */
	@ExcelCellBinding(offset = 2)
	protected int rewardPool;

	/** 单线下注额倍数,选取的次数(百分比，实际值除以100） */
	@ExcelCellBinding(offset = 3)
	protected int times;

	/** 权值 */
	@ExcelCellBinding(offset = 4)
	protected int weight;


	public int getSlotsNum() {
		return this.slotsNum;
	}


	public final static int getSlotsNumMinLimit() {
		return 0;
	}

	public void setSlotsNum(int slotsNum) {
		if (slotsNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[老虎机类型]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
	}
	
	public int getRewardPool() {
		return this.rewardPool;
	}


	public final static int getRewardPoolMinLimit() {
		return 0;
	}

	public void setRewardPool(int rewardPool) {
		if (rewardPool < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[转盘游戏奖池]rewardPool的值不得小于0");
		}
		this.rewardPool = rewardPool;
	}
	
	public int getTimes() {
		return this.times;
	}


	public final static int getTimesMinLimit() {
		return 0;
	}

	public void setTimes(int times) {
		if (times < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[单线下注额倍数,选取的次数(百分比，实际值除以100）]times的值不得小于0");
		}
		this.times = times;
	}
	
	public int getWeight() {
		return this.weight;
	}


	public final static int getWeightMinLimit() {
		return 0;
	}

	public void setWeight(int weight) {
		if (weight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[权值]weight的值不得小于0");
		}
		this.weight = weight;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BonusStoneAgeRuneTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BonusStoneAgeRuneTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BonusStoneAgeRuneTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BonusStoneAgeRuneTemplateVO [  slotsNum=" + slotsNum + ", rewardPool=" + rewardPool + ", times=" + times + ", weight=" + weight + ",]";
	}
}